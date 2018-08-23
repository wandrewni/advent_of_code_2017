package org.wandotini;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.wandotini.Day11HexGrid.Direction.*;

public class Day11HexGridAlt implements Day11HexGrid {

    private Coordinates coordinates;

    @Override
    public List<String> condensePath(String... path) {
        coordinates = new Coordinates();
        List<Direction> initialDirections = Arrays.stream(path).map(Direction::fromString).collect(Collectors.toList());
        initialDirections.forEach(this::add);
        System.out.println(String.format("final: %d, max: %d", coordinates.score(), coordinates.maxPath)); // 1524 for puzzle input
        return coordinates.toPath().stream().map(Direction::toString).collect(Collectors.toList());
    }

    private void add(Direction direction) {
        coordinates.move(direction);
    }

    @EqualsAndHashCode
    @ToString
    private static class Coordinates {
        private static final double HALF = 0.5;
        private final List<Direction> recreatedPath = new ArrayList<>();
        private double x, y;
        private int maxPath = 0;

        public void move(Direction direction) {
            switch (direction) {
                case NW:
                    x--;
                    y += HALF;
                    break;
                case N:
                    y++;
                    break;
                case NE:
                    x++;
                    y += HALF;
                    break;
                case SW:
                    x--;
                    y -= HALF;
                    break;
                case S:
                    y--;
                    break;
                case SE:
                    x++;
                    y -= HALF;
                    break;
            }

            maxPath = Math.max(maxPath, score());
        }

        public List<Direction> toPath() {
            if (x == 0 & y == 0)
                return Collections.emptyList();
            while (x > 0)
                if (y > 0)
                    undo(NE);
                else
                    undo(SE);
            while (x < 0)
                if (y > 0)
                    undo(NW);
                else
                    undo(SW);
            while (y > 0)
                undo(N);
            while (y < 0)
                undo(S);
            return recreatedPath;
        }

        private void undo(Direction direction) {
            recreatedPath.add(direction);
            move(opposite(direction));
        }

        private Direction opposite(Direction direction) {
            switch (direction) {
                case NW: return SE;
                case N: return S;
                case NE: return SW;
                case SW: return NE;
                case S: return N;
                case SE: return NW;
            }
            throw new IllegalStateException();
        }

        public int score() {
            int xSteps = (int) Math.abs(x);
            int yOnlySteps = (int) (Math.abs(y) - xSteps/2);
            return xSteps + yOnlySteps;
        }
    }
}
