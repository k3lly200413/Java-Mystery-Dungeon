package it.unibo.progetto_oop.overworld.playground.placement_strategy;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.overworld.playground.data.StructureData;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Room;

public interface TunnelPlacementStrategy  {
    /**
     * Connects the given rooms in the grid.
     *
     * @param grid  the structure data representing the grid
     * @param rooms the list of rooms to be connected
     * @param rand  the random generator for placement strategy
     */
    void connect(StructureData grid, List<Room> rooms, Random rand);
}
