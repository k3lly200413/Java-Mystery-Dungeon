package it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl;

import java.util.Set;

import it.unibo.progetto_oop.Overworld.Enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.combat.position.Position;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.GenericEnemyState;
import it.unibo.progetto_oop.Overworld.GridNotifier.GridNotifier;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;


public class GenericEnemy implements Enemy {
    private int maxHealth;
    private final int power;
    private Position initialPosition;
    private Position currentPosition;
    private int currentHealth;
    private GenericEnemyState currentState;

    private Set<Position> walls; 
    private GridNotifier gridNotifier;


    public GenericEnemy(int maxHealth, int currentHealth, int power, Position initialPosition, Set<Position> walls,  GridNotifier gridNotifier) {
        this.maxHealth = maxHealth;
        this.power = power;
        this.initialPosition = initialPosition;
        this.currentHealth = currentHealth;
        this.currentPosition = this.initialPosition;

        this.walls = walls;
        this.gridNotifier = gridNotifier;
    }

    
    // getters
    @Override
    public int getCurrentHealth() {
        return this.currentHealth;
    }

    @Override
    public int getMaxHealth() {
        return this.maxHealth;
    }

    @Override
    public int getPower() {
        return this.power;
    }

    @Override
    public Position getCurrentPosition() {
        return this.currentPosition;
    }

    @Override
    public EnemyType getState() {
        return this.currentState.getType();
    }

    @Override
    public Set<Position> getWalls() {
        return this.walls;
    }

    @Override
    public GridNotifier getGridNotifier() {
        return this.gridNotifier;
    }


    // setters 

    @Override
    public void setPosition(Position newPosition) {
        this.currentPosition = newPosition;
    }

    @Override
    public void setState(GenericEnemyState newState) {
        if (newState == null || newState == this.currentState) {
            return;
        }

        if (this.currentState != null) {
            this.currentState.exitState(this);
        }

        this.currentState = newState;

        this.currentState.enterState(this);
    }

    @Override
    public void setGridNotifier(GridNotifier gridNotifier) {
        this.gridNotifier = gridNotifier;
    }


    // methods

    @Override
    public void takeTurn(Player player) {
        this.currentState.update(this, player);
    }

    /**
     * @param player The player that has moved. 
     * @param model The current model of the overworld.
     * 
     *  Based on the type of enemy, it will act differently when the player moves.
     */
    public void playerMoved(Player player) {
        if (this.currentState != null){
            this.currentState.onPlayerMoved(this, player);
        }
    } 
}
