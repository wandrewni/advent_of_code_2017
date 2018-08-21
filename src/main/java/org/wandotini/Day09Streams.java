package org.wandotini;

public class Day09Streams {
    private final String stream;
    private boolean inGarbage = false;
    private boolean skipNextCharacter = false;
    private int groups = 0;
    private int currentGroupScore = 1;
    private int score = 0;
    private int garbageCharacters = 0;

    public Day09Streams(String stream) {
        this.stream = stream;
        processGroups();
    }

    public int processGroups() {
        for (char c : stream.toCharArray())
            if (skipNextCharacter)
                skipNextCharacter = false;
            else
                parse(c);
        return groups;
    }

    private void parse(char c) {
        if (inGarbage)
            handleGarbageCharacters(c);
        else
            handleNonGarbageCharacters(c);
    }

    private void handleNonGarbageCharacters(char c) {
        switch (c) {
            case '<':
                inGarbage = true;
                return;
            case '{':
                groups++;
                score += currentGroupScore++;
                return;
            case '}':
                currentGroupScore--;
                return;
        }
    }

    private void handleGarbageCharacters(char c) {
        switch (c) {
            case '!':
                skipNextCharacter = true;
                return;
            case '>':
                inGarbage = false;
                return;
                default:
                garbageCharacters++;
        }
    }

    public int getScore() {
        return score;
    }

    public int getGarbageCharacters() {
        return garbageCharacters;
    }
}
