package org.wandotini;

import org.junit.Test;
import org.wandotini.utils.TestUtils;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class Day05TrampolineJumpsPartTwoTest {

    @Test
    public void emptyPathReturns0() throws Exception {
        verifyNavigate(0);
    }

    @Test
    public void singleOneReturnsOne() throws Exception {
        verifyNavigate(1, 1);
    }

    @Test
    public void TwoOnesReturnsTwo() throws Exception {
        verifyNavigate(2, 1, 1);
    }

    @Test
    public void TwoLengthStartingWithTwoReturnsOne() throws Exception {
        verifyNavigate(1, 2, 1);
    }

    @Test
    public void OneLengthWithNegativeOneReturnsZero() throws Exception {
        verifyNavigate(1, -1);
    }

    @Test
    public void vistingASpotIncreasesItsOffset() throws Exception {
        verifyNavigate(2, 0);
        verifyNavigate(4, 0, -1);
        verifyNavigate(10, 0, 3, 0, 1, -3);
    }

    @Test
    public void puzzleInput() throws Exception {
        Integer[] jumps = TestUtils.readFileIntoReader("day_five_input.txt")
                .lines()
                .map(Integer::parseInt)
                .collect(toList())
                .toArray(new Integer[0]);

        verifyNavigate(29629538, jumps);
    }

    private void verifyNavigate(int expectedSteps, Integer... jumps) {
        assertThat(Day05TrampolineJumpsPartTwo.navigate(jumps), is(expectedSteps));
    }
}