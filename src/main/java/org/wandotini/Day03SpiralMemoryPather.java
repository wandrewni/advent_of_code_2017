package org.wandotini;

import java.util.Arrays;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Math.min;
import static org.wandotini.Day03SpiralMemoryPather.Direction.*;

public class Day03SpiralMemoryPather {

    private GridTraveler traveler;

    public Grid buildGridTo(int squares) {
        int i = 1;
        traveler = new GridTraveler(getGridSize(squares));
        while (i <= squares)
            traveler.visit(i++);
        return new Grid(traveler.grid);
    }

    // grid is going to be n x n, where n is the closest odd integer >= to the square root of squares
    // so grid size could be 1x1, 3x3, 5x5
    private int getGridSize(int squares) {
        int n = (int) Math.ceil(Math.sqrt(squares));
        return n % 2 == 0 ? n + 1 : n;
    }

    public int calcPathFrom(int square) {
        buildGridTo(square);
        return traveler.calcDistanceBackToOne();
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
        private int lastCol;
        private int lastRow;

        public GridTraveler(int n) {
            grid = new int[n][n];
            row = col = n / 2;
            upperLeftRingBound = lowerRightRingBound = (n - 1) / 2;
        }

        private void visit(int square) {
            grid[row][col] = square;
            if (completedRing() && upperLeftRingBound != 0)
                moveToNextRing();
            travelDir = getNextDir();
            updateCoords();
        }

        private boolean completedRing() {
            return col == lowerRightRingBound && row == lowerRightRingBound;
        }

        public void updateCoords() {
            lastRow = row;
            lastCol = col;
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

        public int calcDistanceBackToOne() {
            int distance = 0;
            int row = this.lastRow;
            int col = this.lastCol;
            while (grid[row][col] != 1) {
                int left, down, up, right;
                left = down = up = right = MAX_VALUE;
                if (col > 0)
                    left = grid[row][col - 1];
                if (row > 0)
                    up = grid[row - 1][col];
                if (col < lowerRightRingBound)
                    right = grid[row][col + 1];
                if (row < lowerRightRingBound)
                    down = grid[row + 1][col];
                left = resetEmptySquares(left);
                right = resetEmptySquares(right);
                down = resetEmptySquares(down);
                up = resetEmptySquares(up);
                int min = min(min(min(left, down), up), right);
                if (min == left)
                    col--;
                if (min == right)
                    col++;
                if (min == up)
                    row--;
                if (min == down)
                    row++;
                distance++;
            }
            return distance;
        }

        private int resetEmptySquares(int square) {
            return square == 0 ? MAX_VALUE : square;
        }
    }
}
