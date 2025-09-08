package it.unibo.progetto_oop.Overworld.enemy.state_pattern;

import it.unibo.progetto_oop.Overworld.enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.Overworld.player.Player;

public interface GenericEnemyState {
    /**
     * Enter the state of the enemy.
     *
     * @param context the enemy that is entering the state
     */
    void enterState(Enemy context);

    /**
     * Exit the state of the enemy.
     *
     * @param context the enemy that is exiting the state
     */
    void exitState(Enemy context);

    /**
     * Update the state of the enemy based on the player's position.
     * this method will be called every turn of the enemy
     * @param context the enemy that is updating its state
     * @param player the player that the enemy is interacting with
     */
    void update(Enemy context,  Player player);

    /**
     * The specific action that a tipe of enemy
     * should take when the player moves.
     *
     * @param context the enemy that is taking the action
     * @param player the player that the enemy is interacting with
     */
    void onPlayerMoved(Enemy context, Player player);

    /**
     * @return the type of the enemy state
     */
    EnemyType getType();
}

