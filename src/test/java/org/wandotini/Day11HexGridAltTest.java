package org.wandotini;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class Day11HexGridAltTest extends Day11HexGridTest {
    @Override
    @Before
    public void setUp() throws Exception {
        grid = new Day11HexGridAlt();
    }

    @Test
    public void emptyPathReturnsEmptyPath() throws Exception {
        super.emptyPathReturnsEmptyPath();
    }

    @Test
    public void singleStepPathReturnsInput() throws Exception {
        super.singleStepPathReturnsInput();
    }

    @Test
    public void twoStepPathWithContinuingDirectionsReturnsInput() throws Exception {
        super.twoStepPathWithContinuingDirectionsReturnsInput();
    }

    @Test
    public void twoStepPathWithOpposingDirectionsReturnsEmptyPath() throws Exception {
        super.twoStepPathWithOpposingDirectionsReturnsEmptyPath();
    }

    @Test
    public void firstMatchedPairIsRemovedFromThePath() throws Exception {
        super.firstMatchedPairIsRemovedFromThePath();
    }

    @Test
    public void allMatchedPairsAreRemovedFromThePath() throws Exception {
        super.allMatchedPairsAreRemovedFromThePath();
    }

    @Test
    public void handlesDiagonalsCombinedWithVerticals() throws Exception {
        super.handlesDiagonalsCombinedWithVerticals();
    }

    @Test
    public void puzzleInput() throws Exception {
        super.puzzleInput();
    }
}
