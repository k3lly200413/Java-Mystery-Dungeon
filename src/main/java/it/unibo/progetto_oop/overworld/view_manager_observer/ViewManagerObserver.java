package it.unibo.progetto_oop.overworld.view_manager_observer;

import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
/**
 * Observer interface for the ViewManager to handle game events.
 */

public interface ViewManagerObserver {
    /**
     * Called when the player comes into contact with an enemy.
     *
     * @param encounteredEnemy the enemy encountered by the player
     */
    void onPlayerEnemyContact(Enemy encounteredEnemy);

    /**
     * Called when an enemy is defeated.
     */
    void onEnemyDefeat();

    /**
     * Called when the player is defeated.
     */
    void onPlayerDefeat();

    /**
     * Called when the Player beats the boss.
     */
    void onPlayerWin();
}
