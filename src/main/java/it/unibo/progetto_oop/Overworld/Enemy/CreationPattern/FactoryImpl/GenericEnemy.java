package it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl;

import it.unibo.progetto_oop.Combat.Position.Position;
import it.unibo.progetto_oop.Overworld.Enemy.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.Player.Player;


public class GenericEnemy implements Enemy {
    private int maxHealth;
    private final int power;
    private Position initialPosition;
    private Position currentPosition;
    private int currentHealth;


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
    public Position getInitialPosition(){
        return this.initialPosition;
    }


    @Override
    public Position getPosition() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPosition'");
    }


    @Override
    public void setPosition(Position newPosition) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPosition'");
    }


    @Override
    public void takeTurn(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'takeTurn'");
    }


    @Override
    public EnemyType getEnemyType() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEnemyType'");
    }
    
    
}
