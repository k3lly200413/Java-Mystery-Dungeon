package it.unibo.progetto_oop.overworld.grid_notifier;

import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * Functional interface for updating list items when an item is removed.
 */
@FunctionalInterface
public interface ListItemUpdater {
    /**
     * Called when an item is removed from the list.
     *
     * @param at the position of the item that was removed
     */
    void onItemRemoved(Position at);
}
