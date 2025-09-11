
package it.unibo.progetto_oop.combat.command_pattern;

import java.util.LinkedList;
import java.util.List;

import it.unibo.progetto_oop.combat.helper.Neighbours;
import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * Button for triggering a melee attack.
 */
public class MeleeButton implements GameButton {

    /**
     * The list of player positions.
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
     */
    private final int where;
    /**
     * Neighbours instance to check if two positions are neighbours.
     */
    private final Neighbours neighbours;

    /**
     * Constructor for MeleeButton.
     * Initializes the player and enemy positions, direction, and distance.
     *
     * @param playerPosition The initial position of the player.
     *
     * @param enemyPosition  The initial position of the enemy.
     *
     * @param direction      The direction in which the player will move.
     */
    public MeleeButton(
            final Position playerPosition,
            final Position enemyPosition,
            final int direction) {
        this.player = playerPosition;
        this.enemy = enemyPosition;
        this.where = direction;
        this.neighbours = new Neighbours();
    }

    @Override
    public final List<Position> execute() {
        if (neighbours.neighbours(
            new Position(
                this.player.x() + this.where, this.player.y()
            ),
                this.enemy, 1)
            ) {
            this.giocatori = this.moveEnemy();
            return this.giocatori;
        } else {
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
