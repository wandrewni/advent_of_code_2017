package org.wandotini;

import java.util.function.IntFunction;
import java.util.stream.IntStream;

public class DayTenKnotHash {
    private Integer[] hash;
    private int currentPosition = 0;
    private int skipSize = 0;

    public DayTenKnotHash(Integer... integers) {
        this.hash = integers;
    }

    public void twist(LengthsGenerator lengthsGenerator) {
        final Integer[] lengths = lengthsGenerator.buildLengths();
        for (Integer length : lengths)
            twist(length);
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

    public interface LengthsGenerator {
        Integer[] buildLengths();
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
}
