package it.unibo.progetto_oop.overworld.playGround.PlacementStrategy;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.overworld.playGround.DungeonLogic.Room;
import it.unibo.progetto_oop.overworld.playGround.data.StructureData;

public interface TunnelPlacementStrategy  {
    void connect(StructureData grid, List<Room> rooms, Random rand);
}
