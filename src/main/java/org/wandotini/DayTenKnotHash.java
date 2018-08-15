package org.wandotini;

public class DayTenKnotHash {
    private Integer[] hash;
    private int currentPosition = 0;
    private int skipSize = 0;

    public DayTenKnotHash(Integer... integers) {
        this.hash = integers;
    }

    public void twist(int length) {
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
}
