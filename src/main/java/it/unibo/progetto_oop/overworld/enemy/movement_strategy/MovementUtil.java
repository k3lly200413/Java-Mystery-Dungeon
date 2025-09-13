package it.unibo.progetto_oop.overworld.enemy.movement_strategy;

import java.util.Optional;
import java.util.function.ToIntFunction;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision.WallCollision;
import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * This class is used by the follower and patroller enemies.
 */
public class MovementUtil {

    /**
     * checker for wall collisions.
     */
    private final WallCollision checker;

    /**
     * Enum representing the possible movement directions for an enemy.
     */
    public enum MoveDirection {
        /**
         * up movement direction.
         */
        UP,

        /**
         * down movement direction.
         */
        DOWN,

        /**
         * left movement direction.
         */
        LEFT,

        /**
         * right movement direction.
         */
        RIGHT,

        /**
         * no movement direction.
         */
        NONE
    }

    /**
     * Constructor for MovementUtil.
     *
     * @param newChecker the wall collision checker
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
    public MovementUtil(final WallCollision newChecker) {
        this.checker = newChecker;
    }

    /**
     * Finds the closest wall on a specific axis
     * (vertical or horizontal) from the enemy's position.
     *
     * @param enemyPosition the position of the enemy
     * @param isVerticalCheck true if checking for vertical 
     *     walls(enemy is moving vertically), false for horizontal walls
     * @return an Optional containing the closest wall position if found,
     *     or an empty Optional if no wall is found
     */
    private Optional<Position> findClosestWallOnAxis(
    final Position enemyPosition, final boolean isVerticalCheck) {
        if (enemyPosition == null) {
            return Optional.empty();
        }

        if (isVerticalCheck) { // Checking vertically, X is fixed, Y is moving
            return checker.closestWall(enemyPosition, 0, 1);
        } else { // Checking horizontally, Y is fixed, X is moving
            return checker.closestWall(enemyPosition, 1, 0);
        }

    }

    /**
     * Determines the initial general move direction
     * for an enemy based on its position and movement axis.
     *
     * @param enemyPosition the position of the enemy
     * @param doesEnemyGoVertically true if the enemy is moving
     *     vertically, false if horizontally
     * @return the initial general move direction for the enemy
     */
    public MoveDirection getInitialGeneralMoveDirection(
    final Position enemyPosition, final boolean doesEnemyGoVertically) {
        final ToIntFunction<Position> getCoordinate;
        final MoveDirection directionIfEnemyIsFurther;
        final MoveDirection directionIfEnemyIsCloser;

        if (doesEnemyGoVertically) {
            getCoordinate = Position::y;
            directionIfEnemyIsFurther = MoveDirection.DOWN;
            // changing the direction
            directionIfEnemyIsCloser = MoveDirection.UP;
        } else { // Horizontal movement
            getCoordinate = Position::x;
            directionIfEnemyIsFurther = MoveDirection.RIGHT;
            // changing the direction
            directionIfEnemyIsCloser = MoveDirection.LEFT;
        }

        final Optional<Position> closestWallOpt =
            findClosestWallOnAxis(enemyPosition, doesEnemyGoVertically);

        if (closestWallOpt.isPresent()) {
            final Position closestWall = closestWallOpt.get();

            final int enemyCoord = getCoordinate.applyAsInt(enemyPosition);
            final int wallCoord = getCoordinate.applyAsInt(closestWall);

            if (enemyCoord > wallCoord) {
                return directionIfEnemyIsFurther; // RIGHT or DOWN
            } else if (enemyCoord < wallCoord) {
                return directionIfEnemyIsCloser; // LEFT or UP
            } else {
                return MoveDirection.NONE;
            }
        } else {
            return MoveDirection.NONE;
        }
    }
}
