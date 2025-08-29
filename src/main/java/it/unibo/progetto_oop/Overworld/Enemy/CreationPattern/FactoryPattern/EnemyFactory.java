package it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.MVC.viewManager;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;

public interface EnemyFactory {
    public Enemy createPatrollerEnemy(int hp, int power, Position spawnPosition, boolean isVertical, OverworldModel model, viewManager game); 
    public Enemy createFollowerEnemy(int hp, int power, Position spawnPosition, boolean isVertical, OverworldModel model, viewManager game); 
    public Enemy createSleeperEnemy(int hp, int power, Position spawnPosition, boolean isVertical, OverworldModel model, viewManager game);
}
