package it.unibo.progetto_oop.overworld.enemy;

/**
 * Enum representing the types of enemies in the overworld.
 */
public enum EnemyType {
    /**
     * Enemy that follows a predefined path.
     */
    PATROLLER,

    /**
     * Enemy that follows the player.
     */
    FOLLOWER,

    /**
     * Enemy that doesn't move.
     */
    SLEEPER
}
