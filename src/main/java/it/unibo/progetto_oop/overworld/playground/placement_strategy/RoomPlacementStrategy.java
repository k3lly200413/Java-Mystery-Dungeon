package it.unibo.progetto_oop.overworld.playground.placement_strategy;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.overworld.playground.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.StructureData;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Room;


@FunctionalInterface
public interface RoomPlacementStrategy  {
    /**
     * Places rooms on the grid based on the given configuration.
     *
     * @param grid the structure data representing the grid
     * @param outRooms the list to store the placed rooms
     * @param rand the random number generator for placement
     * @param config the floor configuration for placement rules
     */
    void placeRooms(
        StructureData grid,
        List<Room> outRooms,
        Random rand,
        FloorConfig config
    );
}
