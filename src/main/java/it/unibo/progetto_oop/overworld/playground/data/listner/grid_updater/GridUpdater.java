package it.unibo.progetto_oop.overworld.playground.data.listner.grid_updater;

import it.unibo.progetto_oop.overworld.playground.data.Position;

public interface GridUpdater {
    /**
     * Called when the player moves from one position to another.
     *
     * @param from the starting position of the player
     * @param to the destination position of the player
     */
    void onPlayerMove(Position from, Position to);

    /**
     * Called when an enemy moves from one position to another.
     *
     * @param from the starting position of the enemy
     * @param to the destination position of the enemy
     */
    void onEnemyMove(Position from, Position to);
    
    /**
     * Called when an item is removed from a specific position.
     *
     * @param at the position where the item was removed
     */
    void onItemRemoved(Position at);

    /**
     * Called when an enemy is removed from a specific position.
     *
     * @param at the position where the enemy was removed
     */
    void onEnemyRemoved(Position at);
}
