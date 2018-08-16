package org.wandotini;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class DayTenKnotHashPartTwoTest {
    private static final Integer[] PUZZLE_INPUT = {94, 84, 0, 79, 2, 27, 81, 1, 123, 93, 218, 23, 103, 255, 254, 243};
    private DayTenKnotHash knotHash;

    @Test
    public void beforeTwistingHashIsInputHash() throws Exception {
        createHash(1, 2);
        verifyHash(1, 2);
    }

    @Test
    public void twistingOfLengthZeroOrOneLeavesHashUnchanged() throws Exception {
        createHash(1, 2);
        twist(0);
        verifyHash(1, 2);
        twist(1);
    }

    @Test
    public void twistingOfFullLengthReversesEntireArray() throws Exception {
        createHash(1, 2);
        twist(2);
        verifyHash(2, 1);

        createHash(1, 2, 3);
        twist(3);
        verifyHash(3, 2, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void lengthLongerThanHashLengthIsInvalid() throws Exception {
        createHash(1, 2, 3, 4);
        twist(20);
    }

    @Test
    public void lengthSmallerThanHashLengthReversesSublist() throws Exception {
        createHash(1, 2, 3, 4);
        twist(2);
        verifyHash(2, 1, 3, 4);
    }

    @Test
    public void currentPositionIncreasesByLengthEachTwist() throws Exception {
        createHash(1, 2, 3, 4, 5, 6);
        twist(2);
        verifyHash(2, 1, 3, 4, 5, 6);

        twist(2);
        verifyHash(2, 1, 4, 3, 5, 6);
    }

    @Test
    public void currentPositionWrapsAroundToFront() throws Exception {
        createHash(1, 2);
        twist(2);
        verifyHash(2, 1);

        twist(2);
        verifyHash(1, 2);
    }

    @Test
    public void currentPositionWrapsAroundToFrontInLongerList() throws Exception {
        createHash(1, 2, 3, 4, 5, 6);
        twist(4);
        verifyHash(4, 3, 2, 1, 5, 6);

        twist(4);
        verifyHash(6, 5, 2, 1, 3, 4);
    }

    @Test
    public void currentPositionIncreasesByLengthAndSkipEachTwist() throws Exception {
        createHash(1, 2, 3, 4, 5, 6);
        twist(4);
        verifyHash(4, 3, 2, 1, 5, 6);

        twist(4);
        verifyHash(6, 5, 2, 1, 3, 4);

        twist(2);
        verifyHash(6, 5, 2, 3, 1, 4);

        twist(2);
        verifyHash(6, 2, 5, 3, 1, 4);
    }

    @Test
    public void mappingFunction() throws Exception {
        createHash(1, 2, 3, 4, 5, 6);
        // portion does not cross end of hash
        // index is not in flipped range
        knotHash.setCurrentPosition(2);
        assertEquals(0, knotHash.findIndexToSwap(0, 2));
        assertEquals(1, knotHash.findIndexToSwap(1, 2));
        assertEquals(4, knotHash.findIndexToSwap(4, 2));
        assertEquals(5, knotHash.findIndexToSwap(5, 2));
        // index is in flipped range
        assertEquals(3, knotHash.findIndexToSwap(2, 2));
        assertEquals(2, knotHash.findIndexToSwap(3, 2));
        // portion crosses end of hash
        // index is not in flipped range
        knotHash.setCurrentPosition(4);
        assertEquals(2, knotHash.findIndexToSwap(2, 4));
        assertEquals(3, knotHash.findIndexToSwap(3, 4));
        // index is in flipped range
        assertEquals(1, knotHash.findIndexToSwap(4, 4));
        assertEquals(0, knotHash.findIndexToSwap(5, 4));
        assertEquals(5, knotHash.findIndexToSwap(0, 4));
        assertEquals(4, knotHash.findIndexToSwap(1, 4));

        knotHash.setCurrentPosition(0);
        // index is in flipped range
        assertEquals(2, knotHash.findIndexToSwap(0, 3));
        assertEquals(1, knotHash.findIndexToSwap(1, 3));
        assertEquals(0, knotHash.findIndexToSwap(2, 3));
        // index is not in flipped range
        assertEquals(3, knotHash.findIndexToSwap(3, 3));
        assertEquals(4, knotHash.findIndexToSwap(4, 3));
        assertEquals(5, knotHash.findIndexToSwap(5, 3));
    }

    @Test
    public void puzzle_example() throws Exception {
        createHash(0, 1, 2, 3, 4);

        final Integer[] integers = {3, 4, 1, 5};
        twist(integers);

        verifyHash(3, 4, 2, 1, 0);
    }

    @Test
    public void puzzleInput() throws Exception {
        Integer[] initialValues = new Integer[256];
        for (int i = 0; i < 256; i++)
            initialValues[i] = i;
        createHash(initialValues);

        twist(PUZZLE_INPUT);

        assertEquals(23715, knotHash.getHash()[0] * knotHash.getHash()[1]);
    }

    private void createHash(Integer... hashValues) {
        knotHash = new DayTenKnotHash(hashValues);
    }

    @Test
    public void verifyIntegerToBinaryConversion() throws Exception {
        // 48 49 50 51 52
        createHash(IntStream.range(0, 60).boxed().toArray(Integer[]::new));
        //                          1   ,   2   ,   3

        knotHash.twist(new DayTenKnotHash.AsciiLengthsGenerator("1,2,3"));

        verifyHash(7, 6, 5, 4, 50, 51, 52, 53, 54, 55, 1, 2, 3, 49, 15, 58, 59, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 0, 56, 57, 14, 13, 12, 11, 10, 9, 8);
    }


    private void twist(Integer... integers) {
        String asString = new String(Arrays.stream(integers)
                .mapToInt(Integer::valueOf).toArray(), 0, integers.length);
        knotHash.twist(new DayTenKnotHash.AsciiLengthsGenerator(asString));
    }

    private void verifyHash(Integer... expectedHashValues) {
        assertArrayEquals(expectedHashValues, knotHash.getHash());
    }
}