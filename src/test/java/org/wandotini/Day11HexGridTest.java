package org.wandotini;

import org.junit.Before;
import org.junit.Test;
import org.wandotini.util.TestUtils;

import java.io.BufferedReader;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class Day11HexGridTest {

    private static final String N = "n";
    private static final String S = "s";
    private static final String SE = "se";
    private static final String SW = "sw";
    private static final String NE = "ne";
    private static final String NW = "nw";
    private Day11HexGrid grid;

    @Before
    public void setUp() throws Exception {
        grid = new Day11HexGrid();
    }

    @Test
    public void emptyPathReturnsEmptyPath() throws Exception {
        assertThat(grid.condensePath(), is(emptyIterable()));
    }

    @Test
    public void singleStepPathReturnsInput() throws Exception {
        verifyCondensedPath(path(N), path(N));
        verifyCondensedPath(path(S), path(S));
        verifyCondensedPath(path(SE), path(SE));
        verifyCondensedPath(path(SW), path(SW));
        verifyCondensedPath(path(NE), path(NE));
        verifyCondensedPath(path(NW), path(NW));
    }

    @Test
    public void twoStepPathWithContinuingDirectionsReturnsInput() throws Exception {
        verifyCondensedPath(path(N, N), path(N, N));
        verifyCondensedPath(path(S, S), path(S, S));
        verifyCondensedPath(path(SE, SE), path(SE, SE));
        verifyCondensedPath(path(SW, SW), path(SW, SW));
        verifyCondensedPath(path(NE, NE), path(NE, NE));
        verifyCondensedPath(path(NW, NW), path(NW, NW));
    }

    @Test
    public void twoStepPathWithOpposingDirectionsReturnsEmptyPath() throws Exception {
        final String[] path = {N, S};
        verifyPathResultsBackAtStart(path);
        verifyPathResultsBackAtStart(S, N);
        verifyPathResultsBackAtStart(SE, NW);
        verifyPathResultsBackAtStart(SW, NE);
        verifyPathResultsBackAtStart(NE, SW);
        verifyPathResultsBackAtStart(NW, SE);
    }

    @Test
    public void firstMatchedPairIsRemovedFromThePath() throws Exception {
        verifyCondensedPath(path(N, S, N), path(N));
        verifyCondensedPath(path(S, N, N), path(N));
        verifyCondensedPath(path(SE, NW, N), path(N));
        verifyCondensedPath(path(SW, NE, N), path(N));
        verifyCondensedPath(path(NE, SW, N), path(N));
        verifyCondensedPath(path(NW, SE, N), path(N));
    }

    @Test
    public void allMatchedPairsAreRemovedFromThePath() throws Exception {
        verifyCondensedPath(path(N, S, N, S, N), path(N));
        verifyCondensedPath(path(S, N, S, N, N), path(N));
        verifyCondensedPath(path(SE, NW, SE, NW, N), path(N));
        verifyCondensedPath(path(SW, NE, SW, NE, N), path(N));
        verifyCondensedPath(path(NE, SW, NE, SW, N), path(N));
        verifyCondensedPath(path(NW, SE, NW, SE, N), path(N));
    }

    @Test
    public void handlesDiagonalsCombinedWithVerticals() throws Exception {
        verifyCondensedPath(path(SW, N, N, SW), path(NW, NW));
        verifyCondensedPath(path(SE, N, N, SE), path(NE, NE));
        verifyCondensedPath(path(NW, S, S, NW), path(SW, SW));
        verifyCondensedPath(path(NE, S, S, NE), path(SE, SE));
    }

    @Test
    public void puzzleInput() throws Exception {
        final BufferedReader bufferedReader = TestUtils.readFileIntoReader("day_11_input.txt");
        String input = bufferedReader.readLine();
        final String[] inputPath = input.split(",");
        final List<String> condensedPath = grid.condensePath(inputPath);
        System.out.println(condensedPath);
        assertThat(condensedPath, is(iterableWithSize(794)));
    }

    private String[] path(String ... path) {
        return path;
    }

    private void verifyCondensedPath(String[] input, String[] expected) {
        assertThat(grid.condensePath(input), containsInAnyOrder(expected));
    }

    private void verifyPathResultsBackAtStart(String... path) {
        assertThat(grid.condensePath(path),   is(emptyIterable()));
    }
}
