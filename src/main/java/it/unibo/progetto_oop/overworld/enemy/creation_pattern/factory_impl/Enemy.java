package it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl;

import it.unibo.progetto_oop.overworld.enemy.state_pattern.GenericEnemyState;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * Interface representing an enemy in the overworld.
 */
public interface Enemy {
    /**
     * Get the current health of the enemy.
     *
     * @return the current health of the enemy
     */
    int getCurrentHp();

    /**
     * Get the maximum health of the enemy.
     *
     * @return the maximum health of the enemy
     */
    int getMaxHp();

    /**
     * Get the power of the enemy.
     *
     * @return the power of the enemy
     */
    int getPower();

    /**
     * Get the type (state) of the enemy.
     *
     * @return the type(state) of the enemy
     */
    GenericEnemyState getState();

    /**
     * Get the enemy's gridNotifier.
     *
     * @return the enemy's gridNotifier
     */
    GridNotifier getGridNotifier();

    /**
     * Set the enemy's gridNotifier.
     *
     * @param gridNotifier the enemy's gridNotifier
     */
    void setGridNotifier(GridNotifier gridNotifier);

    /**
     * Updates the enemy's state based on the player's position.
     *
     * @param player The player that the enemy is interacting with.
     */
    void takeTurn(Player player);

    /**
     * Set the new position of the enemy.
     *
     * @param newPosition the new position of the enemy
     */
    void setPosition(Position newPosition);

    /**
     * Get the current position of the enemy.
     *
     * @return the current position of the enemy
     */
    Position getCurrentPosition();

    /**
     * Set the state of the enemy to a new state.
     *
     * @param newState the new state to set the enemy to
     */
    void setState(GenericEnemyState newState);

    /**
     * Set the current health of the enemy.
     *
     * @param health the new current health of the enemy
     */
    void setHp(int health);

    /**
     * Method to know if the enemy is a boss.
     *
     * @return if the enemy is a boss or not
     */
    default boolean isBoss() {
        return false;
    }
}
