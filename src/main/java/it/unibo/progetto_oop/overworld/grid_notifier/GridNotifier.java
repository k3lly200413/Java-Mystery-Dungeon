package it.unibo.progetto_oop.overworld.grid_notifier;

import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.listner.grid_updater.GridUpdater;

public class GridNotifier {
    /**
     * The GridUpdater responsible for managing/updating the state of the grid.
     */
    private GridUpdater grid;
    /**
     * The ListEnemyUpdater responsible for updating the list of enemies.
     */
    private ListEnemyUpdater listEnemyUpdater;

    /**
     * The ListItemUpdater responsible for updating the list of items.
     */
    private ListItemUpdater listItemUpdater;

    /**
     * Constructs a GridNotifier with the specified GridUpdater.
     *
     * @param newGrid GridUpdater responsible for updating the state of the grid
     */
    public GridNotifier(final GridUpdater newGrid) {
        this.grid = newGrid;
    }

    /**
     * Sets the GridUpdater responsible for updating the state of the grid.
     *
     * @param newGrid the GridUpdater to set
     */
    public final void setGridUpdater(final GridUpdater newGrid) {
        this.grid = newGrid;
    }

    /**
     * Sets the ListEnemyUpdater responsible for updating the list of enemies.
     *
     * @param newListEnemyUpdater the ListEnemyUpdater to set
     */
    public final void setListEnemyUpdater(
            final ListEnemyUpdater newListEnemyUpdater) {
        this.listEnemyUpdater = newListEnemyUpdater;
    }

    /**
     * Sets the ListItemUpdater responsible for updating the list of items.
     *
     * @param newListItemUpdater the ListItemUpdater to set
     */
    public final void setListItemUpdater(
            final ListItemUpdater newListItemUpdater) {
        this.listItemUpdater = newListItemUpdater;
    }

    //------gridUpdater methods-------
    /**
     * Notifies the grid that an enemy has moved from one position to another.
     *
     * @param from the starting position of the enemy
     * @param to the destination position of the enemy
     */
    public final void notifyEnemyMoved(final Position from, final Position to) {
        if (grid != null) {
            grid.onEnemyMove(from, to);
        }
    }

    /**
     * Notifies the grid that the player has moved from one position to another.
     *
     * @param from the starting position of the player
     * @param to the destination position of the player
     */
    public final void notifyPlayerMoved(
            final Position from, final Position to) {
        if (grid != null) {
            grid.onPlayerMove(from, to);
        }
    }

    /**
     * Notifies the grid that an item has been removed from a specific position.
     *
     * @param at the position where the item was removed
     */
    public final void notifyItemRemoved(final Position at) {
        if (grid != null) {
            grid.onItemRemoved(at);
        }
    }

    /**
     * Notifies the grid that an enemy has been removed from position.
     *
     * @param at the position where the enemy was removed
     */
    public final void notifyEnemyRemoved(final Position at) {
        if (grid != null) {
            grid.onEnemyRemoved(at);
        }
    }

    /**
     * Notifies the ListEnemyUpdater that an enemy has
     * been removed from a position.
     *
     * @param at the position where the enemy was removed
     */
    public final void notifyListEnemyRemoved(final Position at) {
        if (listEnemyUpdater != null) {
            listEnemyUpdater.onEnemyRemoved(at);
        }
    }

    /**
     * Notifies the ListItemUpdater that an item
     * has been removed from a position.
     *
     * @param at the position where the item was removed
     */
    public final void notifyListItemRemoved(final Position at) {
        if (listItemUpdater != null) {
            listItemUpdater.onItemRemoved(at);
        }
    }
}
