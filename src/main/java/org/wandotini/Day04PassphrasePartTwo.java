package org.wandotini;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toList;

public class Day04PassphrasePartTwo extends Day04PassphraseValidator {

    @Override
    protected boolean validatePassphrase(String[] splitPassphrase) {
        List<String> deAnagrammedWords = Arrays.stream(splitPassphrase)
                .map(this::sortChars)
                .collect(toList());
        return countUniqueWords(deAnagrammedWords) == splitPassphrase.length;
    }

    private String sortChars(String string) {
        char[] chars = string.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

    private int countUniqueWords(List<String> sortedPassphraseWords) {
        Set<String> uniqueWords = new HashSet<>();
        uniqueWords.addAll(sortedPassphraseWords);
        return uniqueWords.size();
    }

}
