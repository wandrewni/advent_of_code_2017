package org.wandotini;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class Day13PacketScanners {
    private final List<Layer> layers;
    private int packetPosition = -1;
    private int severityRaised;
    private final int maxDepth;

    public Day13PacketScanners(Layer... layers) {
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
        layers.forEach(Layer::tick);
    }

    public int getSeverityRaised() {
        return severityRaised;
    }

    public boolean lastLayerReached() {
        return packetPosition == maxDepth;
    }

    @ToString
    @EqualsAndHashCode
    public static class Layer {
        @Getter private final int depth;
        @Getter private final int range;
        @Getter private int sentryPosition;
        private int positionInterval = 1;

        public Layer(int depth, int range) {
            this.depth = depth;
            this.range = range;
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

        public void tick() {
            if (range > 1) {
                sentryPosition += positionInterval;
                if (sentryPosition == range - 1) positionInterval = -1;
                if (sentryPosition == 0) positionInterval = 1;
            }
        }

        public int calcSeverity() {
            return sentryPosition == 0 ? depth * range
                    : 0;
        }
    }
}
