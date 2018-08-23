package org.wandotini;

import java.util.List;

public interface Day11HexGrid {
    List<String> condensePath(String... path);

    enum Direction {
        NW, N, NE, SW, S, SE;

        static Direction fromString(String string) {
            return Direction.valueOf(string.toUpperCase());
        }

        public String toString() {
            return name().toLowerCase();
        }
    }
}
