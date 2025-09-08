package it.unibo.progetto_oop.overworld.playground.placement_strategy;

import java.util.Random;

import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.StructureData;
import it.unibo.progetto_oop.overworld.playground.data.TileType;

public interface RandomPlacementStrategy {
    void placeObject(StructureData base, StructureData entity, TileType type, int n, Random rand, Position player, int dist);
    Position placePlayer(StructureData base, StructureData entity, Random rand);
    void placeOnBase(StructureData base, TileType type, int n, Random rand);
}
