package org.wandotini;

import java.util.Arrays;

public class DayTwoChecksumPartTwo {
    public static int calcChecksum(String input) {
        return Arrays.stream(input.split("\n"))
                .mapToInt(DayTwoChecksumPartTwo::getEvenlyDivisibleDivision)
                .sum();
    }

    private static int getEvenlyDivisibleDivision(String row) {
        Integer[] numbers = parseRow(row);
        for (int i = 0; i < numbers.length; i++)
            for (int j = 0; j < numbers.length; j++)
                if (i != j && numbers[i] % numbers[j] == 0)
                    return numbers[i] / numbers[j];
        return 0;
    }

    private static Integer[] parseRow(String row) {
        return Arrays.stream(row.split("\t"))
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);
    }
}
