package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
//SINGLETON??
public final class FloorGenerator {
    private final int nRooms;
    private final RoomPlacementStrategy roomPlacement;
    private final TunnelPlacementStrategy tunnelPlacement;
    private final Random rand;

    public FloorGenerator(int nRooms,
                          RoomPlacementStrategy roomPlacement,
                          TunnelPlacementStrategy tunnelPlacement,
                          Random rand) {
        if (nRooms <= 0) throw new IllegalArgumentException("nRooms must be > 0");
        this.nRooms = nRooms;
        this.roomPlacement = Objects.requireNonNull(roomPlacement);
        this.tunnelPlacement = Objects.requireNonNull(tunnelPlacement);
        this.rand = Objects.requireNonNull(rand);
    }

    public List<Room> generate(StructureData grid) {
        grid.fill(TileType.WALL);
        List<Room> rooms = new ArrayList<>();
        roomPlacement.placeRooms(grid, rooms, rand, grid.width(), grid.height(), nRooms);
        if (rooms.size() >= 2) {
            tunnelPlacement.connect(grid, rooms, rand);
        }
        return rooms; // Floor farà List.copyOf(...)
    }
}
