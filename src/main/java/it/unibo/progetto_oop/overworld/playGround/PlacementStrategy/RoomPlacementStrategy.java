package it.unibo.progetto_oop.overworld.playground.PlacementStrategy;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.overworld.playground.DungeonLogic.Room;
import it.unibo.progetto_oop.overworld.playground.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playground.data.StructureData;

public interface RoomPlacementStrategy  {
    void placeRooms(StructureData grid, List<Room> outRooms, Random rand, FloorConfig config);
}
