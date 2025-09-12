package it.unibo.progetto_oop.combat.mvc_pattern;

import javax.swing.JPanel;

import it.unibo.progetto_oop.combat.combat_builder.RedrawContext;

/**
 * API interface for the Combat View in the MVC pattern.
 */
public interface CombatViewApi {
    /**
     * Gets the main panel of the combat view.
     *
     * @return the main JPanel of the combat view
     */
    JPanel getViewPanel();

    /**
     * Displays information text in the view.
     *
     * @param text the information text to display
     */
    void showInfo(String text);

    /**
     * Clears the information display area.
     */
    void clearInfo();

    /**
     * Sets the enabled state of a specific action button.
     *
     * @param ctx redraw context
     */
    void updateDisplay(RedrawContext ctx);

    /**
     * Sets the enabled state of all action buttons.
     */
    void setAllMenusEnabled();

    /**
     * Disables all action buttons.
     */
    void setAllMenusDisabled();

    /**
     * Sets maximum value for the player's health bar.
     *
     * @param max the maximum health value
     */
    void setPlayerHealthBarMax(int max);

    /**
     * Sets maximum value for the enemy's health bar.
     *
     * @param max the maximum health value
     */
    void setEnemyHealthBarMax(int max);

    /**
     * Updates the player's health display.
     *
     * @param hp the new health value
     */
    void updatePlayerHealth(int hp);

    /**
     * Updates the enemy's health display.
     *
     * @param hp the new health value
     */
    void updateEnemyHealth(int hp);

    /**
     * Shows main menu.
     */
    void showMainMenu();

    /**
     * Updates player stamina display.
     *
     * @param stamina the new stamina value
     */
    void updatePlayerStamina(int stamina);

    /**
     * Shows attack menu.
     */
    void showAttackMenu();

    /**
     * Sets which buttons are enabled or disabled.
     *
     * @param action  the action type
     * @param enabled true to enable, false to disable
     */
    void setActionEnabled(ActionType action, boolean enabled);
}
