package it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern;

<<<<<<< HEAD
import java.util.Set;

=======
>>>>>>> 193bbdc31a0a30b1ddfa2952e5f3c0e623bcbbaa
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.GenericEnemy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementStrategy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.VisibilityUtil;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.PatrolMovementStrategy.PatrolMovementStrategy;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.FollowerState;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.PatrollerState;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.SleeperState;
import it.unibo.progetto_oop.Overworld.MVC.OverworldApplication;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.combat.position.Position;

public class EnemyFactoryImpl implements EnemyFactory {

    MovementUtil movementUtil = new MovementUtil();
    MovementStrategy patrolMovementStrategy = new PatrolMovementStrategy();
    
    @Override
    public Enemy createPatrollerEnemy(int hp, int power, Position spawnPosition, boolean isVertical, Set<Position> walls) {
        // create generic enemy
        Enemy enemy = new GenericEnemy(hp, hp, power, spawnPosition, walls);

        // set the correct state
<<<<<<< HEAD
        enemy.setState(new PatrollerState(movementUtil, patrolMovementStrategy, isVertical));
=======
        enemy.setState(new PatrollerState(movementUtil, patrolMovementStrategy, isVertical), model);
>>>>>>> 193bbdc31a0a30b1ddfa2952e5f3c0e623bcbbaa
        return enemy;
    }

    @Override
    public Enemy createFollowerEnemy(int hp, int power, Position spawnPosition, boolean isVertical, Set<Position> walls) {
        VisibilityUtil visibilityUtil = new VisibilityUtil();
        
        Enemy enemy = new GenericEnemy(hp, hp, power, spawnPosition, walls);

<<<<<<< HEAD
        enemy.setState(new FollowerState(visibilityUtil, movementUtil, patrolMovementStrategy, isVertical));
=======
        enemy.setState(new FollowerState(visibilityUtil, movementUtil, patrolMovementStrategy, isVertical), model);
>>>>>>> 193bbdc31a0a30b1ddfa2952e5f3c0e623bcbbaa
        return enemy;
    }

    @Override
    public Enemy createSleeperEnemy(int hp, int power, Position spawnPosition, boolean isVertical, Set<Position> walls) {
        Enemy enemy = new GenericEnemy(hp, hp, power,spawnPosition, walls);

        enemy.setState(new SleeperState());

        return enemy;
    }
    
}
