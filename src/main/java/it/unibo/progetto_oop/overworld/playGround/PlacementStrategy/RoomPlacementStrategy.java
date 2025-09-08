package it.unibo.progetto_oop.overworld.playGround.placementStrategy;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.overworld.playGround.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playGround.data.StructureData;
import it.unibo.progetto_oop.overworld.playGround.dungeonLogic.Room;

public interface RoomPlacementStrategy  {
    void placeRooms(StructureData grid, List<Room> outRooms, Random rand, FloorConfig config);
}
