package org.wandotini;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DayFourTrampolineJumpsTest {

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
        verifyNavigate(5, 0, 3, 0, 1, -3);
    }

    @Test
    public void puzzleInput() throws Exception {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("day_five_input.txt");
        Integer[] jumps = new BufferedReader(new InputStreamReader(resourceAsStream))
                .lines()
                .map(Integer::parseInt)
                .collect(toList())
                .toArray(new Integer[0]);

        verifyNavigate(372139, jumps);
    }

    private void verifyNavigate(int expectedSteps, Integer... jumps) {
        assertThat(DayFourTrampolineJumps.navigate(jumps), is(expectedSteps));
    }
}