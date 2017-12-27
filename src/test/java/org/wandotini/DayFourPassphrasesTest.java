package org.wandotini;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class DayFourPassphrasesTest {

    @Test
    public void oneWordPassphraseIsValid() throws Exception {
        assertValid("aa");
    }

    @Test
    public void twoWordPassphraseIsValid() throws Exception {
        assertValid("aa bb");
    }

    @Test
    public void twoWordPassPhraseWithRepeatingWordsIsInvalid() throws Exception {
        assertInvalid("aa aa");
    }

    @Test
    public void twoWordPassPhraseWithNearlyRepeatingWordsIsValid() throws Exception {
        assertValid("aa aaa");
    }

    @Test
    public void threeWordPassPhraseWithRepeatingWordsIsInvalid() throws Exception {
        assertInvalid("bb aa aa");
    }

    @Test
    public void validPassphrasesWithEmptyListIs0() throws Exception {
        assertThat(DayFourPassphrases.countValidPassphrases(emptyList()), is(0));
        assertThat(DayFourPassphrases.countValidPassphrases(null), is(0));
    }

    @Test
    public void validPassphrasesWithOneValidPassphraseIs1() throws Exception {
        verifyValidCount(1, "aa");
    }

    @Test
    public void validPassphrasesWithNoValidPassphraseIs() throws Exception {
        verifyValidCount(0, "aa aa");
    }

    @Test
    public void severalPassphrasesCalculatedCorrectly() throws Exception {
        verifyValidCount(2, "aa aa", "cc cc", "aa bb cc", "aa dd", "ab cc ab");
    }

    @Test
    public void puzzleInputCalculatedCorrectly() throws Exception {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("day_four_part_one_input.txt");
        String[] passphrases = new BufferedReader(new InputStreamReader(resourceAsStream))
                .lines()
                .collect(toList()).toArray(new String[0]);
        verifyValidCount(386, passphrases);
    }

    private void verifyValidCount(int numValid, String... passphrases) {
        assertThat(DayFourPassphrases.countValidPassphrases(asList(passphrases)), is(numValid));
    }

    private void assertValid(String input) {
        assertThat(DayFourPassphrases.validate(input), is(true));
    }

    private void assertInvalid(String input) {
        assertThat(DayFourPassphrases.validate(input), is(false));
    }
}