package it.unibo.progetto_oop.Combat.CommandPattern;

import java.util.LinkedList;
import java.util.List;

import it.unibo.progetto_oop.Combat.Position.Position;

public class MeleeButton implements GameButton{

    private List<Position> giocatori = new LinkedList<>();
    private Position player;
    private Position enemy;
    private int where;
    private int distance;

    public void setAttributes(Position player, Position enemy, int where, int distance) {
        this.player = player;
        this.enemy = enemy;
        this.where = where;
        this.distance = distance;
    }

    @Override
    public List<Position> execute() {
        // Check if the next step would result in contact
        if (this.neighbours(new Position(this.player.x() + this.where, this.player.y()), this.enemy, 1)) {
            // If so, execute the "push back" move
            this.giocatori = this.moveEnemy();
            return this.giocatori;
        } else {
            // Otherwise, just move the attacker forward
            this.player = new Position(this.player.x() + this.where, this.player.y());
        }

        this.giocatori.clear();
        this.giocatori.add(this.player);
        this.giocatori.add(this.enemy);
        return this.giocatori;
    }

    /**
     * A special move where both the attacker and target are moved in the same direction.
     * Simulates the attacker "pushing" the target back.
     * @return A list containing the new positions.
    */
    public List<Position> moveEnemy() {
        this.enemy = new Position(this.enemy.x() + this.where, this.enemy.y());
        this.player = new Position(this.player.x() + this.where, this.player.y());
        
        this.giocatori.clear();
        this.giocatori.add(this.player);
        this.giocatori.add(this.enemy);
        
        return this.giocatori;
    }

    /**
     * Checks if two positions are within a given distance of each other.
     * @param player The first position.
     * @param other The second position.
     * @param distance The maximum distance to be considered neighbors.
     * @return True if they are neighbors, false otherwise.
    */
    public boolean neighbours(Position player, Position other, int distance) {
        return Math.abs(player.x() - other.x()) <= distance && Math.abs(player.y() - other.y()) <= distance;
    }
    
}
