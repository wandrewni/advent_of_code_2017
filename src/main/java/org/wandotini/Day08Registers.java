package org.wandotini;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.Comparator.naturalOrder;

public class Day08Registers {
    private Map<String, Integer> registers = new HashMap<>();
    private Integer maxValueSeen = null;

    public Day08Registers(String[] instructions) {
        for (String instruction : instructions)
            process(new Instruction(instruction.split(" ")));
    }

    private void process(Instruction instruction) {
        if (instruction.shouldApply())
            instruction.applyOperation();
    }


    private Integer setRegister(String register, int newValue) {
        return registers.put(register, newValue);
    }

    public int getRegister(String register) {
        final Integer value = registers.get(register);
        return value == null ? 0 : value;
    }

    public int largestFinalValue() {
        return registers.values().stream().max(naturalOrder()).orElse(0);
    }

    public Integer maxValueSeen() {
        return maxValueSeen;
    }

    private class Instruction {
        private String register;
        private String operation;
        private int value;
        private int testRegisterValue;
        private String condition;
        private int testValue;
        private int registerValue;

        public Instruction(String... tokens) {
            register = tokens[0];
            operation = tokens[1];
            value = Integer.parseInt(tokens[2]);
            registerValue = getRegister(register);
            if (tokens.length > 3) {
                testRegisterValue = getRegister(tokens[4]);
                condition = tokens[5];
                testValue = Integer.parseInt(tokens[6]);
            }
        }

        private boolean shouldApply() {
            return condition == null || passesCondition();
        }

        private boolean passesCondition() {
            switch (condition) {
                case ">":
                    return testRegisterValue > testValue;
                case ">=":
                    return testRegisterValue >= testValue;
                case "==":
                    return testRegisterValue == testValue;
                case "<":
                    return testRegisterValue < testValue;
                case "<=":
                    return testRegisterValue <= testValue;
                case "!=":
                    return testRegisterValue != testValue;
            }
            throw new IllegalStateException("unknown condition");
        }

        private void applyOperation() {
            final int newValue = operation.equals("inc") ?
                    registerValue + value :
                    registerValue - value;
            setRegister(register, newValue);
            if (maxValueSeen == null || newValue > maxValueSeen)
                maxValueSeen = newValue;
        }
    }
}
