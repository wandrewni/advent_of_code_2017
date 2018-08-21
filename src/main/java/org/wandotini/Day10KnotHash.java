package org.wandotini;

import java.util.Arrays;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Day10KnotHash {
    private Integer[] hash;
    private int currentPosition = 0;
    private int skipSize = 0;
    private int repititions = 1;
    private HashDensifier hashDensifier = hash -> hash;

    public Day10KnotHash(Integer... integers) {
        this.hash = integers;
    }

    public void twist(LengthsGenerator lengthsGenerator) {
        for (int i = 0; i < repititions; i++)
            for (Integer length : lengthsGenerator.buildLengths())
                twist(length);
        hash = hashDensifier.densify(hash);
    }

    private void twist(int length) {
        if (length > hash.length)
            throw new IllegalArgumentException();
        twistHash(length);
        updateCurrentPosition(length);
    }

    private void twistHash(int length) {
        hash = IntStream.range(0, hash.length)
                .mapToObj(findSwapValue(length))
                .toArray(Integer[]::new);
    }

    private IntFunction<Integer> findSwapValue(int length) {
        return i -> hash[findIndexToSwap(i, length)];
    }

    private void updateCurrentPosition(int length) {
        currentPosition += length + skipSize++;
        currentPosition = retractToWithinHashBounds(currentPosition);
    }

    protected int findIndexToSwap(int initialIndex, int length) {
        final int twistEndIndex = currentPosition + length;
        if (indexFallsOutsideOfTwist(initialIndex, twistEndIndex))
            return initialIndex;
        int swapIndex = twistEndIndex - (initialIndex - currentPosition) - 1;
        return retractToWithinHashBounds(swapIndex);
    }

    private int retractToWithinHashBounds(int index) {
        return index % hash.length;
    }

    private boolean indexFallsOutsideOfTwist(int index, int twistEndIndex) {
        boolean twistDoesNotWrap = twistEndIndex < hash.length;
        return index < currentPosition && twistDoesNotWrap ||
                index >= twistEndIndex && twistDoesNotWrap ||
                index < currentPosition && index >= twistEndIndex - hash.length;
    }

    public Integer[] getHash() {
        return hash;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }

    public void setRepititions(int repititions) {
        this.repititions = repititions;
    }

    public void setDensifier(HashDensifier densifier) {
        this.hashDensifier = densifier;
    }

    public String toHashString() {
        return Arrays.stream(hash)
                .map(this::convert)
                .collect(Collectors.joining());
    }

    private String convert(int input) {
        final String hexString = Integer.toHexString(input);
        return hexString.length() == 1 ? "0" + hexString : hexString;
    }

    public interface LengthsGenerator {
        Integer[] buildLengths();
    }

    public interface HashDensifier {
        Integer[] densify(Integer[] hash);
    }

    public static class AsciiLengthsGenerator implements LengthsGenerator {
        private final String input;

        public AsciiLengthsGenerator(String input) {
            this.input = input;
        }

        public Integer[] buildLengths() {
            return input.chars().boxed().toArray(Integer[]::new);
        }
    }

    public static class IntegerLengthsGenerator implements LengthsGenerator {
        private final Integer[] input;

        public IntegerLengthsGenerator(Integer... input) {
            this.input = input;
        }

        public Integer[] buildLengths() {
            return input;
        }
    }

    public static class AsciiLengthsGeneratorWithSuffix extends AsciiLengthsGenerator {
        public AsciiLengthsGeneratorWithSuffix(String input) {
            super(input);
        }

        public Integer[] buildLengths() {
            final Stream<Integer> translatedInput = Arrays.stream(super.buildLengths());
            final Stream<Integer> standardSuffix = Arrays.stream(new Integer[]{17, 31, 73, 47, 23});
            return Stream.concat(translatedInput, standardSuffix)
                    .toArray(Integer[]::new);
        }
    }

    public static class BitWiseOrDensifier implements HashDensifier{
        public Integer[] densify(Integer[] hash) throws IllegalStateException {
            return toStreamBlocks(hash, 16)
                    .map(this::bitwiseOr)
                    .toArray(Integer[]::new);
        }

        private Stream<Stream<Integer>> toStreamBlocks(Integer[] input, int blockLimit) {
            return IntStream.range(0, input.length / blockLimit)
                    .mapToObj(i -> Arrays.stream(input, i * blockLimit, (i + 1) * blockLimit));
        }

        private Integer bitwiseOr(Stream<Integer> intBlock) {
            return intBlock.reduce((previous, current) -> previous ^ current)
            .orElseThrow(() -> new IllegalAccessError("Input hash length must be multiple of 16"));
        }

    }
}
