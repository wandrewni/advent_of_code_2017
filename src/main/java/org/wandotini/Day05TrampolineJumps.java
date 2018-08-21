package org.wandotini;

public class Day05TrampolineJumps {
    public static int navigate(Integer[] jumps) {
        int offset = 0;
        int steps = 0;
        while (offset < jumps.length && offset > -1) {
            steps++;
            offset += jumps[offset]++;
        }
        return steps;
    }
}
