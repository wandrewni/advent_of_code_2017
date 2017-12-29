package org.wandotini;

import org.junit.Test;

import java.util.Arrays;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DaySixMemoryAllocationTest {

    private static final String PUZZLE_INPUT = "2\t8\t8\t5\t4\t2\t3\t1\t5\t5\t1\t2\t15\t13\t5\t14";

    @Test
    public void reallocateOnceWithNullOrEmptyInputReturnsEmptyArray() throws Exception {
        Integer[] expected = new Integer[]{};
        assertThat(new DaySixMemoryAllocation(null).reallocateOnce(), is(expected));
        assertThat(new DaySixMemoryAllocation(new Integer[]{}).reallocateOnce(), is(expected));
    }

    @Test
    public void reallocateOnceWithOneLengthArrayreallocateOncesCorrectly() throws Exception {
        assertThat(new DaySixMemoryAllocation(new Integer[]{1}).reallocateOnce(), is(new Integer[]{1}));
    }

    @Test
    public void reallocateOnceMovesFirstSpotToSecondSpot() throws Exception {
        assertThat(new DaySixMemoryAllocation(new Integer[]{1, 0}).reallocateOnce(), is(new Integer[]{0, 1}));
    }

    @Test
    public void reallocateOnceSelectsLargestBank() throws Exception {
        assertThat(new DaySixMemoryAllocation(new Integer[]{0, 1, 0}).reallocateOnce(), is(new Integer[]{0, 0, 1}));
    }

    @Test
    public void reallocateOnceSelectsFirstInstanceOfLargestBank() throws Exception {
        assertThat(new DaySixMemoryAllocation(new Integer[]{0, 1, 1, 0}).reallocateOnce(), is(new Integer[]{0, 0, 2, 0}));
    }

    @Test
    public void reallocateOnceDistributesAsManyAsOriginalSizeOfBank() throws Exception {
        assertThat(new DaySixMemoryAllocation(new Integer[]{0, 2, 0, 0}).reallocateOnce(), is(new Integer[]{0, 0, 1, 1}));
    }

    @Test
    public void reallocateOnceWrapsAroundEndOfArrayToBeginning() throws Exception {
        assertThat(new DaySixMemoryAllocation(new Integer[]{0, 0, 3, 0}).reallocateOnce(), is(new Integer[]{1, 1, 0, 1}));
    }

    @Test
    public void reallocateWithStartingConfigurationRepeatingReallocationsBackToStart() throws Exception {
        assertThat(new DaySixMemoryAllocation(new Integer[]{1}).reallocate(), is(1));
        assertThat(new DaySixMemoryAllocation(new Integer[]{1, 0}).reallocate(), is(2));
    }

    @Test
    public void reallocateWithStartingConfigurationElsewhereRepeatsUntilConfigurationFound() throws Exception {
        assertThat(new DaySixMemoryAllocation(new Integer[]{0, 2, 7, 0}).reallocate(), is(5));
        /* sequence
            0 2 7 0
            2 4 1 2
            3 1 2 3
            0 2 3 4
            1 3 4 1
            2 4 1 2
         */
    }

    @Test
    public void puzzleInput() throws Exception {
        assertThat(new DaySixMemoryAllocation(parsePuzzleInput()).reallocate(), is(3156));
    }

    private Integer[] parsePuzzleInput() {
        return Arrays.stream(PUZZLE_INPUT.split("\t"))
                .map(Integer::valueOf)
                .collect(toList())
                .toArray(new Integer[0]);
    }
}