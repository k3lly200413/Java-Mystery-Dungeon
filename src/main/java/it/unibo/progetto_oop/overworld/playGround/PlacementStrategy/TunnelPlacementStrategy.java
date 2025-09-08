package it.unibo.progetto_oop.overworld.playground.PlacementStrategy;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.overworld.playground.DungeonLogic.Room;
import it.unibo.progetto_oop.overworld.playground.data.StructureData;

public interface TunnelPlacementStrategy  {
    void connect(StructureData grid, List<Room> rooms, Random rand);
}
