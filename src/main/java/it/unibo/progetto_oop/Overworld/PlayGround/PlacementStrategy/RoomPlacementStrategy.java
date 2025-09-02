package it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.FloorConfig;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.Room;

public interface RoomPlacementStrategy  {
    void placeRooms(StructureData grid, List<Room> outRooms, Random rand, FloorConfig config);
}