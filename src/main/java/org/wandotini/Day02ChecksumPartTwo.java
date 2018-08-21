package org.wandotini;

public class Day02ChecksumPartTwo extends Day02ChecksumCalculator {
    protected int processRow(Integer[] numbers) {
        for (int i = 0; i < numbers.length; i++)
            for (int j = 0; j < numbers.length; j++)
                if (i != j && numbers[i] % numbers[j] == 0)
                    return numbers[i] / numbers[j];
        return 0;
    }
}
