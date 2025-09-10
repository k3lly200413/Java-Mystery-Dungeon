package it.unibo.progetto_oop.overworld.playground.data;

public enum TileType {
    //-----BASE GRID TILES------
    /** Represents a wall tile in the base grid. */
    WALL,
    /** Represents a room tile in the base grid. */
    ROOM,
    /** Represents a tunnel tile in the base grid. */
    TUNNEL,
    /** Represents a stairs tile in the entity grid. */
    STAIRS,

    //-----ENTITY GRID TILES------
    /** Represents the player's tile in the entity grid. */
    PLAYER,
    /** Represents an enemy tile in the entity grid. */
    ENEMY,
    /** Represents a boss tile in the entity grid. */
    BOSS,
    /** Represents an item tile in the entity grid. */
    ITEM,
    /** Represents an empty tile in the entity grid. */
    NONE;
}
