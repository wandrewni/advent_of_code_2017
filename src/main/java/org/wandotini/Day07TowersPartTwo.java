package org.wandotini;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Day07TowersPartTwo {
    private final String[] lines;
    private TreeBuilder treeBuilder;

    public Day07TowersPartTwo(String[] entries) {
        this.lines = entries;
        treeBuilder = new TreeBuilder(lines);
    }

    public String getRoot() {
        return treeBuilder.getRootNode();
    }

    public Integer getWeight(String node) {
        return treeBuilder.findNode(node).getTreeWeight();
    }

    public Integer findUnbalancedProgram() {
        return treeBuilder.findUnbalancedProgram();
    }

    private static class TreeBuilder {
        private Set<String> candidateNodes = new HashSet<>();
        private Set<String> inEligibleNodes = new HashSet<>();
        private Map<String, Integer> weights = new HashMap<>();
        private Map<String, List<String>> parentToChildren = new HashMap<>();
        private String[] nextNodeMembers;
        private Node tree;
        private Map<String, Node> nodes = new HashMap<>();

        public TreeBuilder(String[] lines) {
            if (lines.length == 1) {
                final String[] split = lines[0].split(" ");
                candidateNodes.add(split[0]);
                weights.put(split[0], parseWeight(split[1]));
                parentToChildren.put(split[0], emptyList());
            } else {
                for (String entry : lines)
                    categorizeLine(entry.split(" "));
                candidateNodes.removeAll(inEligibleNodes);
            }

            tree = buildTree(getRootNode());
        }

        private int parseWeight(String s) {
            return Integer.parseInt(s.substring(1, s.length() - 1));
        }

        private String getRootNode() {
            return candidateNodes.iterator().next();
        }

        private void categorizeLine(String[] splitLine) {
            nextNodeMembers = splitLine;
            final String node = nextNodeMembers[0];
            weights.put(node, parseWeight(nextNodeMembers[1]));
            if (nodeHasChildren()) {
                candidateNodes.add(node);
                final List<String> children = getChildren();
                parentToChildren.put(node, children);
                inEligibleNodes.addAll(children);
            } else {
                inEligibleNodes.add(node);
                parentToChildren.put(node, emptyList());
            }
        }

        private Node buildTree(String nodeString) {
            Node node = new Node(nodeString, weights.get(nodeString));
            node.children.addAll(parentToChildren.get(nodeString).stream().map(this::buildTree).collect(Collectors.toList()));
            nodes.put(nodeString, node);
            return node;
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

        // whoof this is unreadable
        public Integer findUnbalancedProgram() {
            List<Node> children = getProblemChildren(tree);
            final int max = children.stream().mapToInt(Node::getTreeWeight).max().getAsInt();
            final int min = children.stream().mapToInt(Node::getTreeWeight).min().getAsInt();
            Node problemChild = children.stream().filter(child -> (!Objects.equals(child.getTreeWeight(), max))).findFirst().get();
            Integer weightedWeight = problemChild.getTreeWeight();
            Integer actualValue = problemChild.getWeight();
            problemChild.weight = actualValue + (max - weightedWeight);
            if (tree.isBalanced()) {
                return actualValue + (max - weightedWeight);
            } else {
                problemChild.weight = actualValue;
                problemChild = children.stream().filter(child -> (!Objects.equals(child.getTreeWeight(), min))).findFirst().get();
                weightedWeight = problemChild.getTreeWeight();
                actualValue = problemChild.getWeight();

            }
            return actualValue + (min - weightedWeight);
        }

        private List<Node> getProblemChildren(Node node) {
            List<Node> children = node.children;
            Optional<Node> childWithUnbalancedChildren = children.stream().filter(Node::isUnbalanced)
                    .findFirst();
            if (childWithUnbalancedChildren.isPresent())
                return getProblemChildren(childWithUnbalancedChildren.get());
            else
                return children;
        }

        public Node findNode(String node) {
            return nodes.get(node);
        }
    }

    static class Node {
        String string;
        int weight;
        List<Node> children = new ArrayList<>();

        public Node(String string, int weight) {
            this.string = string;
            this.weight = weight;
        }

        public int getTreeWeight() {
            return weight + children.stream().mapToInt(Node::getTreeWeight).sum();
        }

        public int getWeight() {
            return weight;
        }

        public boolean isUnbalanced() {
            return children.stream().mapToInt(Node::getTreeWeight).distinct().count() > 1;
        }

        public boolean isBalanced() {
            return children.stream().mapToInt(Node::getTreeWeight).distinct().count() == 1;
        }

        @Override
        public String toString() {
            return "Node{" + "string='" + string + '\'' +
                    ", weight=" + weight +
                    ", treeWeight=" + getTreeWeight() +
                    ", children=" + children.stream().map(child -> "\t :" + child).collect(joining("\n")) +
                    '}';
        }
    }
}
