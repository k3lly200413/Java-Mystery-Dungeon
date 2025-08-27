package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Dungeon {
    private final List<Floor> floors = new ArrayList<>();
    private int currentFloor = -1;
    private final FloorGenerator generator;
    private final FloorConfig config;

    public Dungeon(FloorGenerator generator, FloorConfig config) {
        this.generator = Objects.requireNonNull(generator);
        this.config    = Objects.requireNonNull(config);
    }

    public Floor getCurrentFloor() {
        return floors.get(currentFloor);
    }

    public boolean nextFloor() {
        if (currentFloor >= config.nFloors() - 1)
            return false;

        int nextIndex = currentFloor + 1;
        if (nextIndex >= floors.size()) {
            FloorConfig cfg = (nextIndex == config.nFloors() - 1)
                    ? finalRoomConfig(config)   // ultimo piano una stanza
                    : config;                   //config base
            floors.add(new Floor(cfg, generator));
        }

        currentFloor = nextIndex;
        return true;
    }

    private static FloorConfig finalRoomConfig(FloorConfig c) {
        return new FloorConfig.Builder()
                .size(c.width(), c.height())
                .rooms(1)
                .roomSize(c.minRoomW(), c.maxRoomW(), c.minRoomH(), c.maxRoomH())
                .build();
    }
}
