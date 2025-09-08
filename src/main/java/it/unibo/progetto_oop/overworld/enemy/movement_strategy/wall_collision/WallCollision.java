package it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision;

import java.util.Optional;

import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.StructureData;

public interface WallCollision {

    /**
     * set the grid view.
     * @param newGridView the new grid view
     */
    void setGrid(StructureData newGridView);

    /**
     * set the entity grid view.
     * @param newEntityGridView the new entity grid view
     */
    void setEntityGrid(StructureData newEntityGridView);


    /**
     * check if a position is in bounds.
     * @param p the position to check
     * @return true if the position is in bounds, false otherwise
     */
    boolean inBounds(Position p);

    /**
     * check if a position can be entered by the player.
     * @param to the position to check
     * @return true if the position can be entered, false otherwise
     */
    boolean canEnter(Position to);

    /**
     * check if an enemy can enter a specific position.
     * @param to the position to check
     * @return true if the enemy can enter the position, false otherwise
     */
    boolean canEnemyEnter(Position to);

    /**
     * Find the closest wall in the direction of movement.
     * @param from the starting position
     * @param dx the change in x direction
     * @param dy the change in y direction
     * @return the position of the closest wall, if it exists
     */
    Optional<Position> closestWall(Position from, int dx, int dy);

}
