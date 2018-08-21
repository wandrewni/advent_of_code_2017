package org.wandotini;

import static java.lang.Integer.parseInt;

public class Day01CaptchaPartTwo extends Day01CaptchaParser {
    protected int sumMatchingDigits(String s) {
        int sum = 0;
        for (int i = 0; i < s.length() / 2; i++)
            if (s.charAt(i) == s.charAt(s.length() / 2 + i))
                sum += 2 * parseInt(s.substring(i, i + 1));
        return sum;
    }
}
