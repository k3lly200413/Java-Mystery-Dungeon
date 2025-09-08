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
    /**
     * wall checker to check for walls.
     */
    private WallCollision wallChecker;

    /**
     * combat transition checker to check for combat transitions.
     */
    private CombatCollision combatTransitionChecker;

    /**
     * movement utility class to help with movement.
     */
    private MovementUtil movementUtil;

    /**
     * patrol movement strategy.
     */
    private MovementStrategy patrolMovementStrategy;

    /**
     * follow movement strategy.
     */
    private MovementStrategy followMovementStrategy;

    /**
     * Constructor of the EnemyFactoryImpl class.
     * @param newWallChecker wall checker to check for walls
     * @param newCombatTransitionChecker combat transition checker
     * to check for combat transitions
     */
    public EnemyFactoryImpl(final WallCollision newWallChecker,
    final CombatCollision newCombatTransitionChecker) {
        this.wallChecker = newWallChecker;
        this.combatTransitionChecker = newCombatTransitionChecker;

        this.movementUtil = new MovementUtil(wallChecker);
        this.patrolMovementStrategy =
            new PatrolMovementStrategy(wallChecker, combatTransitionChecker);
        this.followMovementStrategy =
            new FollowMovementStrategy(wallChecker, combatTransitionChecker);
    }

    @Override
    public final Enemy createPatrollerEnemy(final int hp, final int power,
    final Position spawnPosition,
    final boolean isVertical,
    final GridNotifier gridNotifier) {

        // create generic enemy
        Enemy enemy =
            new GenericEnemy(hp, hp, power, spawnPosition, gridNotifier);

        // set the correct state
        enemy.setState(
            new PatrollerState(
                movementUtil, patrolMovementStrategy, isVertical));
        return enemy;
    }

    @Override
    public final Enemy createFollowerEnemy(final int hp, final int power,
    final Position spawnPosition,
    final boolean isVertical,
    final GridNotifier gridNotifier) {
        Enemy enemy =
            new GenericEnemy(hp, hp, power, spawnPosition, gridNotifier);

        enemy.setState(
            new FollowerState(
                movementUtil, followMovementStrategy, isVertical));
        return enemy;
    }

    @Override
    public final Enemy createSleeperEnemy(final int hp, final int power,
    final Position spawnPosition,
    final boolean isVertical,
    final GridNotifier gridNotifier) {
        Enemy enemy =
            new GenericEnemy(hp, hp, power, spawnPosition, gridNotifier);

        enemy.setState(new SleeperState());
        return enemy;
    }

    @Override
    public final Enemy createBossEnemy(final int hp, final int power,
    final Position spawnPosition,
    final boolean isVertical,
    final GridNotifier gridNotifier) {
        Enemy enemy =
            new BossEnemy(hp, hp, power, spawnPosition, gridNotifier);

        enemy.setState(new SleeperState());
        return enemy;
    }
}
