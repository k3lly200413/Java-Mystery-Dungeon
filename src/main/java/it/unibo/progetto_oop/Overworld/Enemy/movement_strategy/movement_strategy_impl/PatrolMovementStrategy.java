package it.unibo.progetto_oop.Overworld.enemy.movement_strategy.movement_strategy_impl;

import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Overworld.combat_collision.CombatCollision;
import it.unibo.progetto_oop.Overworld.enemy.creation_pattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.enemy.movement_strategy.MovementStrategy;
import it.unibo.progetto_oop.Overworld.enemy.movement_strategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.Overworld.enemy.movement_strategy.wall_collision.WallCollision;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;



public class PatrolMovementStrategy implements MovementStrategy {
    /**
     * The direction of this patrol movement.
     */
    private MoveDirection moveDirection;

    /**
     * wall checker to check for walls.
     */
    private WallCollision wallChecker;

    /**
     * combat transition checker to check for combat transitions.
     */
    private CombatCollision combatTransitionChecker;

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
        Position currentPos = context.getCurrentPosition();
        // Initialize target position to current position
        Position targetPos = currentPos;

        this.moveDirection = currDirection; // Set the current direction

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
                System.out.println("No direction given ");
                break;
            default:
                break;
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

            return this.moveDirection;
        } else {
            this.moveDirection = reverseDirection(this.moveDirection);
        }
        return this.moveDirection;
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
