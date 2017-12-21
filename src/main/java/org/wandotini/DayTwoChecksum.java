package org.wandotini;

import java.util.Arrays;

public class DayTwoChecksum extends DayTwoChecksumCalculator {

    @Override
    protected int processRow(Integer[] numbers) {
        final MinMax minMax = new MinMax(numbers[0]);
        Arrays.stream(numbers).forEach(minMax::consider);
        return minMax.difference();
    }

    private class MinMax {
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
