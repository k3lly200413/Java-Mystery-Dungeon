package it.unibo.progetto_oop.combat.mvc_pattern;

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
}
