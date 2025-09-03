package it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy;

import java.util.Random;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;

public interface RandomPlacementStrategy {
    void placeObject(StructureData grid, TileType type, int n, Random rand);
}