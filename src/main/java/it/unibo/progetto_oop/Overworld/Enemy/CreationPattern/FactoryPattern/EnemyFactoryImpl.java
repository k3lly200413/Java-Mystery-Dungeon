package it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern;


import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.BossEnemy;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.GenericEnemy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementStrategy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementStrategyImpl.FollowMovementStrategy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementStrategyImpl.PatrolMovementStrategy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision.CombatCollision;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision.WallCollision;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.FollowerState;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.PatrollerState;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.SleeperState;
import it.unibo.progetto_oop.Overworld.GridNotifier.GridNotifier;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;

public class EnemyFactoryImpl implements EnemyFactory {
    WallCollision wallChecker;
    CombatCollision combatTransitionChecker;
    MovementUtil movementUtil;
    MovementStrategy patrolMovementStrategy;
    MovementStrategy followMovementStrategy;
    
    public EnemyFactoryImpl(WallCollision newWallChecker, CombatCollision newCombatTransitionChecker){
        this.wallChecker = newWallChecker;
        this.combatTransitionChecker = newCombatTransitionChecker;

        this.movementUtil = new MovementUtil(wallChecker);
        this.patrolMovementStrategy = new PatrolMovementStrategy(wallChecker, combatTransitionChecker);
        this.followMovementStrategy = new FollowMovementStrategy(wallChecker, combatTransitionChecker);
    }
    
    @Override
    public Enemy createPatrollerEnemy(int hp, int power, Position spawnPosition, boolean isVertical, GridNotifier gridNotifier) {
        // create generic enemy
        Enemy enemy = new GenericEnemy(hp, hp, power, spawnPosition, gridNotifier);

        // set the correct state
        enemy.setState(new PatrollerState(movementUtil, patrolMovementStrategy, isVertical));
        return enemy;
    }

    @Override
    public Enemy createFollowerEnemy(int hp, int power, Position spawnPosition, boolean isVertical, GridNotifier gridNotifier) {
        Enemy enemy = new GenericEnemy(hp, hp, power, spawnPosition, gridNotifier);

        enemy.setState(new FollowerState(movementUtil, followMovementStrategy, isVertical));
        return enemy;
    }

    @Override
    public Enemy createSleeperEnemy(int hp, int power, Position spawnPosition, boolean isVertical,  GridNotifier gridNotifier) {
        Enemy enemy = new GenericEnemy(hp, hp, power,spawnPosition, gridNotifier);

        enemy.setState(new SleeperState());
        return enemy;
    }

    @Override
    public Enemy createBossEnemy(int hp, int power, Position spawnPosition, boolean isVertical, GridNotifier gridNotifier) {
        Enemy enemy = new BossEnemy(hp, hp, power,spawnPosition, gridNotifier);

        enemy.setState(new SleeperState());
        return enemy;
    }
    
}
