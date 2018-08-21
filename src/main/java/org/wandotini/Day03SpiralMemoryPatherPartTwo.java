package org.wandotini;

import java.util.Arrays;

import static org.wandotini.Day03SpiralMemoryPatherPartTwo.Direction.*;

public class Day03SpiralMemoryPatherPartTwo {
    private GridTraveler traveler;
    private int value;

    public Grid buildGridToFirstValueGreaterThan(int input) {
        value = 0;
        traveler = new GridTraveler(getGridSize(input));
        while (value <= input)
            value = traveler.visit();
        return new Grid(traveler.grid);
    }

    // grid is going to be n x n, where n is the closest odd integer >= to the square root of squares
    // so grid size could be 3x3, 5x5
    // note -- for a value of 1, we have to make a 3x3 grid to reach the first value larger than 1
    // also, as values of input get larger the square becomes unnecessarily large, as we reach larger values sooner
    // in the iteration.  this is probably fine.
    private int getGridSize(int input) {
        int n = (int) Math.ceil(Math.sqrt(input));
        return n % 2 == 0 ? n + 1 : n;
    }

    public int firstValueGreaterThan(int input) {
        buildGridToFirstValueGreaterThan(input);
        return value;
    }

    // mainly making a class to get a nicely formatted toString()
    public static class Grid {
        private int[][] rows;

        Grid(int[]... rows) {
            this.rows = rows;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Grid grid = (Grid) o;

            return Arrays.deepEquals(rows, grid.rows);
        }

        @Override
        public int hashCode() {
            return Arrays.deepHashCode(rows);
        }

        @Override
        public String toString() {
            return Arrays.stream(rows)
                    .map(row -> "\n" + Arrays.toString(row))
                    .reduce("", (a, b) -> a + b);
        }
    }

    enum Direction {
        LEFT, RIGHT, UP, DOWN
    }

    private class GridTraveler {
        int[][] grid;
        private int col;
        private int row;
        private Direction travelDir = RIGHT;
        private int upperLeftRingBound;
        private int lowerRightRingBound;

        public GridTraveler(int n) {
            if (n == 1)
                n = 3;
            grid = new int[n][n];
            row = col = n / 2;
            upperLeftRingBound = lowerRightRingBound = (n - 1) / 2;
        }

        private int visit() {
            int writtenValue = grid[row][col] = calcValue();
            if (completedRing() && upperLeftRingBound != 0)
                moveToNextRing();
            travelDir = getNextDir();
            updateCoords();
            return writtenValue;
        }

        private boolean completedRing() {
            return col == lowerRightRingBound && row == lowerRightRingBound;
        }

        public void updateCoords() {
            switch (travelDir) {
                case LEFT:
                    col--;
                    break;
                case RIGHT:
                    col++;
                    break;
                case UP:
                    row--;
                    break;
                case DOWN:
                    row++;
                    break;
            }
        }

        private Direction getNextDir() {
            switch (travelDir) {
                case LEFT:
                    if (col == upperLeftRingBound) return DOWN;
                case RIGHT:
                    if (col == lowerRightRingBound) return UP;
                case UP:
                    if (row == upperLeftRingBound) return LEFT;
                case DOWN:
                    if (row == lowerRightRingBound) return RIGHT;
            }
            return travelDir;
        }

        private void moveToNextRing() {
            upperLeftRingBound--;
            lowerRightRingBound++;
        }

        private int calcValue() {
            // special case for the middlemost square, which is initialized as 1
            if (upperLeftRingBound == lowerRightRingBound)
                return 1;
            int sum = getSumOfColumnNeighbors();
            if (notInBottomRow())
                sum += getSumOfRow(row + 1);
            if (notInTopRow())
                sum += getSumOfRow(row - 1);
            return sum;
        }

        private int getSumOfColumnNeighbors() {
            int sum = 0;
            if (notInLeftColumn())
                sum += grid[row][col - 1];
            if (notInRightColumn())
                sum += grid[row][col + 1];
            return sum;
        }

        private int getSumOfRow(int row) {
            int sum = 0;
            sum += grid[row][col];
            if (notInLeftColumn())
                sum += grid[row][col - 1];
            if (notInRightColumn())
                sum += grid[row][col + 1];
            return sum;
        }

        private boolean notInTopRow() {
            return row > 0;
        }

        private boolean notInBottomRow() {
            return row < lowerRightRingBound;
        }

        private boolean notInLeftColumn() {
            return col > 0;
        }

        private boolean notInRightColumn() {
            return col < lowerRightRingBound;
        }

    }
}
