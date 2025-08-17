package it.unibo.progetto_oop.Overworld.Enemy;

import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.GenericEnemyState;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Combat.Position.Position;

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
     * Updates the enemy's state based on the player's position.
     * @param model The current model of the overworld.
     * @param player The player that the enemy is interacting with.
     */
    public void takeTurn(OverworldModel model, Player player);
    
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
     * @param model OverworldModel instance to update the enemy's state
     */
    public void setState(GenericEnemyState newState, OverworldModel model);
}
