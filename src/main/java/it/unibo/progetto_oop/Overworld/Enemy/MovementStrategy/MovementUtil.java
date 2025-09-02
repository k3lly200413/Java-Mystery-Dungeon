package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy;

import java.util.Optional;
import java.util.function.ToIntFunction;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;

/**
 * This class is used by the follower and patroller enemies
 */
public class MovementUtil {
    public enum MoveDirection {
        UP, DOWN, LEFT, RIGHT, NONE
    }

    /**
    * Calculates the distance between two positions on a specific axis.
    * @param p1 the first position
    * @param p2 the second position
    * @param getCoordinate a function to extract the coordinate from a position (e.g., Position::getX or Position::getY)
    * @return the distance between the two positions on the specified axis
    */
    private int calculateDistanceOnAxis(Position p1, Position p2, ToIntFunction<Position> getCoordinate) {
        return Math.abs(getCoordinate.applyAsInt(p1) - getCoordinate.applyAsInt(p2));
    }

    /**
     * Finds the closest wall on a specific axis (vertical or horizontal) from the enemy's position.
     * @param enemyPosition the position of the enemy
     * @param isVerticalCheck true if checking for vertical walls(enemy is moving vertically), false for horizontal walls
     * 
     * @return an Optional containing the closest wall position if found, or an empty Optional if no wall is found
     */
    private Optional<Position> findClosestWallOnAxis(Position enemyPosition, boolean isVerticalCheck) {
        if (enemyPosition == null) {
            return Optional.empty();
        }

        if (isVerticalCheck) { // Checking vertically, X is fixed, Y is moving
            return WallCollision.closestWall(enemyPosition, 0, 1);
        } else { // Checking horizontally, Y is fixed, X is moving
            return WallCollision.closestWall(enemyPosition, 1, 0);
        }

    }

    public MoveDirection getInitialGeneralMoveDirection(Position enemyPosition, boolean doesEnemyGoVertically) {
        ToIntFunction<Position> getCoordinate;
        MoveDirection directionIfEnemyIsFurther;
        MoveDirection directionIfEnemyIsCloser;

        if (doesEnemyGoVertically) {
            getCoordinate = Position::y;
            directionIfEnemyIsFurther = MoveDirection.DOWN;
            directionIfEnemyIsCloser = MoveDirection.UP; // changing the direction 
        } else { // Horizontal movement
            getCoordinate = Position::x; 
            directionIfEnemyIsFurther = MoveDirection.RIGHT;
            directionIfEnemyIsCloser = MoveDirection.LEFT; // changing the direction
        }

        Optional<Position> closestWallOpt = findClosestWallOnAxis(enemyPosition, doesEnemyGoVertically);

        if (closestWallOpt.isPresent()) {
            Position closestWall = closestWallOpt.get();

            int enemyCoord = getCoordinate.applyAsInt(enemyPosition);
            int wallCoord = getCoordinate.applyAsInt(closestWall);

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
