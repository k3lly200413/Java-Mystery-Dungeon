
package it.unibo.progetto_oop.overworld.player.adapter_pattern;

/**
 * Interface for possible user actions and stats in the overworld.
 */

public interface PossibleUser {
    /**
     * Get the current HP of the player.
     *
     * @return the current HP of the player
     */
    int getHp();

    /**
     * Get the max HP of the player.
     *
     * @return the max HP of the player
     */
    int getMaxHp();

    /**
     * Get the power of the player.
     *
     * @return the power of the player
     */
    int getPower();

    /**
     * Get the stamina of the player.
     *
     * @return the stamina of the player
     */
    int getStamina();

    /**
     * Get the max stamina of the player.
     *
     * @return the max stamina of the player
     */
    int getMaxStamina();

    /**
     * Increase the player's actual health by the given amount.
     *
     * @param amount the amount to increase the player's health
     */
    void increasePlayerHealth(int amount);

    /**
     * Increase the player's power by the given amount.
     *
     * @param amount the amount to increase the player's power
     */
    void increasePlayerMaxPower(int amount);

    /**
     * Increase the player's stamina by the given amount.
     *
     * @param amount the amount to increase the player's stamina
     */
    void increasePlayerMaxStamina(int amount);

    /**
     * Increase the player's max health by the given amount.
     *
     * @param amount the amount to increase the player's max health
     */
    void increasePlayerMaxHealth(int amount);

    /**
     * Set the player's poisoned status.
     *
     * @param poisoned true if the player is poisoned, false otherwise
     */
    void setPlayerPoisoned(boolean poisoned);

}
