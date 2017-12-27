package org.wandotini;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

public class DayFourPassphrase extends DayFourPassphraseValidator {

    @Override
    protected boolean validatePassphrase(String[] splitPassphrase) {
        return countUniqueWords(splitPassphrase) == splitPassphrase.length;
    }

    private int countUniqueWords(String[] splitPassphrase) {
        Set<String> uniqueWords = new HashSet<>();
        uniqueWords.addAll(asList(splitPassphrase));
        return uniqueWords.size();
    }

}
