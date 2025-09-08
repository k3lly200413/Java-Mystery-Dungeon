package it.unibo.progetto_oop.overworld.playGround.PlacementStrategy;

import java.util.Random;

import it.unibo.progetto_oop.overworld.playGround.data.Position;
import it.unibo.progetto_oop.overworld.playGround.data.StructureData;
import it.unibo.progetto_oop.overworld.playGround.data.TileType;

public interface RandomPlacementStrategy {
    void placeObject(StructureData base, StructureData entity, TileType type, int n, Random rand, Position player, int dist);
    Position placePlayer(StructureData base, StructureData entity, Random rand);
    void placeOnBase(StructureData base, TileType type, int n, Random rand);
}
