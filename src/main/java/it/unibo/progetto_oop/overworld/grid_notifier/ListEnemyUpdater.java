package it.unibo.progetto_oop.overworld.grid_notifier;

import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * Functional interface for updating list enemies when an enemy is removed.
 */
@FunctionalInterface
public interface ListEnemyUpdater {
    /**
     * Called when an enemy is removed from the specified position.
     *
     * @param at the position where the enemy was removed
     */
    void onEnemyRemoved(Position at);
}
