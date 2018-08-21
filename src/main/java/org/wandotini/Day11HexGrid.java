package org.wandotini;

import java.util.ArrayList;
import java.util.List;

public class Day11HexGrid {

    private static final String N = "n";
    private static final String S = "s";
    private static final String NW = "nw";
    private static final String SW = "sw";
    private static final String SE = "se";
    private static final String NE = "ne";

    public List<String> condensePath(String ... path) {
        List<String> resultingPath = new ArrayList<>();
        for (String step : path) {
            String opposite = produceOpposite(step);
            if (resultingPath.contains(opposite))
                resultingPath.remove(opposite);
            else
                resultingPath.add(step);
        }
        return resultingPath;
    }

    private String produceOpposite(String direction) {
        if (direction.equals(N)) return S;
        if (direction.equals(S)) return N;
        if (direction.equals(NW)) return SE;
        if (direction.equals(SW)) return NE;
        if (direction.equals(NE)) return SW;
        return NW;
    }
}
