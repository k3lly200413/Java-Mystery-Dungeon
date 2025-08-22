package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.List;
import java.util.Random;

public interface RoomPlacementStrategy  {
    void placeRooms(StructureData grid, List<Room> outRooms, Random rand, FloorConfig config);
}
