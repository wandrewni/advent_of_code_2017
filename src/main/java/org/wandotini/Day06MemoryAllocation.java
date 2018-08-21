package org.wandotini;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import static java.util.Arrays.copyOf;

public class Day06MemoryAllocation {
    private final Integer[] banks;

    public Day06MemoryAllocation(Integer[] inputBanks) {
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
        Set<String> seenBankConfigurations = new HashSet<>();
        int steps = 0;
        while (seenBankConfigurations.add(createSimpleHash())) {
            reallocateOnce();
            steps++;
        }
        return steps;
    }

    private String createSimpleHash() {
        return Arrays.stream(banks)
                .map(String::valueOf)
                .reduce("", (a, b) -> a + b);
    }
}
