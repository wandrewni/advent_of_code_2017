package org.wandotini;

import org.junit.Test;
import org.wandotini.utils.TestUtils;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class DaySevenTowersTest {

    @Test
    public void rootOfSingleMemberTowerIsSingleMember() throws Exception {
        assertThat(DaySevenTowers.getRoot(array("root (15)")), is("root"));
    }

    @Test
    public void rootOfTwoLevelTowerIsTheEntryWithChildren() throws Exception {
        assertThat(DaySevenTowers.getRoot(array("top (24)", "newRoot (14) -> top")), is("newRoot"));
    }

    @Test
    public void rootOfTallerTowerIsTheEntryWithNoParents() throws Exception {
        assertThat(DaySevenTowers.getRoot(array("top (24)", "secondLevel (14) -> top", "root (99) -> secondLevel")), is("root"));
    }

    @Test
    public void tierWithMultipleChildrenShouldIncludeAllChildren() throws Exception {
        assertThat(DaySevenTowers.getRoot(array(
                "top (24)",
                "secondLevel (14) -> top",
                "secondLevel2 (44) -> otherTop",
                "root (99) -> secondLevel, secondLevel2",
                "otherTop (22)")
        ), is("root"));
    }

    @Test
    public void puzzleInput() throws Exception {
        String[] input = TestUtils.readFileIntoReader("day_seven_input.txt")
                .lines()
                .map(line -> line.replace("\n",""))
                .toArray(String[]::new);
        assertThat(DaySevenTowers.getRoot(input), is("ahnofa"));
    }

    private String[] array(String... entries) {
        return entries;
    }
}