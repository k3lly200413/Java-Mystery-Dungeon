package it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl;

import it.unibo.progetto_oop.Combat.Position.Position;
import it.unibo.progetto_oop.Overworld.Enemy.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.GenericEnemyState;


public class GenericEnemy implements Enemy {
    private int maxHealth;
    private final int power;
    private Position initialPosition;
    private Position currentPosition;
    private int currentHealth;
    private OverworldModel model; // TODO: check if this is needed, maybe it can be removed
    private GenericEnemyState currentState;


    public GenericEnemy(int maxHealth, int currentHealth, int power, Position initialPosition) {
        this.maxHealth = maxHealth;
        this.power = power;
        this.initialPosition = initialPosition;
        this.currentHealth = currentHealth;
        this.currentPosition = this.initialPosition;
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


    // setters 

    @Override
    public void setPosition(Position newPosition) {
        this.currentPosition = newPosition;
    }

    private void setModel(OverworldModel model){
        this.model = model;
    }

    @Override
    public void setState(GenericEnemyState newState, OverworldModel model) {
        if (newState == null || newState == this.currentState) {
            return;
        }

        this.setModel(model);

        if (this.currentState != null) {
            this.currentState.exitState(this);
        }

        this.currentState = newState;

        this.currentState.enterState(this, this.model);
    }


    // methods

    @Override
    public void takeTurn(OverworldModel model, Player player) {
        this.currentState.update(this, model, player);
    }

    /**
     * @param player The player that has moved. 
     * @param model The current model of the overworld.
     * 
     *  Based on the type of enemy, it will act differently when the player moves.
     */
    public void playerMoved(Player player, OverworldModel model) {
        if (this.currentState != null){
            this.currentState.onPlayerMoved(this, player, model);
        }
    } 
}
