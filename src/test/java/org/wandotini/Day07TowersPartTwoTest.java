package org.wandotini;

import org.junit.Test;
import org.wandotini.util.TestUtils;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day07TowersPartTwoTest {

    @Test
    public void rootOfSingleMemberTowerIsSingleMember() throws Exception {
        assertThat(getRoot("root (15)"), is("root"));
    }

    @Test
    public void rootOfTwoLevelTowerIsTheEntryWithChildren() throws Exception {
        assertThat(getRoot("top (24)", "newRoot (14) -> top"), is("newRoot"));
    }

    @Test
    public void rootOfTallerTowerIsTheEntryWithNoParents() throws Exception {
        assertThat(getRoot("top (24)", "secondLevel (14) -> top", "root (99) -> secondLevel"), is("root"));
    }

    @Test
    public void tierWithMultipleChildrenShouldIncludeAllChildren() throws Exception {
        assertThat(getRoot(
                "top (24)",
                "secondLevel (14) -> top",
                "secondLevel2 (44) -> otherTop",
                "root (99) -> secondLevel, secondLevel2",
                "otherTop (22)"
        ), is("root"));
    }

    @Test
    public void weightOfSingleMemberTowerIsSingleMember() throws Exception {
        assertThat(getWeight("root", "root (15)"), is(15));
    }

    @Test
    public void weightsOfTwoLevelTower() throws Exception {
        assertThat(getWeight("secondLevel", "root (15) -> secondLevel", "secondLevel (20)"), is(20));
        assertThat(getWeight("root", "root (15) -> secondLevel, secondLevel2", "secondLevel (20)", "secondLevel2 (25)"), is(60));
    }

    @Test
    public void findUnbalanceOnNextLevel() throws Exception {
        assertThat(findUnbalance("root (15) -> secondLevel, secondLevel2", "secondLevel (25)", "secondLevel2 (20)"), is(25));
    }

    @Test
    public void findUnbalanceWhenOnNextLevelAndNeedsIncreasing() throws Exception {
        assertThat(findUnbalance("root (15) -> secondLevel, secondLevel2, secondLevel3",
                "secondLevel (20)",
                "secondLevel2 (25)",
                "secondLevel3 (25)"), is(25));
    }

    @Test
    public void findUnbalanceWhenWeightsAreDifferent() throws Exception {
        assertThat(findUnbalance("root (15) -> secondLevel, secondLevel2, secondLevel3",
                "secondLevel (20) -> thirdLevelA, thirdLevelB", // 90
                "secondLevel2 (46) -> thirdLevelC, thirdLevelD", // 90
                "secondLevel3 (25) -> thirdLevelE", // needs to be 66 to reach 90
                "thirdLevelA (35)",
                "thirdLevelB (35)",
                "thirdLevelC (22)",
                "thirdLevelD (22)",
                "thirdLevelE (24)"), is(66));
    }

    @Test
    public void findUnbalanceWhenOnThirdLevel() throws Exception {
        assertThat(findUnbalance("root (15) -> secondLevel, secondLevel2, secondLevel3",
                "secondLevel (25) -> thirdLevelA, thirdLevelB", // 90
                "secondLevel2 (46) -> thirdLevelC, thirdLevelD", // 90
                "secondLevel3 (34) -> thirdLevelE, thirdLevelF", // needs to be 56 more to reach 90
                "thirdLevelA (35)",
                "thirdLevelB (35)",
                "thirdLevelC (22)",
                "thirdLevelD (22)",
                "thirdLevelE (30)",
                "thirdLevelF (20) -> fourthLevelA, fourthLevelB", // needs to be 30 to be 68
                "fourthLevelA (4)",
                "fourthLevelB (4)"), is(28));
    }

    @Test
    public void findUnbalance() throws Exception {
        assertThat(findUnbalance("root (15) -> secondLevel, secondLevel2, secondLevel3",
                "secondLevel (26) -> thirdLevelA, thirdLevelB", // 90
                "secondLevel2 (46) -> thirdLevelC, thirdLevelD", // 90
                "secondLevel3 (30) -> thirdLevelE, thirdLevelF", // needs to be 60 more to reach 90
                "thirdLevelA (32)",
                "thirdLevelB (32)",
                "thirdLevelC (22)",
                "thirdLevelD (22)",
                "thirdLevelE (25)", // needs to be 30 to be 60
                "thirdLevelF (30)"), is(30));
    }

    @Test
    public void findUnbalanceWhenOnThirdLevelAlt() throws Exception {
        assertThat(findUnbalance("root (15) -> secondLevel, secondLevel2, secondLevel3",
                "secondLevel (26) -> thirdLevelA, thirdLevelB", // 90
                "secondLevel2 (46) -> thirdLevelC, thirdLevelD", // 90
                "secondLevel3 (34) -> thirdLevelE, thirdLevelF", // needs to be 65 more to reach 90
                "thirdLevelA (32)",
                "thirdLevelB (32)",
                "thirdLevelC (22)",
                "thirdLevelD (22)",
                "thirdLevelE (20) -> fourthLevelA, fourthLevelB", // needs to be 30 to be 68
                "thirdLevelF (28)",
                "fourthLevelA (4)",
                "fourthLevelB (2)"), is(4));
    }


    @Test
    public void puzzleInput() throws Exception {
        String[] input = TestUtils.readFileIntoReader("day_07_input.txt")
                .lines()
                .map(line -> line.replace("\n",""))
                .toArray(String[]::new);
        assertThat(findUnbalance(input), is(802));
    }

    private Integer findUnbalance(String... entries) {
        return buildTowers(entries).findUnbalancedProgram();
    }

    private Integer getWeight(String root, String... entries) {
        return buildTowers(entries).getWeight(root);
    }

    private String getRoot(String... entries) {
        return buildTowers(entries).getRoot();
    }

    private Day07TowersPartTwo buildTowers(String[] entries) {
        return new Day07TowersPartTwo(entries);
    }
}