package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import java.util.*;
import java.util.stream.IntStream;


/** 
 * visual range related utilities
 */
public class VisibilityUtil {

    /**
     * check if the player is in the enemy's line of sight
     * @param enemy enemy's position
     * @param player player's position
     * @param neighbourDistance the distance considered as "neighbour" for the enemy
     * @return
     */
    public boolean inLos(Position enemy, Position player, int neighbourDistance){
        if(this.neighbours(enemy, player, neighbourDistance) && this.hasLineOfSight(enemy, player)){
            return true;
        }
        return false;
    }

    /**
     * Check if the enemy has line of sight to the player
     * @param startPos the enemy's position
     * @param endPos the player's position
     * @return true if there is a clear line of sight, false otherwise
     */
    private boolean hasLineOfSight(Position startPos, Position endPos) {
        if (startPos == null || endPos == null) {
            return false;
        }
        if (startPos.equals(endPos)){
            return true;
        }

        // Bresenham's line algorithm to find the cells between startPos and endPos
        List<Position> lineCells = bresenhamLine(startPos, endPos);

        // close enough
        if (lineCells.size()<=2) {
            return true;
        }

        // check if any of the cells in the line (except the first and last) are walls
        boolean collisionDetected = IntStream.range(1, lineCells.size()-1) 
                                            .mapToObj(lineCells::get)
                                            .anyMatch(p -> WallCollision.inBounds(p));

        return !collisionDetected;
    }

    /**
     * Bresenhamm's line algorithm
     * @param enemy the enemy's position
     * @param player the player's position
     * @return a list of positions representing the line between the enemy and the player
     */
    private List<Position> bresenhamLine(Position enemy, Position player){
        
        List<Position> line = new ArrayList<>();
        // enemy's position
        int x0 = enemy.x();
        int x1 = player.x();

        // player's position
        int y0 = enemy.y();
        int y1 = player.y();

        // distance between the two points
        int dx = Math.abs(x1-x0);
        int dy = Math.abs(y1-y0);
 
        // determine the step direction for x and y
        int sx = x0<x1 ? 1 : -1; 
        int sy = y0<y1 ? 1 : -1; 
 
        int err = dx-dy; // decision variable
        int e2;
 
        while (true) 
        {
            line.add(new Position(x0, y0)); // current pixel
 
            if (x0 == x1 && y0 == y1) // reached the end point
                break;
 
            e2 = 2 * err; // temp error
            if (e2>-dy) // adjust x coordinate
            {
                err = err-dy;
                x0 = x0+sx; // move in x direction
            }
 
            if (e2<dx) // adjust y coordinate
            {
                err = err+dx;
                y0 = y0+sy; // move in y direction
            }
        }                                
        return line;
    }

    /**
     * Check if the enemy and player are within a certain distance(neighbors)
     * @param enemy enemy's position
     * @param player player's position
     * @param distance distance to consider as "neighbour"
     * @return true if they are neighbours, false otherwise
     */
    public boolean neighbours(Position enemy, Position player, int distance){
        return Math.abs(enemy.x()-player.x())<distance && Math.abs(enemy.y()-player.y())<distance;
    }

    /**
     * Get the first move position towards the player
     * @param enemy enemy's position
     * @param player player's position
     * @return the position to move to
     */
    public Position firstMove(Position enemy, Position player){

        int nextX = enemy.x();
        int nextY = enemy.y();

        if (enemy.x()<player.x()) {
            nextX++;
        }
        else if (enemy.x()>player.x()) {
            nextX--;
        }
        else if (enemy.y()<player.y()) {
            nextY++;
        }
        else if (enemy.y()> player.y()) {
            nextY--;
        }
        return new Position(nextX, nextY);

    }


}
