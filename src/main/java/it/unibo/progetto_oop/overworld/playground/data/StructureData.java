package it.unibo.progetto_oop.overworld.playground.data;

public interface StructureData {
    int width();
    int height();
    boolean inBounds(int x, int y); // Check if the coordinates are within the bounds of the structure
    TileType get(int x, int y);
    void set(int x, int y, TileType t);
    void fill(TileType t);
}
