package it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;

import java.util.Set;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;


public interface EnemyFactory {
    public Enemy createPatrollerEnemy(int hp, int power, Position spawnPosition, boolean isVertical, Set<Position> walls); 
    public Enemy createFollowerEnemy(int hp, int power, Position spawnPosition, boolean isVertical, Set<Position> walls); 
    public Enemy createSleeperEnemy(int hp, int power, Position spawnPosition, boolean isVertical, Set<Position> walls);
}
