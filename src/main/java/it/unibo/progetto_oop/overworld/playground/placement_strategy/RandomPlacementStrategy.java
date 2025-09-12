package it.unibo.progetto_oop.overworld.playground.placement_strategy;

import java.util.Random;

import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ReadOnlyGrid;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.StructureData;

/**
 * Strategy interface for random placement of objects and players in a grid structure.
 */
public interface RandomPlacementStrategy {
    /**
     * Places an object on the entity structure.
     *
     * @param base    The base structure data.
     * @param entity  The entity structure data.
     * @param type    The type of tile to place.
     * @param n       The number of tiles to place.
     * @param rand    The random generator for placement.
     * @param player  The player's position.
     * @param dist    The minimum distance from the player.
     */
    void placeObject(
        ReadOnlyGrid base,
        StructureData entity,
        TileType type,
        int n,
        Random rand,
        Position player,
        int dist
    );
    
    /**
     * Places the player on the entity structure.
     *
     * @param base    The base structure data.
     * @param entity  The entity structure data.
     * @param rand    The random generator for placement.
     * @return The position where the player is placed.
     */
    Position placePlayer(ReadOnlyGrid base, StructureData entity, Random rand);

    /**
     * Places tiles of a specific type on the base structure.
     *
     * @param base    The base structure data.
     * @param type    The type of tile to place.
     * @param n       The number of tiles to place.
     * @param rand    The random generator for placement.
     */
    void placeOnBase(StructureData base, TileType type, int n, Random rand);
}
