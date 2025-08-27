package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.Random;

public interface RandomPlacementStrategy {
    void placeObject(StructureData grid, TileType type, int n, Random rand);
}
