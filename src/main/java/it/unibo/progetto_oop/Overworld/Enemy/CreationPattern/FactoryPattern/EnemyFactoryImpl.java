package it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern;

import it.unibo.progetto_oop.Combat.Position.Position;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.GenericEnemy;

public class EnemyFactoryImpl implements EnemyFactory {

    @Override
    public Enemy createpatrollerEnemy() {

        //TODO: Remove Placeholder values
        Enemy enemy = new GenericEnemy(100, 100, 10, new Position(0, 0));
        // set the state to patroller
        return enemy;
    }

    @Override
    public Enemy createFollowerEnemy() {
        //TODO: Remove Placeholder values
        Enemy enemy = new GenericEnemy(100, 100, 10, new Position(0, 0));
        // set the state to follower
        return enemy;
    }

    @Override
    public Enemy createSleeperEnemy() {
        //TODO: Remove Placeholder values
        Enemy enemy = new GenericEnemy(100, 100, 10, new Position(0, 0));
        // set the state to sleeper
        return enemy;
    }
    
}
