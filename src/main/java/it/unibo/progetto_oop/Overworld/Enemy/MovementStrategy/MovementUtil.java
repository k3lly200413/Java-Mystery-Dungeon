package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy;

import java.util.Optional;
import java.util.Set;
import java.util.function.ToIntFunction;
import it.unibo.progetto_oop.Combat.Position.Position;

public class MovementUtil {
    public enum MoveDirection {
        UP, DOWN, LEFT, RIGHT, NONE
    }

    // TODO: Function to calculate the next move direction based on the current position, target position, wall position
    // -> Function to calculate closest wall
    // --> Function to calculate distance: DONE

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
     * @param wallList the set of wall positions
     * @param isVerticalCheck true if checking for vertical walls(enemy is moving vertically), false for horizontal walls
     * 
     * @return an Optional containing the closest wall position if found, or an empty Optional if no wall is found
     */
    private Optional<Position> findClosestWallOnAxis(Position enemyPosition, Set<Position> wallList, boolean isVerticalCheck) {
        // TODO: based on the axis the enemy is moving on, we check wich wall is closer
        return Optional.empty();
    }

    public MoveDirection getInitialGeneralMoveDirection(Position enemyPosition, Set<Position> wallList, boolean doesEnemyGoVertically) {
        // TODO: determine the initial move direction based on the enemy's position, wall positions, and whether the enemy moves vertically or horizontally
        return null;
    }
}
