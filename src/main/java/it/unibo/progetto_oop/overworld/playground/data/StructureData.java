package it.unibo.progetto_oop.overworld.playground.data;

public interface StructureData {
    /**
     * Gets the width of the structure.
     * @return the width as an integer.
     */
    int width();

    /**
     * Gets the height of the structure.
     * @return the height as an integer.
     */
    int height();
    /**
     * Checks if the given coordinates are within the bounds of the structure.
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @return true if the coordinates are within bounds, false otherwise.
     */
    boolean inBounds(int x, int y);

    /**
     * Gets the tile type at the specified coordinates.
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @return the tile type at the given coordinates.
     */
    TileType get(int x, int y);

    /**
     * Sets the tile at the specified coordinates.
     * @param x the x-coordinate.
     * @param y the y-coordinate.
     * @param t the tile type to set.
     */
    void set(int x, int y, TileType t);

    /**
     * Fills the entire structure with the specified tile type.
     * @param t the tile type to fill the structure with.
     */
    void fill(TileType t);
}
