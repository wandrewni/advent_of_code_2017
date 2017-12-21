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
        int min = numbers.get(0);
        int max = numbers.get(0);
        for (Integer number : numbers) {
            if (number > max)
                max = number;
            if (number < min)
                min = number;
        }
        return max - min;
    }
}
