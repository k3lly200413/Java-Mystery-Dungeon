package it.unibo.progetto_oop.Combat.CommandPattern;

import java.util.LinkedList;
import java.util.List;

import it.unibo.progetto_oop.Combat.Position.Position;

public class MeleeButton implements GameButton {

    /**
     * List of positions representing players in the combat.
     */
    private List<Position> giocatori = new LinkedList<>();
    /**
     * The position of the player.
     */
    private Position player;
    /**
     * The position of the enemy.
     */
    private Position enemy;
    /**
     * The direction in which the player will move.
     * It can be either 1 (right) or -1 (left).
     */
    private int where;
    /**
     * The distance to check for contact.
     * This is used to determine if the player can attack the enemy.
     */
    private int distance;

    /**
     * Constructor for MeleeButton.
     * Initializes the player and enemy positions, direction, and distance.
     *
     * @param playerPosition The initial position of the player.
     * @param enemyPosition  The initial position of the enemy.
     * @param direction      The direction in which the player will move.
     * @param distanceBuffer       The distance to check for contact.
     */
    public final void setAttributes(
            final Position playerPosition,
            final Position enemyPosition,
            final int direction,
            final int distanceBuffer) {
        this.player = playerPosition;
        this.enemy = enemyPosition;
        this.where = direction;
        this.distance = distanceBuffer;
    }

    @Override
    public final List<Position> execute() {
        // Check if the next step would result in contact
        if (this.neighbours(
            new Position(
                this.player.x() + this.where, this.player.y()
            ),
                this.enemy, 1)
            ) {
            // If so, execute the "push back" move
            this.giocatori = this.moveEnemy();
            return this.giocatori;
        } else {
            // Otherwise, just move the attacker forward
            this.player = new Position(
                this.player.x() + this.where, this.player.y());
        }

        this.giocatori.clear();
        this.giocatori.add(this.player);
        this.giocatori.add(this.enemy);
        return this.giocatori;
    }

    /**
     * A special move where both the attacker and target are moved in the same
     * direction.
     * Simulates the attacker "pushing" the target back.
     *
     * @return A list containing the new positions.
     */
    public List<Position> moveEnemy() {
        this.enemy = new Position(this.enemy.x() + this.where, this.enemy.y());
        this.player =
            new Position(this.player.x() + this.where, this.player.y());

        this.giocatori.clear();
        this.giocatori.add(this.player);
        this.giocatori.add(this.enemy);

        return this.giocatori;
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
     *
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
