package it.unibo.progetto_oop.combat.mvc_pattern;

import it.unibo.progetto_oop.combat.combat_builder.RedrawContext;

/**
 * Interface for the Combat View in the MVC pattern.
 */
public interface CombatViewInterface {

    /**
     * Updates the player's health display.
     *
     * @param value the new health value
     */
    void updatePlayerHealth(int value);

    /**
     * Updates the player's stamina display.
     *
     * @param value the new stamina value
     */
    void updatePlayerStamina(int value);

    /**
     * Updates the enemy's health display.
     *
     * @param value the new health value
     */
    void updateEnemyHealth(int value);

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
     * Sets the controller for this view.
     *
     * @param combatController the combat controller to set
     */
    void setController(CombatController combatController);

    /**
     * Shows the game over screen with an option to restart.
     *
     * @param onRestart the action to perform on restart press
     */
    void showGameOver(Runnable onRestart);

    /**
     * Initializes the view components.
     */
    void init();

    /**
     * Sets the maximum value for the player's health bar.
     *
     * @param max the maximum health value
     */
    void setPlayerHealthBarMax(int max);

    /**
     * Sets the maximum value for the enemy's health bar.
     *
     * @param max the maximum health value
     */
    void setEnemyHealthBarMax(int max);

    /**
     * Sets the maximum value for the player's stamina bar.
     *
     * @param max the maximum stamina value
     */
    void setPlayerMaxStaminaBar(int max);

    /**
     * Displays the combat view.
     */
    void display();

    /**
     * Enables or disables a specific action button.
     *
     * @param action the action type to modify
     * @param isEnabled true to enable the button, false to disable it
     */
    void setActionEnabled(ActionType action, boolean isEnabled);

     /**
     * Display the main set of actions (Attack, Bag, Run).
     */
    void showMainMenu();

    /**
     * Display the options for a physical attack.
     */
    void showAttackMenu();

    /**
     * Display the contents of the bag/inventory.
     */
    void showBagMenu();

    /**
     * Redraws the grid based on the provided context.
     *
     * @param context the context containing information for redrawing
     */
    void redrawGrid(RedrawContext context);

    /**
     * Returns instance of CombatView.
     *
     * @return the CombatView instance
     */
    CombatView getViewPanel();

    /**
     * Disables all Menus so that player cannot interact with them.
     */
    void setAllMenusDisabled();

    /**
     * Enables all Menus so that player can interact with them.
     */
    void setAllMenusEnabled();
}
