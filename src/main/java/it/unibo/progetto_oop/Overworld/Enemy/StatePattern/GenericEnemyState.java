package it.unibo.progetto_oop.Overworld.Enemy.StatePattern;

import java.util.Set;

import it.unibo.progetto_oop.Combat.Position.Position;
import it.unibo.progetto_oop.Overworld.Enemy.*;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Player.Player;

public interface GenericEnemyState {
    /**
     * Enter the state of the enemy.
     * @param context the enemy that is entering the state
     * @param model OverworldModel instance to update the enemy's state
     */
    public void enterState(Enemy context, Set<Position> walls);
    /**
     * Exit the state of the enemy.
     * @param context the enemy that is exiting the state
     */
    public void exitState(Enemy context);

    /**
     * Update the state of the enemy based on the player's position.
     * this method will be called every turn of the enemy
     * @param enemy the enemy that is updating its state
     * @param model OverworldModel instance to update the enemy's state
     * @param player the player that the enemy is interacting with
     */
    public void update(Enemy enemy, Set<Position> walls, Player player);
    
    /**
     * The specific action that a tipe of enemy should take when the player moves.
     * @param context the enemy that is taking the action
     * @param player the player that the enemy is interacting with
     * @param model  OverworldModel instance to update the enemy's state
     */
    public void onPlayerMoved(Enemy context, Player player);
    
    /**
     * 
     * @return the type of the enemy state
     */
    public EnemyType getType();
}

