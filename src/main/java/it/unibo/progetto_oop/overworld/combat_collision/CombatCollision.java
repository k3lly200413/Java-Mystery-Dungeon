package it.unibo.progetto_oop.overworld.combat_collision;

import it.unibo.progetto_oop.overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.overworld.ViewManagerObserver.ViewManagerObserver;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.player.Player;

public interface CombatCollision {
    /**
     * check if the player is close enough to the enemy.
     * @param player the position of the player
     * @param enemy the position of the enemy
     * @return true if the player is close enough to the enemy, false otherwise
     */
    boolean checkCombatCollision(Position player, Position enemy);


    /**
     * transition to the combat view between the player and the enemy.
     * @param enemy the enemy that will enter combat
     * @param player the player that will enter combat
     */
    void showCombat(Enemy enemy, Player player);

    /**
     * set the inCombat flag.
     * @param inCombat true if the enemy is in combat, false otherwise
    */
    void setInCombat(boolean inCombat);

    /**
     * get the inCombat flag.
     * @param currentViewManagerObserver the current view manager observer
     */
    void setViewManagerListener(ViewManagerObserver currentViewManagerObserver);

    /**
     * show the overworld view.
     */
    void showOverworld();

}
