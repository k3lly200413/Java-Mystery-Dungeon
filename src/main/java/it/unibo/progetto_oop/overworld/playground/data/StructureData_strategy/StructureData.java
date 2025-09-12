package it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy;

import it.unibo.progetto_oop.overworld.playground.data.TileType;

public interface StructureData {
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
     *
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @return the tile type at the given coordinates.
     */
    TileType get(int x, int y);

    /**
     * Sets the tile at the specified coordinates.
     *
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @param t the tile type to set.
     */
    void set(int x, int y, TileType t);

    /**
     * Fills the entire structure with the specified tile type.
     *
     * @param t the tile type to fill the structure with.
     */
    void fill(TileType t);

    /**
     * Checks if the given coordinates are within the bounds of the grid.
     *
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @return true if the coordinates are within bounds, false otherwise.
     */
    default boolean inBounds(final int x, final int y) {
        return x >= 0 && x < width() && y >= 0 && y < height();
    }
}
