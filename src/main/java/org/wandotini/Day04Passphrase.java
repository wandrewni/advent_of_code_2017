package org.wandotini;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

public class Day04Passphrase extends Day04PassphraseValidator {

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
