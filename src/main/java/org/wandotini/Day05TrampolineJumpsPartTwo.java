package org.wandotini;

public class Day05TrampolineJumpsPartTwo {
    public static int navigate(Integer[] jumps) {
        int offset = 0;
        int steps = 0;
        while (offset < jumps.length && offset > -1) {
            steps++;
            Integer foundOffset = jumps[offset];
            if (foundOffset > 2)
                jumps[offset]--;
            else
                jumps[offset]++;
            offset += foundOffset;
        }
        return steps;
    }
}
