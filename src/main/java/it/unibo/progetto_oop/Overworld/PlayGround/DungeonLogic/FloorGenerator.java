package it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.FloorConfig;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;
import it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy.RandomPlacementStrategy;
import it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy.RoomPlacementStrategy;
import it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy.TunnelPlacementStrategy;
//SINGLETON??
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
     * @param roomps the strategy for placing rooms
     * @param tunnelps the strategy for connecting rooms with tunnels
     * @param randps the strategy for placing objects like stairs...
     *
     * @param r the random number generator used for placement strategies
     */
    public FloorGenerator(
            final RoomPlacementStrategy roomps,
            final TunnelPlacementStrategy tunnelps,
            final RandomPlacementStrategy randps,
            final Random r
    ) {
        this.roomPlacement = Objects.requireNonNull(roomps);
        this.tunnelPlacement = Objects.requireNonNull(tunnelps);
        this.objectPlacer = Objects.requireNonNull(randps);
        this.rand = Objects.requireNonNull(r);
    }

    /**
     * Generate floor using confiurguration data.
     * And fill the provided grid with rooms, tunnels, and objects.
     *
     * @param grid the structure data representing the floor grid
     * @param conf the floor configuration specifying parameters
     * @return a list of rooms generated on the floor
     */
    public List<Room> generate(
            final StructureData grid,
            final FloorConfig conf
    ) {
        if (conf.nRooms() <= 0) {
            throw new IllegalArgumentException("nRooms must be > 0");
        }
        grid.fill(TileType.WALL);
        List<Room> rooms = new ArrayList<>();
        roomPlacement.placeRooms(grid, rooms, rand, conf);
        if (rooms.size() >= 2) {
            tunnelPlacement.connect(grid, rooms, rand);
        }
        objectPlacer.placeObject(grid, TileType.STAIRS, 1, rand);
        objectPlacer.placeObject(grid, TileType.PLAYER, 1, rand);
        objectPlacer.placeObject(grid, TileType.ENEMY, rooms.size() * 2, rand);
        objectPlacer.placeObject(grid, TileType.ITEM, rooms.size(), rand);
        return rooms; // Floor farà List.copyOf(...)
    }
}
