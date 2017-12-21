package org.wandotini;

import static java.lang.Integer.parseInt;

public class DayOneCaptchaPartTwo {
    private static final String DIGITS = "\\d+";

    public static int parse(String s) {
        validateInput(s);
        return sumMatchingDigits(s);
    }

    private static int sumMatchingDigits(String s) {
        int sum = 0;
        for (int i = 0; i < s.length() / 2; i++)
            if (s.charAt(i) == s.charAt(s.length() / 2 + i))
                sum += 2 * parseInt(s.substring(i, i + 1));
        return sum;
    }

    private static void validateInput(String s) {
        if (s == null)
            throw new IllegalArgumentException("input must be non-null");
        if (!s.matches(DIGITS))
            throw new IllegalArgumentException("input must be a string of integer digits");
    }
}
