
/**
 * Represents the patroller state for an enemy.
 */
package it.unibo.progetto_oop.overworld.enemy.state_pattern;

import java.nio.channels.UnsupportedAddressTypeException;

import it.unibo.progetto_oop.overworld.enemy.EnemyType;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementStrategy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementUtil;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.overworld.player.Player;

public class PatrollerState implements GenericEnemyState {
    /**
     * The current movement direction of the patroller enemy.
     */
    private MoveDirection currentDirection;

    /**
     * Utility class for movement-related operations.
     */
    private final MovementUtil movementUtil;

    /**
     * Indicates whether the patroller moves
     * vertically (true) or horizontally (false).
     */
    private final boolean isVertical;

    /**
     * The movement strategy used by the patroller enemy.
     */
    private final MovementStrategy movementStrategy;

    /**
     * Constructor for PatrollerState.
     *
     * @param newMovementUtil the movement utility
     * @param newMovementStrategy the movement strategy
     * @param vertical whether the patroller moves vertically
     */
    public PatrollerState(final MovementUtil newMovementUtil,
        final MovementStrategy newMovementStrategy, final boolean vertical) {
        this.movementUtil = newMovementUtil;
        this.isVertical = vertical;
        this.movementStrategy = newMovementStrategy;
    }

    @Override
    /**
     * Initially the patroller will choose a direction based on its position.
     * 
     * @param context the enemy in case
     */
    public void enterState(final Enemy context) {
        currentDirection = movementUtil
            .getInitialGeneralMoveDirection(
                context.getCurrentPosition(), this.isVertical);

        if (this.currentDirection == MoveDirection.NONE) {
            this.currentDirection = this.isVertical
                ? MoveDirection.DOWN : MoveDirection.UP;
        }
    }

    /**
     * No specific exit actions for the patroller state.
     */
    @Override
    public void exitState(final Enemy context) {
        throw new UnsupportedOperationException(
            "PatrollerState does not support exitState operation.");
    }

    /**
     * Update the enemy's movement based on the current strategy.
     * 
     * @param context the enemy
     * @param player the player
     * 
     * @return the new direction of movement (if needed)
     */
    @Override
    public final void update(final Enemy context, final Player player) {
        this.currentDirection = this.movementStrategy
            .executeMove(context, player, this.currentDirection);
    }

    /**
     * No specific actions when the player moves for the patroller state.
     */
    @Override
    public void onPlayerMoved(final Enemy context, final Player player) {
        throw new UnsupportedOperationException(
            "Patroller state doesn't support onPlayerMoved operation");
    }

    /**
     * Get the type of enemy associated with this state.
     * 
     * @return the enemy type (PATROLLER)
     */
    @Override
    public EnemyType getType() {
        return EnemyType.PATROLLER;
    }

    /**
     * Get a description of the enemy's current state.
     * 
     * @return the state description
     */
    @Override
    public String getDescription() {
        return "Patroller State";
    }
}
