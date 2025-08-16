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
    private OverworldModel model;
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
    public Position getPosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPosition'");
    }

    @Override
    public EnemyType getState() {
        return this.currentState.getType();
    }


    // setters 

    @Override
    public void setPosition(Position newPosition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPosition'");
    }

    private void setModel(OverworldModel model){
        this.model = model;
    }

    @Override
    public void setState(GenericEnemyState newState) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setState'");
    }


    // methods

    @Override
    public void takeTurn(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeTurn'");
    }


    


    


    
    
    
}
