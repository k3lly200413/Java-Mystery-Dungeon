package it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern;

import java.util.Set;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.GenericEnemy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementStrategy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.PatrolMovementStrategy.PatrolMovementStrategy;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.FollowerState;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.PatrollerState;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.SleeperState;
import it.unibo.progetto_oop.Overworld.GridNotifier.GridNotifier;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;

public class EnemyFactoryImpl implements EnemyFactory {

    MovementUtil movementUtil = new MovementUtil();
    MovementStrategy patrolMovementStrategy = new PatrolMovementStrategy();
    
    @Override
    public Enemy createPatrollerEnemy(int hp, int power, Position spawnPosition, boolean isVertical, Set<Position> walls, GridNotifier gridNotifier) {
        // create generic enemy
        Enemy enemy = new GenericEnemy(hp, hp, power, spawnPosition, walls, gridNotifier);

        // set the correct state
        enemy.setState(new PatrollerState(movementUtil, patrolMovementStrategy, isVertical));
        return enemy;
    }

    @Override
    public Enemy createFollowerEnemy(int hp, int power, Position spawnPosition, boolean isVertical, Set<Position> walls,  GridNotifier gridNotifier) {
        Enemy enemy = new GenericEnemy(hp, hp, power, spawnPosition, walls, gridNotifier);

        enemy.setState(new FollowerState(movementUtil, patrolMovementStrategy, isVertical));
        return enemy;
    }

    @Override
    public Enemy createSleeperEnemy(int hp, int power, Position spawnPosition, boolean isVertical, Set<Position> walls,  GridNotifier gridNotifier) {
        Enemy enemy = new GenericEnemy(hp, hp, power,spawnPosition, walls, gridNotifier);

        enemy.setState(new SleeperState());
        return enemy;
    }
    
}