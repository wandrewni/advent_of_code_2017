package org.wandotini;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

public class DayFourPassphrases {
    public static boolean validate(String passphrase) {
        String[] splitPassphrase = passphrase.split(" ");
        if (splitPassphrase.length == 1)
            return true;
        else
            return countUniqueWords(splitPassphrase) == splitPassphrase.length;
    }

    private static int countUniqueWords(String[] splitPassphrase) {
        Set<String> uniqueWords = new HashSet<>();
        uniqueWords.addAll(asList(splitPassphrase));
        return uniqueWords.size();
    }

    public static int countValidPassphrases(List<String> passphrases) {
        if (passphrases == null)
            return 0;
        else
            return passphrases.stream()
                    .map(passphrase -> validate(passphrase) ? 1 : 0)
                    .reduce((a, b) -> a + b)
                    .orElse(0);
    }
}
