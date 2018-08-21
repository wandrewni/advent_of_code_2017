package org.wandotini;

import org.junit.Test;
import org.wandotini.utils.TestUtils;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class Day08RegistersTest {

    private Day08Registers registers;

    @Test
    public void withNoInstructions_registerValueIsZero() throws Exception {
        registers = processInstructions();
        verifyRegisterValue("a", 0);
        verifyRegisterValue("b", 0);
    }

    @Test
    public void withIncInstruction_registerValueIncreased() throws Exception {
        registers = processInstructions(
                "a inc 1",
                "b inc 2");
        verifyRegisterValue("a", 1);
        verifyRegisterValue("b", 2);
    }

    @Test
    public void handlesMultipleInstructionsForSameRegister() throws Exception {
        registers = processInstructions(
                "a inc 1",
                "a inc 2"
        );
        verifyRegisterValue("a", 3);
    }

    @Test
    public void handlesDecrementInstructions() throws Exception {
        registers = processInstructions(
                "a dec 1"
        );
        verifyRegisterValue("a", -1);
    }

    @Test
    public void handlesGreaterThan() throws Exception {
        registers = processInstructions(
                "a dec 30 if a > -1",
                "a inc 1 if b > -1"
        );
        verifyRegisterValue("a", -29);
    }

    @Test
    public void handlesGreaterThanOrEqualTo() throws Exception {
        registers = processInstructions(
                "a inc 1 if b > -1",
                "a inc 30 if a >= 1"
        );
        verifyRegisterValue("a", 31);
    }

    @Test
    public void handlesEquals() throws Exception {
        registers = processInstructions(
                "a dec 30 if a == 0",
                "a inc 1 if a == -30"
        );
        verifyRegisterValue("a", -29);
    }

    @Test
    public void handlesLessThan() throws Exception {
        registers = processInstructions(
                "a dec 30 if a < 1",
                "a inc 1 if a < -30"
        );
        verifyRegisterValue("a", -30);
    }

    @Test
    public void handlesLessThanOrEqualTo() throws Exception {
        registers = processInstructions(
                "a dec 30 if a <= 0",
                "a inc 1 if a <= -30"
        );
        verifyRegisterValue("a", -29);
    }

    @Test
    public void handlesNotEqualTo() throws Exception {
        registers = processInstructions(
                "a dec 30 if a != 200",
                "a inc 1 if a != -30"
        );
        verifyRegisterValue("a", -30);
    }

    @Test
    public void largestValueAfterModifyingNoRegistersIs0() throws Exception {
        registers = processInstructions();

        assertThat(registers.largestFinalValue(), is(0));
    }

    @Test
    public void largestValueAfterOneModificationIsThatRegister() throws Exception {
        registers = processInstructions("a inc 20");

        assertThat(registers.largestFinalValue(), is(20));
    }

    @Test
    public void largestValueAfterSeveralModificationsIsLargest() throws Exception {
        registers = processInstructions(
                "a inc 20",
                "b inc 20",
                "c inc 20",
                "c inc 20"
        );

        assertThat(registers.largestFinalValue(), is(40));
    }

    @Test
    public void maxValueSeenAfterModifyingNoRegistersIsNull() throws Exception {
        registers = processInstructions();

        assertThat(registers.maxValueSeen(), is(nullValue()));
    }

    @Test
    public void maxValueSeenAfterOneModificationIsThatRegister() throws Exception {
        registers = processInstructions("a inc 20");

        assertThat(registers.maxValueSeen(), is(20));
    }

    @Test
    public void maxValueSeenAfterSeveralModificationsIsLargest() throws Exception {
        registers = processInstructions(
                "a inc 20",
                "b inc 20",
                "c inc 20",
                "c inc 20",
                "c dec 20"
        );

        assertThat(registers.maxValueSeen(), is(40));
    }

    @Test
    public void puzzleInput_largestValue() throws Exception {
        final String[] input = TestUtils.readFileIntoReader("day_eight_input.txt").lines().toArray(String[]::new);

        registers = processInstructions(input);

        assertThat(registers.largestFinalValue(), is(6343));
    }

    @Test
    public void puzzleInput_maxValueSeen() throws Exception {
        final String[] input = TestUtils.readFileIntoReader("day_eight_input.txt").lines().toArray(String[]::new);

        registers = processInstructions(input);

        assertThat(registers.maxValueSeen(), is(7184));
    }

    private Day08Registers processInstructions(String... instructions) {
        return new Day08Registers(instructions);
    }

    private void verifyRegisterValue(String register, int value) {
        assertThat(registers.getRegister(register), is(value));
    }
}