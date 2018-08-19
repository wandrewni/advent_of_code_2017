package org.wandotini;

import org.junit.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DayTenKnotHashPartTwoTest extends DayTenKnotHashTest {
    private static final String PUZZLE_INPUT = "94,84,0,79,2,27,81,1,123,93,218,23,103,255,254,243";

    @Test
    public void verifyIntegerToBinaryConversion() throws Exception {
        // 49 44 50 44 51
        // 1   ,   2   ,   3
        final Integer[] expectedHash = {7, 6, 5, 4, 50, 51, 52, 53, 54, 55, 1, 2, 3, 49, 15, 58, 59, 48, 47, 46, 45, 44, 43, 42, 41, 40, 39, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 0, 56, 57, 14, 13, 12, 11, 10, 9, 8};

        createHash(IntStream.range(0, 60).boxed().toArray(Integer[]::new));
        knotHash.twist(new DayTenKnotHash.AsciiLengthsGenerator("1,2,3"));
        verifyHash(expectedHash);

        createHash(IntStream.range(0, 60).boxed().toArray(Integer[]::new));
        //   1  ,  2  ,  3
        knotHash.twist(new DayTenKnotHash.IntegerLengthsGenerator(49, 44, 50, 44, 51));
        verifyHash(expectedHash);
    }

    @Test
    public void hashWithExtraSequence() throws Exception {
        final Integer[] expectedHash = {16, 17, 15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 50, 62, 61, 60, 59, 58, 57, 56, 55, 54, 1, 2, 3, 49, 30, 22, 23, 24, 25, 26, 27, 28, 29, 72, 71, 0, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 74, 66, 67, 68, 69, 70, 53, 52, 51, 63, 64, 65, 73, 21, 20, 19, 18};
        createHash(IntStream.range(0, 75).boxed().toArray(Integer[]::new));
        //   1  ,  2  ,  3 + additional lengths
        knotHash.twist(new DayTenKnotHash.IntegerLengthsGenerator(49, 44, 50, 44, 51, 17, 31, 73, 47, 23));
        verifyHash(expectedHash);

        createHash(IntStream.range(0, 75).boxed().toArray(Integer[]::new));

        knotHash.twist(new DayTenKnotHash.AsciiLengthsGeneratorWithSuffix("1,2,3"));
        verifyHash(expectedHash);
    }

    @Test
    public void twistMultipleRoundsBasedOnIterationSetting() throws Exception {
        final Integer[] inputHash = {0, 1, 2, 3, 4};
        final Integer[] lengths = {3, 4, 1, 5};
        final Integer[] expectedHash = {0, 4, 3, 1, 2};

        createHash(inputHash);
        twist(lengths);
        twist(lengths);
        twist(lengths);
        verifyHash(expectedHash);

        createHash(inputHash);
        knotHash.setRepititions(3);
        twist(lengths);
        verifyHash(expectedHash);
    }

    @Test
    public void withBitWiseOrDensifierProducesCorrectHash() throws Exception {
        createHash(IntStream.range(0, 32).boxed().toArray(Integer[]::new));
        knotHash.setRepititions(64);
        knotHash.setDensifier(new DayTenKnotHash.BitWiseOrDensifier());
        knotHash.twist(new DayTenKnotHash.IntegerLengthsGenerator(2, 5, 10, 1, 2));
        // predensified hash: 19, 13, 5, 30, 10, 17, 15, 0, 9, 21, 20, 31, 8, 14, 25, 2, 29, 28, 18, 7, 27, 22, 12, 6, 1, 23, 11, 16, 4, 26, 24, 3

        //19^ 13^ 5^ 30^ 10^ 17^ 15^ 0^ 9^ 21^ 20^ 31^ 8^ 14^ 25^ 2 = 27
        //29^ 28^ 18^ 7^ 27^ 22^ 12^ 6^ 1^ 23^ 11^ 16^ 4^ 26^ 24^ 3 = 27

        verifyHash(27, 27);

    }

    @Test
    public void translateHashToString() throws Exception {
        final Integer[] inputHash = {20, 25, 15, 10, 94};
        final Integer[] lengths = {3, 4, 1, 5};
                                        // 0a, 5e, 0f, 19, 14
        final Integer[] expectedHash = {10, 94, 15, 25, 20};

        createHash(inputHash);
        twist(lengths);
        verifyHash(expectedHash);

        verifyHashString("0a5e0f1914");
    }

    @Test
    public void finalExampleHashes() throws Exception {
        verifyStandardHash("","a2582a3a0e66e6e86e3812dcb672a272");
        verifyStandardHash("AoC 2017", "33efeb34ea91902bb2f59c9920caa6cd");
        verifyStandardHash("1,2,3", "3efbe78a8d82f29979031a4aa0b16a9d");
        verifyStandardHash("1,2,4", "63960835bcdc130f0b66d7ff4f6a5a8e");
    }

    @Test
    public void puzzleInput() throws Exception {
        verifyStandardHash(PUZZLE_INPUT, "541dc3180fd4b72881e39cf925a50253");
    }

    private void verifyStandardHash(String lengthString, String expectedHash) {
        Integer[] initialValues = IntStream.range(0, 256).boxed().toArray(Integer[]::new);
        createHash(initialValues);
        knotHash.setRepititions(64);
        knotHash.setDensifier(new DayTenKnotHash.BitWiseOrDensifier());
        knotHash.twist(new DayTenKnotHash.AsciiLengthsGeneratorWithSuffix(lengthString));
        verifyHashString(expectedHash);
    }

    private void verifyHashString(String expected) {
        assertThat(knotHash.toHashString(), is(expected));
    }

    void twist(Integer... integers) {
        String asString = new String(Arrays.stream(integers)
                .mapToInt(Integer::valueOf).toArray(), 0, integers.length);
        knotHash.twist(new DayTenKnotHash.AsciiLengthsGenerator(asString));
    }
}
