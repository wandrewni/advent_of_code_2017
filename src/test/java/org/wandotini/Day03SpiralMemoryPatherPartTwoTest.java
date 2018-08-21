package org.wandotini;

import org.junit.Test;
import org.wandotini.Day03SpiralMemoryPatherPartTwo.Grid;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day03SpiralMemoryPatherPartTwoTest {
    private Day03SpiralMemoryPatherPartTwo spiralMemoryPather = new Day03SpiralMemoryPatherPartTwo();

    @Test
    public void buildSquareToInput1() throws Exception {
        verifyGrid(1,
                new int[]{0, 0, 2},
                new int[]{0, 1, 1},
                new int[]{0, 0, 0}
                );
    }

    @Test
    public void buildSquareToInput2() throws Exception {
        verifyGrid(2,
                new int[]{0, 4, 2},
                new int[]{0, 1, 1},
                new int[]{0, 0, 0}
                );
    }

    @Test
    public void buildSquareToInput3() throws Exception {
        verifyGrid(3,
                new int[]{0, 4, 2},
                new int[]{0, 1, 1},
                new int[]{0, 0, 0}
        );
    }

    @Test
    public void buildSquareToInput4And5() throws Exception {
        verifyGrid(4,
                new int[]{5, 4, 2},
                new int[]{0, 1, 1},
                new int[]{0, 0, 0}
        );
        verifyGrid(5,
                new int[]{5, 4, 2},
                new int[]{10, 1, 1},
                new int[]{0, 0, 0}
        );
    }

    @Test
    public void buildSquareToInput6And7() throws Exception {
        verifyGrid(6,
                new int[]{5, 4, 2},
                new int[]{10, 1, 1},
                new int[]{0, 0, 0}
        );
        verifyGrid(7,
                new int[]{5, 4, 2},
                new int[]{10, 1, 1},
                new int[]{0, 0, 0}
        );
    }

    @Test
    public void buildSquareToInput8And9() throws Exception {
        verifyGrid(8,
                new int[]{5, 4, 2},
                new int[]{10, 1, 1},
                new int[]{0, 0, 0}
        );
        verifyGrid(9,
                new int[]{5, 4, 2},
                new int[]{10, 1, 1},
                new int[]{0, 0, 0}
        );
    }

    @Test
    public void buildSquareToInput24And25() throws Exception {
        verifyGrid(24,
                new int[]{0, 0, 0, 0, 0},
                new int[]{0, 5, 4, 2, 0},
                new int[]{0,10, 1, 1, 0},
                new int[]{0,11,23,25, 0},
                new int[]{0, 0, 0, 0, 0}
        );
        verifyGrid(25,
                new int[]{0, 0, 0, 0, 0},
                new int[]{0, 5, 4, 2, 0},
                new int[]{0,10, 1, 1, 0},
                new int[]{0,11,23,25,26},
                new int[]{0, 0, 0, 0, 0}
        );
    }

    @Test
    public void calcFirstValueGreaterThanInput() throws Exception {
        assertThat(spiralMemoryPather.firstValueGreaterThan(1), is (2));
        assertThat(spiralMemoryPather.firstValueGreaterThan(24), is (25));
        assertThat(spiralMemoryPather.firstValueGreaterThan(8), is (10));

        // puzzle input
        assertThat(spiralMemoryPather.firstValueGreaterThan(277678), is(279138));
    }

    private void verifyGrid(int squares, int[]... expectedGrid) {
        assertThat("expectation for " + squares + " incorrect", spiralMemoryPather.buildGridToFirstValueGreaterThan(squares), is(new Grid(expectedGrid)));
    }

}