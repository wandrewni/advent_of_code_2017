package org.wandotini;

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
        if (length > 1)
            twistHash(length);
        updateCurrentPosition(length);
    }

    private void twistHash(int length) {
        Integer[] newHash = new Integer[hash.length];
        for (int i = 0; i < hash.length; i++)
            newHash[i] = hash[findIndexToSwap(i, length)];
        hash = newHash;
    }

    private void updateCurrentPosition(int length) {
        currentPosition += length + skipSize++;
        while (currentPosition >= hash.length)
            currentPosition -= hash.length;
    }

    protected int findIndexToSwap(int index, int length) {
        final int twistEndIndex = currentPosition + length;
        if (indexFallsOutsideOfTwist(index, twistEndIndex))
            return index;
        int swap = twistEndIndex - (index - currentPosition) - 1;
        while (swap >= hash.length)
            swap -= hash.length;
        return swap;
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
