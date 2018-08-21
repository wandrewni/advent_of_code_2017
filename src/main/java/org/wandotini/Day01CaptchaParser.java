package org.wandotini;

public abstract class Day01CaptchaParser {
    private static final String DIGITS = "\\d+";

    public int parse(String s) {
        validateInput(s);

        return sumMatchingDigits(s);
    }

    protected abstract int sumMatchingDigits(String s);

    private void validateInput(String s) {
        if (s == null)
            throw new IllegalArgumentException("input must be non-null");
        if (!s.matches(DIGITS))
            throw new IllegalArgumentException("input must be a string of integer digits");
    }
}
