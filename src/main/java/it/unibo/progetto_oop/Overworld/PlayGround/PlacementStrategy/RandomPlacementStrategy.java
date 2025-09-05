package it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy;

import java.util.Random;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;

public interface RandomPlacementStrategy {
    void placeObject(StructureData g, TileType type, int n, Random rand, Position player, int dist);
    Position placePlayer(StructureData g, TileType type, Random rand);
}
