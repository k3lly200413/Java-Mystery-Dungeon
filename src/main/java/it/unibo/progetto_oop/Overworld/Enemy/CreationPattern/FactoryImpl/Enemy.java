package it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl;

import java.util.Set;

import it.unibo.progetto_oop.Overworld.Enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.GenericEnemyState;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.Player.Player;
<<<<<<< HEAD
=======
import it.unibo.progetto_oop.combat.position.Position;
>>>>>>> 193bbdc31a0a30b1ddfa2952e5f3c0e623bcbbaa

public interface Enemy {
    /** 
     * @return the current health of the enemy
     */
    public int getCurrentHealth();
    
    /**
     * @return the maximum health of the enemy
     */
    public int getMaxHealth();

    /**
     * @return the power of the enemy
     */
    public int getPower();

    /** 
     * @return the type(state) of the enemy
     */
    public EnemyType getState();

    /**
     * 
     * @return the walls of the floor this enemy is moving on
     */
    public Set<Position> getWalls();
    
    /**
     * 
     * Updates the enemy's state based on the player's position.
     * @param player The player that the enemy is interacting with.
     */
    public void takeTurn(Player player);
    
    /**
     * @param newPosition the new position of the enemy
     */
    public void setPosition(Position newPosition);

    /**
     * @return the currents position of the enemy
     */
    public Position getCurrentPosition();
    
    /**
     * Set the state of the enemy to a new state
     * @param newState the new state to set the enemy to
     */
    public void setState(GenericEnemyState newState);
}
