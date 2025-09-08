package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementStrategyImpl;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementStrategy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision.CombatCollision;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision.WallCollision;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.VisibilityUtil;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class FollowMovementStrategy implements MovementStrategy {
    /**
     * utility class to check visibility and line of sight.
     */
    private VisibilityUtil visibilityUtil;

    /**
     * patrol movement strategy to use when the player is not in sight.
     */
    private PatrolMovementStrategy patrolMovementStrategy;

    /**
     * wall checker to check for walls.
     */
    private WallCollision wallChecker;

    /**
     * combat transition checker to check for combat transitions.
     */
    private CombatCollision combatTransitionChecker;

    // constants
    /**
     * The distance within which the enemy can detect the player.
     */
    private static final int NEIGHBOUR_DISTANCE = 6;

    /**
     * Constructor for the FollowMovementStrategy class.
     * @param newWallChecker the wall checker
     * @param newCombatCollisionChecker the combat collision checker
     */
    public FollowMovementStrategy(final WallCollision newWallChecker,
    final CombatCollision newCombatCollisionChecker) {
        this.visibilityUtil = new VisibilityUtil(newWallChecker);
        this.patrolMovementStrategy = new PatrolMovementStrategy(
            newWallChecker, newCombatCollisionChecker);
        this.wallChecker = newWallChecker;
        this.combatTransitionChecker = newCombatCollisionChecker;
    }

    @Override
    public final MoveDirection executeMove(final Enemy context,
    final Player player, final MoveDirection currDirection) {
        Position currentPos = context.getCurrentPosition();
        Position targetPos = this.visibilityUtil.firstMove(
            context.getCurrentPosition(), player.getPosition());

        Position playerPos = player.getPosition();

        // if the player is in the enemy's line of sight,
        // move towards the player
        if (player != null
            && this.visibilityUtil.inLos(
                currentPos, playerPos, NEIGHBOUR_DISTANCE)
            && wallChecker.canEnemyEnter(targetPos)) {

            // if the player and the enemy are close enough
            // -> enter combat state
            if (this.combatTransitionChecker.
            checkCombatCollision(playerPos, targetPos)) {
                this.combatTransitionChecker.
                    initiateCombatTransition(context, player);
            }

            // not close enough -> move closer towards the player
            context.setPosition(targetPos);
            context.getGridNotifier().notifyEnemyMoved(currentPos, targetPos);
            return currDirection;

        } else { // else, continue patrolling
            return patrolMovementStrategy.
                executeMove(context, player, currDirection);
        }
    }
}
