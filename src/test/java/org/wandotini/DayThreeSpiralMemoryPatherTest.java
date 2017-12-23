package org.wandotini;

import org.junit.Ignore;
import org.junit.Test;
import org.wandotini.DayThreeSpiralMemoryPather.Grid;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DayThreeSpiralMemoryPatherTest {
    private DayThreeSpiralMemoryPather spiralMemoryPather = new DayThreeSpiralMemoryPather();

    @Test
    public void buildSquareToSquare1() throws Exception {
        verifyGrid(1,
                new int[]{1}
                );
    }

    @Test
    public void pathFromSquareOneIsZero() throws Exception {
        verifyPathDistance(1, 0);
    }

    @Test
    public void buildSquareToSquare2() throws Exception {
        verifyGrid(2,
                new int[]{0, 0, 0},
                new int[]{0, 1, 2},
                new int[]{0, 0, 0}
                );
    }

    @Test
    public void pathFromSquare2IsOne() throws Exception {
        verifyPathDistance(2, 1);
    }

    @Test
    public void buildSquareToSquare3() throws Exception {
        verifyGrid(3,
                new int[]{0, 0, 3},
                new int[]{0, 1, 2},
                new int[]{0, 0, 0}
        );
    }

    @Test
    public void buildSquareToSquare4And5() throws Exception {
        verifyGrid(4,
                new int[]{0, 4, 3},
                new int[]{0, 1, 2},
                new int[]{0, 0, 0}
        );
        verifyGrid(5,
                new int[]{5, 4, 3},
                new int[]{0, 1, 2},
                new int[]{0, 0, 0}
        );
    }

    @Test
    public void buildSquareToSquare6And7() throws Exception {
        verifyGrid(6,
                new int[]{5, 4, 3},
                new int[]{6, 1, 2},
                new int[]{0, 0, 0}
        );
        verifyGrid(7,
                new int[]{5, 4, 3},
                new int[]{6, 1, 2},
                new int[]{7, 0, 0}
        );
    }

    @Test
    public void buildSquareToSquare8And9() throws Exception {
        verifyGrid(8,
                new int[]{5, 4, 3},
                new int[]{6, 1, 2},
                new int[]{7, 8, 0}
        );
        verifyGrid(9,
                new int[]{5, 4, 3},
                new int[]{6, 1, 2},
                new int[]{7, 8, 9}
        );
    }

    @Test
    public void ThreeByThreeOrthagonalPaths() throws Exception {
        verifyPathDistance(8, 1);
        verifyPathDistance(4, 1);
        verifyPathDistance(2, 1);
        verifyPathDistance(6, 1);
    }

    @Test
    public void ThreeByThreeDiagonalPaths() throws Exception {
        verifyPathDistance(9, 2);
        verifyPathDistance(7, 2);
        verifyPathDistance(5, 2);
        verifyPathDistance(3, 2);
    }

    @Test
    public void buildSquareToSquare25() throws Exception {
        verifyGrid(25,
                new int[]{17, 16, 15, 14, 13},
                new int[]{18,  5,  4,  3, 12},
                new int[]{19,  6,  1,  2, 11},
                new int[]{20,  7,  8,  9, 10},
                new int[]{21, 22, 23, 24, 25}
        );
    }

    @Test
    public void buildSquareToSquare49() throws Exception {
        verifyGrid(49,
                new int[]{37, 36, 35, 34, 33, 32, 31},
                new int[]{38, 17, 16, 15, 14, 13, 30},
                new int[]{39, 18,  5,  4,  3, 12, 29},
                new int[]{40, 19,  6,  1,  2, 11, 28},
                new int[]{41, 20,  7,  8,  9, 10, 27},
                new int[]{42, 21, 22, 23, 24, 25, 26},
                new int[]{43, 44, 45, 46, 47, 48, 49}
        );
    }

    @Test
    public void puzzlePath() throws Exception {
        verifyPathDistance(277678, 475);
    }

    private void verifyGrid(int squares, int[]... expectedGrid) {
        assertThat(spiralMemoryPather.buildGridTo(squares), is(new Grid(expectedGrid)));
    }

    private void verifyPathDistance(int square, int distance) {
        assertThat(spiralMemoryPather.calcPathFrom(square), is(distance));
    }
}