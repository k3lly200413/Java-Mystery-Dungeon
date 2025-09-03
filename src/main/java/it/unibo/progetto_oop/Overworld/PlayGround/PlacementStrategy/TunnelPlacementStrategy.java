package it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.Room;

public interface TunnelPlacementStrategy  {
    void connect(StructureData grid, List<Room> rooms, Random rand);
}
