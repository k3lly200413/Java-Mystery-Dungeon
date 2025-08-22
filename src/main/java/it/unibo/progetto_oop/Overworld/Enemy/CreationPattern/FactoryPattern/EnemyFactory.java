package it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Combat.Position.Position;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;

public interface EnemyFactory {
    public Enemy createPatrollerEnemy(int hp, int power, Position spawnPosition, boolean isVertical, OverworldModel model); 
    public Enemy createFollowerEnemy(int hp, int power, Position spawnPosition, boolean isVertical, OverworldModel model); 
    public Enemy createSleeperEnemy(int hp, int power, Position spawnPosition, boolean isVertical, OverworldModel model);
}
