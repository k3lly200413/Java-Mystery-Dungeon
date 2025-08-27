package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
//SINGLETON??
public final class FloorGenerator {
    private final RoomPlacementStrategy roomPlacement;
    private final TunnelPlacementStrategy tunnelPlacement;
    private final RandomPlacementStrategy objectPlacer;
    private final Random rand;

    public FloorGenerator(RoomPlacementStrategy roomPlacement, TunnelPlacementStrategy tunnelPlacement,
                            RandomPlacementStrategy objectPlacer, Random rand) {
        this.roomPlacement = Objects.requireNonNull(roomPlacement);
        this.tunnelPlacement = Objects.requireNonNull(tunnelPlacement);
        this.objectPlacer = Objects.requireNonNull(objectPlacer);
        this.rand = Objects.requireNonNull(rand);
    }

    public List<Room> generate(StructureData grid, FloorConfig conf) {
        if (conf.nRooms() <= 0)
            throw new IllegalArgumentException("nRooms must be > 0");
        grid.fill(TileType.WALL);
        List<Room> rooms = new ArrayList<>();
        roomPlacement.placeRooms(grid, rooms, rand, conf);
        if (rooms.size() >= 2) {
            tunnelPlacement.connect(grid, rooms, rand);
        }
        objectPlacer.placeObject(grid, TileType.STAIRS, 1, rand);
        objectPlacer.placeObject(grid, TileType.PLAYER, 1, rand);
        objectPlacer.placeObject(grid, TileType.ENEMY, rooms.size()*2, rand);
        objectPlacer.placeObject(grid, TileType.ITEM, rooms.size(), rand);
        return rooms; // Floor farà List.copyOf(...)
    }
}
