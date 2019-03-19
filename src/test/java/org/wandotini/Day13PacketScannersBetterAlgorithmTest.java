package org.wandotini;

import lombok.ToString;
import org.junit.Test;
import org.wandotini.Day13PacketScannersBetterAlgorithm.Layer;
import org.wandotini.util.TestUtils;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class Day13PacketScannersBetterAlgorithmTest {

    private Day13PacketScannersBetterAlgorithm packetScanners;

    @Test(expected = IllegalStateException.class)
    public void degenerateTests() {
        packetScanners = scanner();
        assertThat(packetScanners.getLayers(), is(iterableWithSize(0)));
    }

    @Test
    public void constructingWithOneLayer() {
        packetScanners = scanner(layer(0, 3));
        assertThat(packetScanners.getLayers(), contains(layer(0, 3)));
    }

    @Test
    public void constructingWithTwoLayers() {
        packetScanners = scanner(layer(0, 1), layer(1, 3));
        assertThat(packetScanners.getLayers(), contains(
                layer(0, 1),
                layer(1, 3))
        );
    }

    @Test
    public void constructingWithLayersFillsInMissingLayers() {
        packetScanners = scanner(layer(1, 3));

        assertThat(packetScanners.getLayers(), contains(
                layer(0, 0),
                layer(1, 3)
        ));
    }

    @Test
    public void newScannersHaveAPacketPositionOfZero() {
        packetScanners = scanner(layer(0, 0));
        assertThat(packetScanners.getPacketPosition(), is(-1));
    }

    @Test
    public void newScannersHaveASeverityValueOfZero() {
        packetScanners = scanner(layer(0, 0));
        assertThat(packetScanners.getSeverityRaised(), is(0));
    }

    @Test
    public void packetPositionMovesWithEachTick() {
        packetScanners = scanner(layer(0, 0));
        packetScanners.tick(1);
        assertThat(packetScanners.getPacketPosition(), is(0));
    }

    @Test
    public void sentryPositionOnLayerMovesAfterTick() {
        Layer layer = layer(0, 2);
        assertThat(layer.getSentryPosition(), is(0));
        layer = layer.tick(1);
        assertThat(layer.getSentryPosition(), is(1));
    }

    @Test
    public void sentryPositionIsAlways0OnRange1() {
        Layer layer = layer(0, 1);
        assertThat(layer.getSentryPosition(), is(0));
        layer = layer.tick(1);
        assertThat(layer.getSentryPosition(), is(0));
    }

    @Test
    public void sentryPositionReversesOnBoundaries() {
        Layer layer = layer(0, 3);
        assertThat(layer.getSentryPosition(), is(0));
        layer = layer.tick(1);
        assertThat(layer.getSentryPosition(), is(1));
        layer = layer.tick(1);
        assertThat(layer.getSentryPosition(), is(2));
        layer = layer.tick(1);
        assertThat(layer.getSentryPosition(), is(1));
        layer = layer.tick(1);
        assertThat(layer.getSentryPosition(), is(0));
        layer = layer.tick(1);
        assertThat(layer.getSentryPosition(), is(1));
    }

    @Test
    public void sentryPositionReversesOnBoundaries_intervals() {
        assertThat(layer(0, 3).tick(0).getSentryPosition(), is(0));
        assertThat(layer(0, 3).tick(1).getSentryPosition(), is(1));
        assertThat(layer(0, 3).tick(2).getSentryPosition(), is(2));
        assertThat(layer(0, 3).tick(3).getSentryPosition(), is(1));
        assertThat(layer(0, 3).tick(4).getSentryPosition(), is(0));
        assertThat(layer(0, 3).tick(5).getSentryPosition(), is(1));
        assertThat(layer(0, 3).tick(6).getSentryPosition(), is(2));
        assertThat(layer(0, 3).tick(7).getSentryPosition(), is(1));
        assertThat(layer(0, 3).tick(8).getSentryPosition(), is(0));
        assertThat(layer(0, 3).tick(9).getSentryPosition(), is(1));
    }

    @Test
    public void layerSeverityIsZeroForALayerWithNoRange() {
        Layer layer = layer(4, 0);
        assertThat(layer.calcSeverity(), is(0));
        layer = layer.tick(1);
        assertThat(layer.calcSeverity(), is(0));
    }

    @Test
    public void layerSeverityIsRangeTimesDepth() {
        assertThat(layer(1, 3).calcSeverity(), is(3));
        assertThat(layer(2, 7).calcSeverity(), is(14));
    }

    @Test
    public void layerSeverityIsZeroWhenSentryNotInFirstPosition() {
        Layer layer = layer(3, 2);
        layer = layer.tick(1);
        assertThat(layer.calcSeverity(), is(0));
        layer = layer.tick(1);
        assertThat(layer.calcSeverity(), is(6));
    }

    @Test
    public void packetAtEndWhenPositionEqualsMaxDepth() {
        packetScanners = scanner(layer(2, 0));
        assertFalse(packetScanners.lastLayerReached());
        packetScanners.tick(1);
        assertFalse(packetScanners.lastLayerReached());
        packetScanners.tick(2);
        assertTrue(packetScanners.lastLayerReached());
    }

    @Test
    public void scannerSeverityIsSumOfLayerSeverities() {
        packetScanners = scanner(
                layer(0, 1),
                layer(1, 1),
                layer(2, 1)
        );

        assertThat(packetScanners.getSeverityRaised(), is(0));
        packetScanners.tick(1);
        assertThat(packetScanners.getSeverityRaised(), is(0));
        packetScanners.tick(1);
        assertThat(packetScanners.getSeverityRaised(), is(1));
    }

    @Test
    public void packetScannerTicksMoveSentries() {
        packetScanners = scanner(
                layer(0, 1),
                layer(1, 2),
                layer(2, 2)
        );

        packetScanners.tick(3);
        assertThat(packetScanners.getSeverityRaised(), is(4));
    }

    @Test
    public void puzzleInput() {
        packetScanners = puzzleInputScanners();
        while (!packetScanners.lastLayerReached())
            packetScanners.tick(1);
        assertThat(packetScanners.getSeverityRaised(), is(1876));
    }

    private Day13PacketScannersBetterAlgorithm puzzleInputScanners() {
        Layer[] layers = TestUtils.readFileIntoLines("day_13_input.txt")
                .map(Layer::fromString)
                .toArray(Layer[]::new);
        return scanner(layers);
    }

    @Test
    public void minimumSafeDelayTwo_forVerySimpleLayout() {
        packetScanners = scanner(layer(0, 0));
        assertThat(packetScanners.someSafeDelays(3), containsInAnyOrder(0, 1, 2));
    }

    @Test
    public void minimumSafeDelayTwo_forSingleRange() {
        packetScanners = scanner(layer(0, 3));
        assertThat(packetScanners.someSafeDelays(3), containsInAnyOrder(1, 2, 3));
        assertThat(packetScanners.someSafeDelays(4), containsInAnyOrder(1, 2, 3, 5));
        assertThat(packetScanners.someSafeDelays(8), containsInAnyOrder(1, 2, 3, 5, 6, 7, 9, 10));
    }

    @Test
    public void safeDelaysForSingleLargerRange() {
        packetScanners = scanner(layer(0, 2));
        assertThat(packetScanners.someSafeDelays(2), containsInAnyOrder(1, 3));
        assertThat(packetScanners.someSafeDelays(3), containsInAnyOrder(1, 3, 5));
        assertThat(packetScanners.someSafeDelays(5), containsInAnyOrder(1, 3, 5, 7, 9));
    }

    @Test
    public void safeDelaysForSingleLargerRange_secondRange() {
        packetScanners = scanner(layer(1, 2));
        assertThat(packetScanners.someSafeDelays(2), containsInAnyOrder(0, 2));
        assertThat(packetScanners.someSafeDelays(3), containsInAnyOrder(0, 2, 4));
        assertThat(packetScanners.someSafeDelays(5), containsInAnyOrder(0, 2, 4, 6, 8));
    }

    @Test
    public void someSafeDelays_forSecondDepthRange() {
        packetScanners = scanner(layer(1, 3));
        assertThat(packetScanners.someSafeDelays(4), containsInAnyOrder(0, 1, 2, 4));
    }

    @Test
    public void delayExampleUsingNewAlgorithm() {
        packetScanners = scanner(
                layer(0, 3),
                layer(1, 2),
                layer(4, 4),
                layer(6, 4)
        );

        assertThat(packetScanners.someSafeDelays(1), contains(10));
    }

    @Test
    public void parseSimpleInput() {
        Layer layer = Layer.fromString("1: 3");
        assertThat(layer.getDepth(), is(1));
        assertThat(layer.getRange(), is(3));
    }

    @Test
    public void puzzleDayTwo() {
        packetScanners = puzzleInputScanners();
        assertThat(packetScanners.someSafeDelays(1), contains(3964778)); // 352 ms
    }

    private Layer layer(int depth, int range) {
        return Layer.of(depth, range);
    }

    private Day13PacketScannersBetterAlgorithm scanner(Layer... layers) {
        return new Day13PacketScannersBetterAlgorithm(layers);
    }

}
