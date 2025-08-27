package it.unibo.progetto_oop.Combat.Helper;

import it.unibo.progetto_oop.Combat.Position.Position;

public class Neighbours {

    /**
     * Constructor for neighbours class.
     * used to check if two enteties are close in combat
     *
     */
    public Neighbours() {

    }

    /**
     * Checks if two positions are within a given distance of each other.
     *
     * @param firstPosition   The first position.
     * @param secondPosition    The second position.
     * @param distanceBuffer The maximum distance to be considered neighbors.
     * @return True if they are neighbors, false otherwise.
     */
    public boolean neighbours(
        final Position firstPosition,
        final Position secondPosition,
        final int distanceBuffer) {
        return
            Math.abs(firstPosition.x() - secondPosition.x())
            <= distanceBuffer
            && Math.abs(firstPosition.y() - secondPosition.y())
            <= distanceBuffer;
    }

    /**
     * Method used to display death of a character.
     *
     * @param deathPosition   Center of dead player
     * @param positionToCheck    points we want to display
     * @param distanceBuffer distance from center of dead character
     * @return true if all checks are true, false otherwise
     *
     *
     *         eg.
     *         °°°
     *         °°° Normal character
     *         °°°
     *
     *         ° ° °
     *         ° ° ° Dead character
     *         ° ° °
     */

    public boolean deathNeighbours(
        final Position deathPosition,
        final Position positionToCheck,
        final int distanceBuffer) {
        return
        (Math.abs(deathPosition.x() - positionToCheck.x()) == distanceBuffer
        && Math.abs(deathPosition.y() - positionToCheck.y()) == distanceBuffer)
        || (Math.abs(deathPosition.x() - positionToCheck.x()) == distanceBuffer
        && deathPosition.y() == positionToCheck.y())
        || (deathPosition.x() == positionToCheck.x()
        && Math.abs(deathPosition.y() - positionToCheck.y()) == distanceBuffer)
        || (deathPosition.x() == positionToCheck.x()
        && positionToCheck.y() == deathPosition.y());
    }

}
