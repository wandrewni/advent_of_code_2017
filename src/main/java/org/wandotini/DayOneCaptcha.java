package org.wandotini;

public class DayOneCaptcha extends DayOneCaptchaParser {
    protected int sumMatchingDigits(String s) {
        if (s.length() == 1)
            return 0;
        int sum = 0;
        for (int i = 0; i < s.length(); i++) {
            int nextChar = (i == s.length() - 1) ? 0 : (i + 1);
            if (s.charAt(i) == s.charAt(nextChar))
                sum += Integer.parseInt(s.substring(nextChar, nextChar + 1));
        }
        return sum;
    }
}
