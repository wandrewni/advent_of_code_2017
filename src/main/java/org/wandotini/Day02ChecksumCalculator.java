package org.wandotini;

import java.util.Arrays;

public abstract class Day02ChecksumCalculator {
    public int calcChecksum(String input) {
        return Arrays.stream(input.split("\n"))
                .mapToInt(this::processRows)
                .sum();
    }

    private int processRows(String row) {
        return processRow(parseRow(row));
    }

    private Integer[] parseRow(String row) {
        return Arrays.stream(row.split("\t"))
                    .map(Integer::parseInt)
                    .toArray(Integer[]::new);
    }

    protected abstract int processRow(Integer[] numbers);
}
