package org.wandotini;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.wandotini.Day11HexGrid.Direction.*;

public class Day11HexGridPostReducer implements Day11HexGrid {
    @Override
    public List<String> condensePath(String... path) {
        final List<PathStep> condensedPath = new PathCondenser(path).buildPath();
        return condensedPath.stream()
                .map(PathStep::toString)
                .collect(Collectors.toList());
    }

    private class PathCondenser {
        private List<PathStep> path;
        PathCondenser(String... path) {
            this.path = Stream.of(path)
                    .map(Day11HexGridPostReducer.PathStep::parse)
                    .collect(Collectors.toList());
        }

        List<PathStep> buildPath() {
            removeOpposites();
            consolidateDiagonalVerticalPairs();
            return path;
        }

        private void removeOpposites() {
            List<PathStep> filteredPath = new ArrayList<>();
            for (PathStep direction : path)
                direction.removeOppositeOrAddDirection(filteredPath);
            this.path = new ArrayList<>(filteredPath);
        }

        private void consolidateDiagonalVerticalPairs() {
            List<PathStep> filteredPath = new ArrayList<>();
            for (PathStep direction : path)
                direction.addDirectionCondensingPairs(filteredPath);
            path = new ArrayList<>(filteredPath);
        }

    }

    public static abstract class PathStep {
        private final Direction direction;
        private static final Map<Direction, PathStep> steps = ImmutableMap.<Direction, PathStep>builder()
                .put(NE, new Northeast())
                .put(NW, new Northwest())
                .put(N, new North())
                .put(SW, new Southwest())
                .put(S, new South())
                .put(SE, new Southeast())
                .build();

        public PathStep(Direction direction) {
            this.direction = direction;
        }

        public static PathStep parse(String direction) {
            return steps.get(Direction.valueOf(direction.toUpperCase()));
        }

        public static PathStep of(Direction direction) {
            return steps.get(direction);
        }

        public String toString() {
            return direction.toString().toLowerCase();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PathStep pathStep = (PathStep) o;

            return direction == pathStep.direction;
        }

        @Override
        public int hashCode() {
            return direction.hashCode();
        }

        private void removeOppositeOrAddDirection(List<PathStep> path) {
            final boolean removedOpposite = path.remove(PathStep.of(getOpposite()));
            if (!removedOpposite)
                path.add(this);
        }

        protected abstract Direction getOpposite();

        public void addDirectionCondensingPairs(List<PathStep> finalPath) {
            condenseIfPossible(finalPath, getCondensablePairs());
        }

        void condenseIfPossible(List<PathStep> finalPath, CondensablePair... possibleCondensings) {
            for (CondensablePair condensablePair : possibleCondensings)
                if (condensablePair.canCondense(finalPath)) {
                    condensablePair.condense(finalPath);
                    return;
                }
            finalPath.add(this);
        }

        abstract CondensablePair[] getCondensablePairs();

        private static class Northeast extends PathStep {
            public Northeast() {
                super(NE);
            }

            @Override
            protected Direction getOpposite() {
                return SW;
            }

            CondensablePair[] getCondensablePairs() {
                return new CondensablePair[]{new CondensablePair(S, SE)};
            }

        }

        private static class Northwest extends PathStep {
            public Northwest() {
                super(NW);
            }

            @Override
            protected Direction getOpposite() {
                return SE;
            }

            CondensablePair[] getCondensablePairs() {
                return new CondensablePair[]{new CondensablePair(S, SW)};
            }
        }

        private static class North extends PathStep {
            public North() {
                super(N);
            }

            @Override
            protected Direction getOpposite() {
                return S;
            }

            CondensablePair[] getCondensablePairs() {
                return new CondensablePair[]{new CondensablePair(SW, NW), new CondensablePair(SE, NE)};
            }

        }

        private static class Southwest extends PathStep {
            public Southwest() {
                super(SW);
            }

            @Override
            protected Direction getOpposite() {
                return NE;
            }

            CondensablePair[] getCondensablePairs() {
                return new CondensablePair[]{new CondensablePair(N, NW)};
            }
        }

        private static class South extends PathStep {
            public South() {
                super(S);
            }

            @Override
            protected Direction getOpposite() {
                return N;
            }

            @Override
            protected CondensablePair[] getCondensablePairs() {
                return new CondensablePair[]{new CondensablePair(NW, SW), new CondensablePair(NE, SE)};
            }
        }

        private static class Southeast extends PathStep {
            public Southeast() {
                super(SE);
            }

            @Override
            protected Direction getOpposite() {
                return NW;
            }

            CondensablePair[] getCondensablePairs() {
                return new CondensablePair[]{new CondensablePair(N, NE)};
            }
        }
    }

    public static class CondensablePair {
        private final Direction condensableWith;
        private final Direction condensableTo;

        public CondensablePair(Direction condensableWith, Direction condensableTo) {
            this.condensableWith = condensableWith;
            this.condensableTo = condensableTo;
        }

        public Direction getCondensableWith() {
            return condensableWith;
        }

        public Direction getCondensableTo() {
            return condensableTo;
        }

        boolean canCondense(List<PathStep> finalPath) {
            return finalPath.contains(PathStep.of(getCondensableWith()));
        }

        void condense(List<PathStep> finalPath) {
            finalPath.remove(PathStep.of(getCondensableWith()));
            finalPath.add(PathStep.of(getCondensableTo()));
        }
    }
}
