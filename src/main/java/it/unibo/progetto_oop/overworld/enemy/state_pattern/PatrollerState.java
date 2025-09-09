package it.unibo.progetto_oop.overworld.enemy.state_pattern;

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
    public final void enterState(final Enemy context) {
        System.out.println("Entering " + this.getDescription());

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
    }

    @Override
    public final void update(final Enemy context, final Player player) {
        this.currentDirection = this.movementStrategy
            .executeMove(context, player, this.currentDirection);
    }

    @Override
    public final void onPlayerMoved(final Enemy context, final Player player) {
    }

    @Override
    public final EnemyType getType() {
        return EnemyType.PATROLLER;
    }

    @Override
    public String getDescription() {    
        return "Patroller State";
    }

}
