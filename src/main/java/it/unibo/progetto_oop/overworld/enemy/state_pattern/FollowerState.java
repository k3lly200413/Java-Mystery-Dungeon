package it.unibo.progetto_oop.overworld.enemy.state_pattern;

import it.unibo.progetto_oop.overworld.enemy.EnemyType;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementStrategy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementUtil;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.overworld.player.Player;

/**
 * Represents the follower state for an enemy.
 */
public class FollowerState implements GenericEnemyState {
    /**
     * The current movement direction of the follower enemy.
     */
    private MoveDirection currentDirection;

    /**
     * Utility class for movement-related operations.
     */
    private final MovementUtil movementUtil;

    /**
     * The movement strategy used by the follower enemy.
     */
    private final MovementStrategy movementStrategy;

    /**
     * Indicates whether the follower moves
     * vertically (true) or horizontally (false).
     */
    private final boolean isVertical;

    /**
     * Constructor for FollowerState.
     *
     * @param newMovementUtil the movement utility
     * @param newMovementStrategy the movement strategy
     * @param vertical whether the follower moves vertically
     */
    public FollowerState(
        final MovementUtil newMovementUtil,
        final MovementStrategy newMovementStrategy,
        final boolean vertical) {
        this.movementUtil = newMovementUtil;
        this.isVertical = vertical;
        this.movementStrategy = newMovementStrategy;
    }

    /**
     * Initially the follower will act like a patroller.
     */
    @Override
    public void enterState(final Enemy context) {
        currentDirection = movementUtil
            .getInitialGeneralMoveDirection(
                context.getCurrentPosition(), this.isVertical);

        if (this.currentDirection == MoveDirection.NONE) {
            this.currentDirection = this.isVertical
                ? MoveDirection.DOWN : MoveDirection.UP;
        }
    }

    @Override
    public final void exitState(final Enemy context) {
        throw new UnsupportedOperationException(
            "FollowerState cannot be exited");
    }

    @Override
    public final void update(final Enemy context, final Player player) {
        this.currentDirection = this.movementStrategy
            .executeMove(context, player, this.currentDirection);
    }

    @Override
    public final EnemyType getType() {
        return EnemyType.FOLLOWER;
    }

    @Override
    public final String getDescription() {
        return "Follower State";
    }

}
