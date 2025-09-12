package it.unibo.progetto_oop.combat.mvc_pattern;

import javax.swing.JPanel;

import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;

/**
 * Narrow API that exposes only the operations
 * ViewManager needs from the controller.
 */
public interface CombatControllerApi {
    /**
     * Set the enemy that has been encountered.
     *
     * @param enemy the enemy to set
     */
    void setEncounteredEnemy(Enemy enemy);

    /**
     * Set enemy HP.
     *
     * @param currentHp the current HP of the enemy
     * @param maxHp     the maximum HP of the enemy
     */
    void setEnemyHp(int currentHp, int maxHp);

    /**
     * Reset the controller for a new combat.
     */
    void resetForNewCombat();

    /**
     * Redraw the view to reflect the current state.
     */
    void redrawView();

    /**
     * Get the view panel for the combat.
     *
     * @return the view panel
     */
    JPanel getViewPanel();

}
