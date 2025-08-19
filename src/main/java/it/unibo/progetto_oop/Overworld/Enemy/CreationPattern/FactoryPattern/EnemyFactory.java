package it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern;

import it.unibo.progetto_oop.Overworld.Enemy.Enemy;

public interface EnemyFactory {
    public Enemy createpatrollerEnemy(); 
    public Enemy createFollowerEnemy(); 
    public Enemy createSleeperEnemy();
}
