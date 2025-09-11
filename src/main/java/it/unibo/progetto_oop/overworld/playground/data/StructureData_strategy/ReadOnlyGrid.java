package it.unibo.progetto_oop.overworld.playground.data.StructureData_strategy;

import it.unibo.progetto_oop.overworld.playground.data.TileType;

public interface ReadOnlyGrid {
    /*
     * Get the width of the grid.
     */
    int width();

    /*
     * Get the height of the grid.
     */
    int height();
    
    /**
     * Gets the tile type at the specified coordinates.
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @return the tile type at the given coordinates.
     */
    TileType get(int x, int y);

    /**
     * Checks if the given coordinates are within the bounds of the grid.
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @return true if the coordinates are within bounds, false otherwise.
     */
    boolean inBounds(final int x, final int y);
}
