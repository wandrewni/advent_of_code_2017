package org.wandotini;

public class DayOneCaptcha {

    private static final String DIGITS = "\\d+";

    public static int parse(String s) {
        validateInput(s);
        if (s.length() == 1)
            return 0;
        return sumMatchingDigits(s);
    }

    private static int sumMatchingDigits(String s) {
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            int nextChar = i == s.length() - 1 ? 0 : i + 1;
            if (s.charAt(i) == s.charAt(nextChar))
                sum += Integer.parseInt(s.substring(nextChar, nextChar + 1));
        }
        return sum;
    }

    private static void validateInput(String s) {
        if (s == null)
            throw new IllegalArgumentException("input must be non-null");
        if (!s.matches(DIGITS))
            throw new IllegalArgumentException("input must be a string of integer digits");
    }
}
