
package it.unibo.progetto_oop.overworld.combat_collision;

import it.unibo.progetto_oop.overworld.ViewManagerObserver;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * Interface for handling combat collision
 * logic between player and enemy
 * in the overworld.
 */
public interface CombatCollision {

    /**
     * Check if the player is close enough to the enemy.
     *
     * @param player the position of the player
     * @param enemy the position of the enemy
     * @return true if the player is close enough to the enemy, false otherwise
     */
    boolean checkCombatCollision(Position player, Position enemy);

    /**
     * Transition to the combat view between the player and the enemy.
     *
     * @param enemy the enemy that will enter combat
     * @param player the player that will enter combat
     */
    void showCombat(Enemy enemy, Player player);

    /**
     * Set the inCombat flag.
     *
     * @param inCombat true if the enemy is in combat, false otherwise
     */
    void setInCombat(boolean inCombat);

    /**
     * Set the view manager observer for combat transitions.
     *
     * @param currentViewManagerObserver the current view manager observer
     */
    void setViewManagerListener(ViewManagerObserver currentViewManagerObserver);

    /**
     * Show the overworld view.
     */
    void showOverworld();

}
