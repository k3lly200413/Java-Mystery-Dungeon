package it.unibo.progetto_oop.overworld.enemy.movement_strategy.movement_strategy_impl;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.progetto_oop.overworld.combat_collision.CombatCollision;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementStrategy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.VisibilityUtil;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision.WallCollision;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * utility class to check visibility and line of sight.
 */
@SuppressFBWarnings("EI_EXPOSE_REP2")
public class FollowMovementStrategy implements MovementStrategy {

    /**
     * The distance within which the enemy can detect the player.
     */
    private static final int NEIGHBOUR_DISTANCE = 6;

    /**
     * Utility class for visibility-related operations.
     */
    private final VisibilityUtil visibilityUtil;

    /**
     * patrol movement strategy to use when the player is not in sight.
     */
    private final PatrolMovementStrategy patrolMovementStrategy;

    /**
     * wall checker to check for walls.
     */
    private final WallCollision wallChecker;

    /**
     * combat transition checker to check for combat transitions.
     */
    private final CombatCollision combatTransitionChecker;

    /**
     * Constructor for the FollowMovementStrategy class.
     *
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
        final Position currentPos = context.getCurrentPosition();

        if (player != null) {
            final Position playerPos = player.getPosition();
            final Position targetPos = this.visibilityUtil.firstMove(
                currentPos, playerPos);

            // if the player is in the enemy's line of sight,
            // move towards the player
            if (this.visibilityUtil.inLos(
                    currentPos, playerPos, NEIGHBOUR_DISTANCE)
                && wallChecker.canEnemyEnter(targetPos)) {

                // if the player and the enemy are close enough -> enter combat state
                if (this.combatTransitionChecker
                        .checkCombatCollision(playerPos, targetPos)) {
                    this.combatTransitionChecker.showCombat(context, player);
                }

                // not close enough -> move closer towards the player
                context.setPosition(targetPos);
                context.getGridNotifier().notifyEnemyMoved(currentPos, targetPos);
                return currDirection;
            }
        }
        // keep patrolling
        return patrolMovementStrategy.executeMove(context, player, currDirection);
    }
}
