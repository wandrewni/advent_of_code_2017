package org.wandotini;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DayTwoChecksum {
    public static int calcChecksum(String input) {
        return Arrays.stream(input.split("\n"))
                .mapToInt(DayTwoChecksum::getDifferenceForRow)
                .sum();
    }

    private static int getDifferenceForRow(String row) {
        List<Integer> numbers = Arrays.stream(row.split("\t"))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        return getMaxDifference(numbers);
    }

    private static int getMaxDifference(List<Integer> numbers) {
        final MinMax minMax = new MinMax(numbers.get(0));
        numbers.forEach(minMax::consider);
        return minMax.difference();
    }

    private static class MinMax {
        private int min;
        private int max;

        MinMax(int base) {
            max = min = base;
        }

        void consider(Integer number) {
            if (number > max)
                max = number;
            if (number < min)
                min = number;
        }

        private int difference() {
            return max - min;
        }
    }
}
