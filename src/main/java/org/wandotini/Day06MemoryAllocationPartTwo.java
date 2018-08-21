package org.wandotini;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.copyOf;

public class Day06MemoryAllocationPartTwo {
    private final Integer[] banks;

    public Day06MemoryAllocationPartTwo(Integer[] inputBanks) {
        if (inputBanks == null)
            banks = new Integer[0];
        else
            banks = copyOf(inputBanks, inputBanks.length);
    }

    public Integer[] reallocateOnce() {
        if (banks.length < 2)
            return banks;

        int index = findIndexOfMaxBank(banks);
        redistribute(index, banks[index]);
        return banks;
    }


    private int findIndexOfMaxBank(Integer[] banks) {
        int index = 0;
        int max = banks[0];
        for (int i = 1; i < banks.length; i++)
            if (banks[i] > max) {
                max = banks[i];
                index = i;
            }
        return index;
    }

    private void redistribute(int index, int amount) {
        banks[index++] = 0;
        while (amount > 0) {
            if (index == banks.length)
                index = 0;
            banks[index++]++;
            amount--;
        }
    }

    public int reallocate() {
        if (banks.length < 2)
            return 1;
        Map<String, Configuration> seenBankConfigurations = new HashMap<>();
        int step = 0;
        while (true) {
            final Configuration previousInstance = seenBankConfigurations.put(createSimpleHash(), new Configuration(copyOf(banks, banks.length), step));
            if (previousInstance != null)
                return step - previousInstance.step;
            reallocateOnce();
            step++;
        }
    }

    private String createSimpleHash() {
        return Arrays.stream(banks)
                .map(String::valueOf)
                .reduce("", (a, b) -> a + b);
    }

    class Configuration {
        public Configuration(Integer[] banks, int step) {
            this.banks = banks;
            this.step = step;
        }

        private Integer[] banks;
        private int step;
    }
}
