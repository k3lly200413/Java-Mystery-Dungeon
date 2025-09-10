package it.unibo.progetto_oop.overworld.enemy.movement_strategy.movement_strategy_impl;

import it.unibo.progetto_oop.overworld.combat_collision.CombatCollision;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementStrategy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision.WallCollision;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;



public class PatrolMovementStrategy implements MovementStrategy {
    /**
     * wall checker to check for walls.
     */
    private final WallCollision wallChecker;

    /**
     * combat transition checker to check for combat transitions.
     */
    private final CombatCollision combatTransitionChecker;

    /**
     * Constructor for the PatrolMovementStrategy class.
     * @param newWallChecker wall checker
     * @param newCombatTransitionChecker combat transition checker
     */
    public PatrolMovementStrategy(
    final WallCollision newWallChecker,
    final CombatCollision newCombatTransitionChecker) {
        this.wallChecker = newWallChecker;
        this.combatTransitionChecker = newCombatTransitionChecker;
    }

    @Override
    public final MoveDirection executeMove(
    final Enemy context,
    final Player player,
    final MoveDirection currDirection) {
        final Position currentPos = context.getCurrentPosition();
        // Initialize target position to current position
        Position targetPos = currentPos;

        MoveDirection moveDirection =
            currDirection; // Set the current direction

        switch (moveDirection) {
            case UP:
                targetPos = new Position(currentPos.x(), currentPos.y() - 1);
                break;
            case DOWN:
                targetPos = new Position(currentPos.x(), currentPos.y() + 1);
                break;
            case LEFT:
                targetPos = new Position(currentPos.x() - 1, currentPos.y());
                break;
            case RIGHT:
                targetPos = new Position(currentPos.x() + 1, currentPos.y());
                break;
            case NONE:
                break;
            default:
                throw new IllegalStateException(
                    "Unexpected value: " + moveDirection);
        }

        // Check if the target position is not the
        // same as the current position and is not a wall
        if (wallChecker.canEnemyEnter(targetPos)) {
            // if close enough to the player -> combat
            if (this.combatTransitionChecker
            .checkCombatCollision(player.getPosition(), targetPos)) {
                this.combatTransitionChecker.showCombat(context, player);
            }

            context.setPosition(targetPos);
            context.getGridNotifier().notifyEnemyMoved(currentPos, targetPos);

            return moveDirection;
        } else {
            moveDirection = reverseDirection(moveDirection);
        }
        return moveDirection;
    }


    private MoveDirection reverseDirection(final MoveDirection dir) {
        switch (dir) {
            case UP:
                return MoveDirection.DOWN;
            case DOWN:
                return MoveDirection.UP;
            case LEFT:
                return MoveDirection.RIGHT;
            case RIGHT:
                return MoveDirection.LEFT;
            default:
                return MoveDirection.NONE;
        }
    }
}
