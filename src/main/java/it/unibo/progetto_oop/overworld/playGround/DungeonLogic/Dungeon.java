package it.unibo.progetto_oop.overworld.playGround.DungeonLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unibo.progetto_oop.overworld.playGround.Data.FloorConfig;

public class Dungeon {
    /**
     * List of floors in the dungeon.
     */
    private final List<Floor> floors = new ArrayList<>();
    /**
     * Index of the current floor.
     * Start at -1 because nextFloor is called immediately.
     */
    private int currentFloor = -1;
    /**
     * Generator used to create floors in the dungeon.
     */
    private final FloorGenerator generator;
    /**
     * Configuration settings for the dungeon floors.
     */
    private final FloorConfig config;

    /**
     * Constructs a Dungeon with the specified generator and configuration.
     *
     * @param gen the generator used to create floors in the dungeon
     * @param conf the configuration settings for the dungeon floors
     */
    public Dungeon(final FloorGenerator gen, final FloorConfig conf) {
        this.generator = Objects.requireNonNull(gen);
        this.config = Objects.requireNonNull(conf);
    }

    /**
     * @return the index of the current floor
     */
    public int getCurrentFloorIndex() {
        return this.currentFloor;
    }
    
    /**
     * Give the current floor of the dungeon.
     *
     * @return the current floor
     */
    public final Floor getCurrentFloor() {
        return floors.get(currentFloor);
    }

    /**
     * Advances to the next floor in the dungeon if possible.
     *
     * @return {@code true} if the floor was successfully advanced,
     * {@code false} if already on the last floor
     */
    public final boolean nextFloor() {
        if (currentFloor >= config.nFloors() - 1) {
            return false;
        }
        int nextIndex = currentFloor + 1;
        if (nextIndex >= floors.size()) {
            final boolean isFinal = (nextIndex == config.nFloors() - 1);
            final FloorConfig cfg = isFinal ? finalRoomConfig(config) : config;
            floors.add(new Floor(cfg, generator, isFinal));
        }
        currentFloor = nextIndex;
        return true;
    }

    private FloorConfig finalRoomConfig(final FloorConfig c) {
        return new FloorConfig.Builder()
                .size(c.width(), c.height())
                .rooms(1)
                .roomSize(
                    c.minRoomW()+1, c.maxRoomW() - 4, c.minRoomH()+1, c.maxRoomH() - 4
                )
                .build();
    }
}
