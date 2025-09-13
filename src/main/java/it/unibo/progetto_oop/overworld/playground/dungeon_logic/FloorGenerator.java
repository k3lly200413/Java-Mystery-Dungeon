package it.unibo.progetto_oop.overworld.playground.dungeon_logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import it.unibo.progetto_oop.overworld.playground.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.StructureData;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.RandomPlacementStrategy;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.RoomPlacementStrategy;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.TunnelPlacementStrategy;

/**
 * Class responsible for generating a dungeon floor with rooms, tunnels, and
 * objects.
 */
public final class FloorGenerator {
    /**
     * Strategy for placing rooms.
     */
    private final RoomPlacementStrategy roomPlacement;
    /**
     * Strategy for connecting rooms with tunnels.
     */
    private final TunnelPlacementStrategy tunnelPlacement;
    /**
     * Strategy for placing objects like stairs, player, enemies, and items.
     */
    private final RandomPlacementStrategy objectPlacer;
    /**
     * Random number generator used for placement strategies.
     */
    private final Random rand;

    /**
     * Constructs a FloorGenerator with the specified placement strategies.
     *
     * @param roomps   the strategy for placing rooms
     * @param tunnelps the strategy for connecting rooms with tunnels
     * @param randps   the strategy for placing objects like stairs...
     */
    public FloorGenerator(
            final RoomPlacementStrategy roomps,
            final TunnelPlacementStrategy tunnelps,
            final RandomPlacementStrategy randps) {
        this.roomPlacement = Objects.requireNonNull(roomps);
        this.tunnelPlacement = Objects.requireNonNull(tunnelps);
        this.objectPlacer = Objects.requireNonNull(randps);
        this.rand = new Random();
    }

    /**
     * Generate floor using configuration data.
     * And fill the provided grid with rooms, tunnels, and objects.
     *
     * @param baseGrid   the structure data representing the floor grid
     * @param entityGrid the structure data representing the entity grid
     * @param conf       the floor configuration specifying parameters
     * @param finalFloor indicates whether this is the final floor
     * @return a list of rooms generated on the floor
     */
    public List<Room> generate(
            final StructureData baseGrid,
            final StructureData entityGrid,
            final FloorConfig conf,
            final boolean finalFloor) {
        if (conf.nRooms() <= 0) {
            throw new IllegalArgumentException("nRooms must be > 0");
        }
        final List<Room> rooms = new ArrayList<>();
        roomPlacement.placeRooms(baseGrid, rooms, rand, conf);
        if (rooms.size() >= 2) {
            tunnelPlacement.connect(baseGrid, rooms, rand);
        }
        if (!finalFloor) {
            objectPlacer.placeOnBase(baseGrid, TileType.STAIRS, 1, rand);
        }
        return rooms; // Floor farà List.copyOf()
    }
}
