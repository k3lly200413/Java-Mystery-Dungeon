package it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;

public interface EnemyFactory {
    public Enemy createPatrollerEnemy(); 
    public Enemy createFollowerEnemy(); 
    public Enemy createSleeperEnemy();
}
