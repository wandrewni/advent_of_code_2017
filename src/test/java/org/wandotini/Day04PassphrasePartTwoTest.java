package org.wandotini;

import org.junit.Test;
import org.wandotini.util.TestUtils;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day04PassphrasePartTwoTest {

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
    public void twoWordPassPhraseWithAnagramsIsInvalid() throws Exception {
        assertInvalid("ba ab");
    }

    @Test
    public void validPassphrasesWithEmptyListIs0() throws Exception {
        assertThat(count(emptyList()), is(0));
        assertThat(count(null), is(0));
    }

    @Test
    public void validPassphrasesWithOneValidPassphraseIs1() throws Exception {
        verifyValidCount(1, "aa");
    }

    @Test
    public void validPassphrasesWithNoValidPassphraseIs() throws Exception {
        verifyValidCount(0, "ba ab");
    }

    @Test
    public void severalPassphrasesCalculatedCorrectly() throws Exception {
        verifyValidCount(3, "ab ba", "ca cc", "aa bb cc", "aa dd", "ab cc ab");
    }

    @Test
    public void puzzleInputCalculatedCorrectly() throws Exception {
        String[] passphrases = TestUtils.readFileIntoReader("day_04_input.txt")
                .lines()
                .collect(toList()).toArray(new String[0]);
        verifyValidCount(208, passphrases);
    }

    private void verifyValidCount(int numValid, String... passphrases) {
        assertThat(count(asList(passphrases)), is(numValid));
    }


    private void assertValid(String input) {
        assertThat(validate(input), is(true));
    }


    private void assertInvalid(String input) {
        assertThat(validate(input), is(false));
    }

    private int count(List<String> passphrases) {
        return new Day04PassphrasePartTwo().countValidPassphrases(passphrases);
    }
    private boolean validate(String input) {
        return new Day04PassphrasePartTwo().isValid(input);
    }
}