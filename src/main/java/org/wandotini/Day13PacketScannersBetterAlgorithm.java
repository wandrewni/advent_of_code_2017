package org.wandotini;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Day13PacketScannersBetterAlgorithm {
    private List<Layer> layers;
    private int packetPosition = -1;
    private int severityRaised;
    private final int maxDepth;

    public Day13PacketScannersBetterAlgorithm(Layer... layers) {
        if (layers.length == 0) throw new IllegalStateException();
        Map<Integer, Layer> layerMap = new HashMap<>();
        Arrays.stream(layers).forEach(layer -> layerMap.put(layer.getDepth(), layer));
        maxDepth = Arrays.stream(layers).mapToInt(Layer::getDepth).max().orElse(0);

        this.layers = IntStream.range(0, maxDepth + 1)
                .mapToObj(
                        depth -> layerMap.containsKey(depth) ? layerMap.get(depth)
                                : Layer.emptyLayer(depth)
                )
                .collect(toList());
    }

    public List<Layer> getLayers() {
        return layers;
    }

    public int getPacketPosition() {
        return packetPosition;
    }

    public void tick() {
        packetPosition++;
        severityRaised += layers.get(packetPosition).calcSeverity();
        layers = layers.stream().map(Layer::tick).collect(toList());
    }

    public int getSeverityRaised() {
        return severityRaised;
    }

    public boolean lastLayerReached() {
        return packetPosition == maxDepth;
    }

    void tick(int numTicks) {
        IntStream.range(0, numTicks)
                .forEach(i -> tick());
    }

    public List<Integer> someSafeDelays(int desiredValues) {
        List<Integer> safeDelays = new ArrayList<>();
        for (int delay = 0; safeDelays.size() < desiredValues; delay++)
            if (delaySafe(delay))
                safeDelays.add(delay);
        return safeDelays;
    }

    private boolean delaySafe(int delay) {
        return layers.stream().noneMatch(layer -> delayWillHitSentry(delay, layer));
    }

    private boolean delayWillHitSentry(int delay, Layer layer) {
        final boolean unsafe = layer.sentryPresentAtTime(delay + layer.depth);
        return layer.hasSentry() && unsafe;
    }

    @ToString
    @EqualsAndHashCode
    public static class Layer {
        @Getter private final int depth;
        @Getter private final int range;
        @Getter private final int sentryPosition;
        private final int positionInterval;

        public Layer(int depth, int range) {
            this.depth = depth;
            this.range = range;
            this.sentryPosition = 0;
            this.positionInterval = 1;
        }

        public Layer(int depth, int range, int sentryPosition, int positionInterval) {
            this.depth = depth;
            this.range = range;
            this.sentryPosition = sentryPosition;
            this.positionInterval = positionInterval;
        }

        public static Layer fromString(String input) {
            final String[] splitInput = input.replaceAll(" ", "").split(":");
            final int depth = Integer.parseInt(splitInput[0]);
            final int range = Integer.parseInt(splitInput[1]);
            return new Layer(depth, range);
        }

        static Layer of(int depth, int range) {
            return new Layer(depth, range);
        }

        private static Layer emptyLayer(int depth) {
            return of(depth, 0);
        }

        public Layer tick() {
            return tick(1);
        }

        public Layer tick(int numTicks) {
            if (range <= 1) return this;
            Layer newLayer = this;
            int i = numTicks;
            while (i-- > 0)
                newLayer = newLayer.singleTick();
            return newLayer;
        }

        private Layer singleTick() {
            final int newSentryPosition = sentryPosition + positionInterval;
            final int newInterval = determinePositionInterval(newSentryPosition, range, positionInterval);
            return new Layer(depth, range, newSentryPosition, newInterval);
        }

        private int determinePositionInterval(int newSentryPosition, int range, int originalInterval) {
            if (newSentryPosition == range - 1) return -1;
            else if (newSentryPosition == 0) return 1;
            return originalInterval;
        }

        public int calcSeverity() {
            return sentryPosition == 0 ? depth * range
                    : 0;
        }

        private boolean hasSentry() {
            return range != 0;
        }

        private boolean sentryPresentAtTime(int time) {
            final int sentryCycleLength = 2 * (range - 1);
            return time % sentryCycleLength == 0;
        }
    }
}
