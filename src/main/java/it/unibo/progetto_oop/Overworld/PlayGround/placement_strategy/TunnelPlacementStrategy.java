package it.unibo.progetto_oop.overworld.playground.placement_strategy;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.overworld.playground.data.StructureData;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Room;

public interface TunnelPlacementStrategy  {
    void connect(StructureData grid, List<Room> rooms, Random rand);
}
