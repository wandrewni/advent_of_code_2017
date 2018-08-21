package org.wandotini;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class Day07Towers {

    public static String getRoot(String[] lines) {
        if (lines.length == 1)
            return lines[0].split(" ")[0];
        else
            return new RootFinder(lines).findRootNode();
    }

    private static class RootFinder {
        private Set<String> candidateNodes = new HashSet<>();
        private Set<String> inEligibleNodes = new HashSet<>();
        private String[] lines;
        private String[] nextNodeMembers;

        public RootFinder(String[] lines) {
            this.lines = lines;
        }

        private String findRootNode() {
            for (String entry : lines)
                categorizeLine(entry.split(" "));
            candidateNodes.removeAll(inEligibleNodes);
            return candidateNodes.iterator().next();
        }

        private void categorizeLine(String[] splitLine) {
            nextNodeMembers = splitLine;
            final String node = nextNodeMembers[0];
            if (nodeHasChildren()) {
                candidateNodes.add(node);
                inEligibleNodes.addAll(getChildren());
            } else
                inEligibleNodes.add(node);
        }

        private boolean nodeHasChildren() {
            return nextNodeMembers.length > 3;
        }

        private List<String> getChildren() {
            return Arrays.stream(nextNodeMembers, 3, nextNodeMembers.length)
                    .map(removeCommas())
                    .collect(toList());
        }

        private Function<String, String> removeCommas() {
            return child -> child.replace(",", "");
        }
    }
}
