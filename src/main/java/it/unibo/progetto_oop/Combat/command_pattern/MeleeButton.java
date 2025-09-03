package it.unibo.progetto_oop.Combat.command_pattern;

import java.util.LinkedList;
import java.util.List;

import it.unibo.progetto_oop.Combat.Helper.Neighbours;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;

public class MeleeButton implements GameButton {

    private List<Position> giocatori = new LinkedList<>();
    private Position player;
    private Position enemy;
    private int where;
    private int distance;
    /**
     * Neighbours instance to check if two positions are neighbours.
     */
    private Neighbours neighbours;

    /**
     * Constructor for MeleeButton.
     * Initializes the player and enemy positions, direction, and distance.
     *
     * @param playerPosition The initial position of the player.
     * @param enemyPosition  The initial position of the enemy.
     * @param direction      The direction in which the player will move.
     * @param distanceBuffer       The distance to check for contact.
     */
    public MeleeButton(
            final Position playerPosition,
            final Position enemyPosition,
            final int direction,
            final int distanceBuffer) {
        this.player = playerPosition;
        this.enemy = enemyPosition;
        this.where = direction;
        this.distance = distanceBuffer;
        this.neighbours = new Neighbours();
    }

    @Override
    public final List<Position> execute() {
        // Check if the next step would result in contact
        if (neighbours.neighbours(
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
}
