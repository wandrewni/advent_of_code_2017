package org.wandotini;

import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.wandotini.Day11HexGrid.Direction.*;

public class Day11HexGrid {
    public List<String> condensePath(String... path) {
        return new PathCondenser(path).buildPath();
    }

    private class PathCondenser {
        private List<PathStep> path;

        PathCondenser(String... path) {
            this.path = Stream.of(path)
                    .map(Day11HexGrid.PathStep::parse)
                    .collect(Collectors.toList());
        }

        List<String> buildPath() {
            removeOpposites();
            consolidateDiagonalVerticalPairs();
            return path.stream().map(PathStep::toString).collect(Collectors.toList());
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

        public Direction getDirection() {
            return direction;
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

        public abstract void addDirectionCondensingPairs(List<PathStep> finalPath);

        void replace(List<PathStep> finalPath, Direction toRemove, Direction toAdd) {
            finalPath.remove(PathStep.of(toRemove));
            finalPath.add(PathStep.of(toAdd));
        }

        void condenseIfPossible(List<PathStep> finalPath, Direction condensablePartner, Direction condensableTo) {
            if (finalPath.contains(of(condensablePartner)))
                replace(finalPath, condensablePartner, condensableTo);
            else
                finalPath.add(this);
        }

        void condenseWithTwoPossibilities(List<PathStep> finalPath,
                                          Direction condensingPair1, Direction condensableToOne,
                                          Direction condensingPartnerTwo, Direction condensableToTwo) {
            if (finalPath.contains(of(condensingPair1)))
                replace(finalPath, condensingPair1, condensableToOne);
            else if (finalPath.contains(of(condensingPartnerTwo)))
                replace(finalPath, condensingPartnerTwo, condensableToTwo);
            else
                finalPath.add(this);
        }

        private static class Northeast extends PathStep {
            public Northeast() {
                super(NE);
            }

            @Override
            protected Direction getOpposite() {
                return SW;
            }

            @Override
            public void addDirectionCondensingPairs(List<PathStep> finalPath) {
                condenseIfPossible(finalPath, S, SE);
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

            @Override
            public void addDirectionCondensingPairs(List<PathStep> finalPath) {
                condenseIfPossible(finalPath, S, SW);
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

            @Override
            public void addDirectionCondensingPairs(List<PathStep> finalPath) {
                condenseWithTwoPossibilities(finalPath, SW, NW, SE, NE);
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

            @Override
            public void addDirectionCondensingPairs(List<PathStep> finalPath) {
                condenseIfPossible(finalPath, N, NW);
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
            public void addDirectionCondensingPairs(List<PathStep> finalPath) {
                condenseWithTwoPossibilities(finalPath, NW, SW, NE, SE);
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

            @Override
            public void addDirectionCondensingPairs(List<PathStep> finalPath) {
                condenseIfPossible(finalPath, N, NE);
            }
        }
    }

    enum Direction {
        NW, N, NE, SW, S, SE
    }
}
