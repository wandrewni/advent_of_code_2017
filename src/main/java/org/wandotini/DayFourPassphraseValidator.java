package org.wandotini;

import java.util.List;

public abstract class DayFourPassphraseValidator {
    public boolean isValid(String passphrase) {
        String[] splitPassphrase = passphrase.split(" ");
        return splitPassphrase.length == 1 || validatePassphrase(splitPassphrase);
    }

    protected abstract boolean validatePassphrase(String[] splitPassphrase);

    public int countValidPassphrases(List<String> passphrases) {
        if (passphrases == null)
            return 0;
        else
            return passphrases.stream()
                    .map(passphrase -> isValid(passphrase) ? 1 : 0)
                    .reduce((a, b) -> a + b)
                    .orElse(0);
    }
}
