
package it.unibo.progetto_oop.combat.draw_helper;

import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * Helper class for drawing operations in the combat system.
 */
public class DrawHelper {
    /**
     * Check if two positions are neighbours within a certain distance.
     *
     * @param pos1 position of the first entity
     * @param pos2 position of the second entity
     * @param dist the distance to check
     * @return true if the positions are neighbours, false otherwise
     */
    public final boolean neighbours(
            final Position pos1, final Position pos2, final int dist) {
        return
        Math.abs(pos1.x() - pos2.x()) <= dist
        && Math.abs(pos1.y() - pos2.y()) <= dist;
    }

    /**
     * Check if two positions are diagonally adjacent within a certain distance.
     *
     * @param pos1 position of the first entity
     * @param pos2 position of the second entity
     * @param dist the distance to check
     * @return true if the positions are diagonally adjacent, false otherwise
     */
    public final boolean deathNeighbours(
        final Position pos1, final Position pos2, final int dist) {
        return
        Math.abs(pos1.x() - pos2.x()) == dist
        && Math.abs(pos1.y() - pos2.y()) == dist
        || Math.abs(pos1.x() - pos2.x()) == dist && pos1.y() == pos2.y()
        || pos1.x() == pos2.x() && Math.abs(pos1.y() - pos2.y()) == dist
        || pos1.x() == pos2.x() && pos2.y() == pos1.y();

    }
}
