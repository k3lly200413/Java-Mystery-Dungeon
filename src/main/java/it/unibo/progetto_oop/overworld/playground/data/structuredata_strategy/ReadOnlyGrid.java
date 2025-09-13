package it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy;

import it.unibo.progetto_oop.overworld.playground.data.TileType;

/**
 * Read-only interface for accessing grid structure data.
 */
public interface ReadOnlyGrid {

    /**
     * Returns the width of the grid.
     *
     * @return the width of the grid
     */
    int width();

    /**
     * Returns the height of the grid.
     *
     * @return the height of the grid
     */
    int height();

    /**
     * Returns the tile type at the specified coordinates.
     *
     * @param x the column index (0-based)
     * @param y the row index (0-based)
     * @return the tile type at (x, y)
     */
    TileType get(int x, int y);

    /**
     * Checks whether the given coordinates are within the grid bounds.
     *
     * @param x the column index (0-based)
     * @param y the row index (0-based)
     * @return {@code true} if (x, y) is within bounds; {@code false} otherwise
     */
    boolean inBounds(int x, int y);
}
