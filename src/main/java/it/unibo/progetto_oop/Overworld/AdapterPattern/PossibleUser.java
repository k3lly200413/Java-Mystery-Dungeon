package it.unibo.progetto_oop.Overworld.AdapterPattern;

public interface PossibleUser {
    /**
     * Get the current HP of the player.
     * @return the current HP of the player
     */
    int getHp();

    /**
     * Get the max HP of the player.
     * @return the max HP of the player
     */
    int getMaxHP();

    /**
     * Increase the player's actual health by the given amount.
     * @param amount the amount to increase the player's health
     */
    void increasePlayerHealth(int amount);

    /**
     * Increase the player's power by the given amount.
     * @param amount the amount to increase the player's power
     */
    void increasePlayerPower(int amount);

    /**
     * Set the player's poisoned status.
     * @param poisoned true if the player is poisoned, false otherwise
     */
    void setPlayerPoisoned(boolean poisoned);
}
