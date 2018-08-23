package org.wandotini.util;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day12DigitalPlumbingTest {

    @Test
    public void canParseVillageProgramDefinition() throws Exception {
        assertThat(parse("0 <-> 0"), is(villageProgram(0)));
        assertThat(parse("0 <-> 1"), is(villageProgram(0, 1)));
        assertThat(parse("0 <-> 1, 2"), is(villageProgram(0, 1, 2)));
    }

    static private VillageProgram villageProgram(int id, Integer ... pipe) {
        return VillageProgram.of(id, pipe);
    }

    private static VillageProgram parse(String input) {
        final String[] split = input.split(" ");
        int id = Integer.parseInt(split[0]);
        final Integer[] integers = extractPipeConnections(id, split);
        return villageProgram(id, integers);
    }

    private static Integer[] extractPipeConnections(int id, String[] split) {
        return Arrays.stream(split, 2, split.length)
                .map(string -> string.replace(",", ""))
                .map(Integer::parseInt)
                .filter(integer -> id != integer)
                .toArray(Integer[]::new);
    }

    @Value(staticConstructor = "of")
    private static class VillageProgram {
        int id;
        Integer[] pipe;
    }
}
