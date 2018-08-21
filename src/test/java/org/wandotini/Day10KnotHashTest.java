package org.wandotini;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class Day10KnotHashTest {
    private static final String PUZZLE_INPUT = "94,84,0,79,2,27,81,1,123,93,218,23,103,255,254,243";
    Day10KnotHash knotHash;

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
        verifyHash(2, 1, 3, 4, 5 ,6);

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
        verifyHash(4, 3, 2, 1, 5 ,6);

        twist(4);
        verifyHash(6, 5, 2, 1, 3, 4);
    }

    @Test
    public void currentPositionIncreasesByLengthAndSkipEachTwist() throws Exception {
        createHash(1, 2, 3, 4, 5, 6);
        twist(4);
        verifyHash(4, 3, 2, 1, 5 ,6);

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

        twist(3, 4, 1, 5);

        verifyHash(3, 4, 2, 1, 0);
    }

    @Test
    public void puzzleInput() throws Exception {
        Integer[] initialValues = IntStream.range(0, 256).boxed().toArray(Integer[]::new);
        createHash(initialValues);

        final Integer[] puzzleInputAsIntegers = Arrays.stream(PUZZLE_INPUT.split(","))
                .map(Integer::parseInt)
                .toArray(Integer[]::new);
        twist(puzzleInputAsIntegers);

        assertEquals(23715, knotHash.getHash()[0] * knotHash.getHash()[1]);
    }

    void createHash(Integer... hashValues) {
        knotHash = new Day10KnotHash(hashValues);
    }

    void twist(Integer... lengths) {
        knotHash.twist(new Day10KnotHash.IntegerLengthsGenerator(lengths));
    }

    void verifyHash(Integer... expectedHashValues) {
        assertArrayEquals(expectedHashValues, knotHash.getHash());
    }
}