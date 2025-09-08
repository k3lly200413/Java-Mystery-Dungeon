package it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl;

import it.unibo.progetto_oop.overworld.playGround.Data.Position;
import it.unibo.progetto_oop.overworld.enemy.EnemyType;
import it.unibo.progetto_oop.overworld.enemy.state_pattern.GenericEnemyState;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.player.Player;

public interface Enemy {
    /**
     * @return the current health of the enemy
     */
    int getCurrentHealth();

    /**
     * @return the maximum health of the enemy
     */
    int getMaxHealth();

    /**
     * @return the power of the enemy
     */
    int getPower();

    /**
     * @return the type(state) of the enemy
     */
    EnemyType getState();

    /**
     * @return the enemy's gridNotifier
     */
    GridNotifier getGridNotifier();

    /**
     * @param gridNotifier the enemy's gridNotifier
     */
    void setGridNotifier(GridNotifier gridNotifier);

    /**
     * Updates the enemy's state based on the player's position.
     * @param player The player that the enemy is interacting with.
     */
    void takeTurn(Player player);

    /**
     * @param newPosition the new position of the enemy
     */
    void setPosition(Position newPosition);

    /**
     * @return the currents position of the enemy
     */
    Position getCurrentPosition();

    /**
     * Set the state of the enemy to a new state.
     * @param newState the new state to set the enemy to
     */
    void setState(GenericEnemyState newState);

    /**
     * Method to notify the enemy that the player has moved.
     * @param player The player that has moved.
     */
    void playerMoved(Player player);

    /**
     * method to know if the enemy is a boss.
     * @return if the enemy is a boss or not
     */
    default boolean isBoss() {
        return false;
    }
}
