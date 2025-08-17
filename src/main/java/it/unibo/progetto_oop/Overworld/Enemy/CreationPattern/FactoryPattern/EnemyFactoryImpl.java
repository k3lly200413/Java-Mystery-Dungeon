package it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.GenericEnemy;

public class EnemyFactoryImpl implements EnemyFactory {

    @Override
    public Enemy createpatrollerEnemy() {
        // Enemy enemy = new GenericEnemy();
        // set the state to patroller
        // return enemy;
    }

    @Override
    public Enemy createFollowerEnemy() {
        // Enemy enemy = new GenericEnemy();
        // set the state to follower
        // return enemy;
    }

    @Override
    public Enemy createSleeperEnemy() {
        // Enemy enemy = new GenericEnemy();
        // set the state to sleeper
        // return enemy;
    }
    
}
