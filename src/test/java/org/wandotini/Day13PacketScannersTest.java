package org.wandotini;

import org.junit.Test;
import org.wandotini.Day13PacketScanners.Layer;
import org.wandotini.util.TestUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class Day13PacketScannersTest {

    private Day13PacketScanners packetScanners;

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
        packetScanners = scanner(layer(0,0));
        assertThat(packetScanners.getPacketPosition(), is(-1));
    }

    @Test
    public void newScannersHaveASeverityValueOfZero() {
        packetScanners = scanner(layer(0, 0));
        assertThat(packetScanners.getSeverityRaised(), is(0));
    }

    @Test
    public void packetPositionMovesWithEachTick() {
        packetScanners = scanner(layer(0,0));
        packetScanners.tick();
        assertThat(packetScanners.getPacketPosition(), is(0));
    }

    @Test
    public void sentryPositionOnLayerMovesAfterTick() {
        Layer layer = layer(0, 2);
        assertThat(layer.getSentryPosition(), is(0));
        layer.tick();
        assertThat(layer.getSentryPosition(), is(1));
    }

    @Test
    public void sentryPositionIsAlways0OnRange1() {
        Layer layer = layer(0, 1);
        assertThat(layer.getSentryPosition(), is(0));
        layer.tick();
        assertThat(layer.getSentryPosition(), is(0));
    }

    @Test
    public void sentryPositionReversesOnBoundaries() {
        Layer layer = layer(0, 3);
        assertThat(layer.getSentryPosition(), is(0));
        layer.tick();
        assertThat(layer.getSentryPosition(), is(1));
        layer.tick();
        assertThat(layer.getSentryPosition(), is(2));
        layer.tick();
        assertThat(layer.getSentryPosition(), is(1));
        layer.tick();
        assertThat(layer.getSentryPosition(), is(0));
        layer.tick();
        assertThat(layer.getSentryPosition(), is(1));
    }

    @Test
    public void layerSeverityIsZeroForALayerWithNoRange() {
        final Layer layer = layer(4, 0);
        assertThat(layer.calcSeverity(), is(0));
        layer.tick();
        assertThat(layer.calcSeverity(), is(0));
    }

    @Test
    public void layerSeverityIsRangeTimesDepth() {
        assertThat(layer(1, 3).calcSeverity(), is(3));
        assertThat(layer(2, 7).calcSeverity(), is(14));
    }

    @Test
    public void layerSeverityIsZeroWhenSentryNotInFirstPosition() {
        final Layer layer = layer(3, 2);
        System.out.println(layer);
        layer.tick();
        System.out.println(layer);
        assertThat(layer.calcSeverity(), is(0));
        System.out.println(layer);
        layer.tick();
        System.out.println(layer);
        assertThat(layer.calcSeverity(), is(6));
    }

    @Test
    public void packetAtEndWhenPositionEqualsMaxDepth() {
        packetScanners = scanner(layer(2,0));
        assertFalse(packetScanners.lastLayerReached());
        packetScanners.tick();
        assertFalse(packetScanners.lastLayerReached());
        packetScanners.tick();
        packetScanners.tick();
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
        packetScanners.tick();
        assertThat(packetScanners.getSeverityRaised(), is(0));
        packetScanners.tick();
        assertThat(packetScanners.getSeverityRaised(), is(1));
    }

    @Test
    public void packetScannerTicksMoveSentries() {
        packetScanners = scanner(
                layer(0, 1),
                layer(1, 2),
                layer(2, 2)
        );

        packetScanners.tick();
        packetScanners.tick();
        packetScanners.tick();
        assertThat(packetScanners.getSeverityRaised(), is(4));
    }

    @Test
    public void puzzleInput() {
        Layer[] layers = TestUtils.readFileIntoLines("day_13_input.txt")
                .map(Layer::fromString)
                .toArray(Layer[]::new);
        packetScanners = scanner(layers);
        while(!packetScanners.lastLayerReached())
            packetScanners.tick();
        assertThat(packetScanners.getSeverityRaised(), is(1876));
    }

    @Test
    public void parseSimpleInput() {
        Layer layer = Layer.fromString("1: 3");
        assertThat(layer.getDepth(), is(1));
        assertThat(layer.getRange(), is(3));
    }

    private Layer layer(int depth, int range) {
        return Layer.of(depth, range);
    }

    private Day13PacketScanners scanner(Layer... layers) {
        return new Day13PacketScanners(layers);
    }

}
