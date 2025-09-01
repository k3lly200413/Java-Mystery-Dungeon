package it.unibo.progetto_oop.Combat.combat_builder;

import it.unibo.progetto_oop.combat.mvc_pattern.CombatModel;

/**
 * Builder class for constructing {@link CombatModel} instances.
 * Provides a fluent API to configure all parameters before creation.
 */
public class CombatBuilder {

    private int newSize;
    private int staminaMax;
    private int playerPower;
    private int playerPoisonPower;
    private int playerLongRangePower;
    private int enemyPower;
    private int enemySpeed;
    private String enemyName;

    /** @return the configured size of the combat area */
    public int getSize() {
        return newSize;
    }

    /** @return the maximum stamina of the player */
    public int getStaminaMax() {
        return staminaMax;
    }

    /** @return the base attack power of the player */
    public int getPlayerPower() {
        return playerPower;
    }

    /** @return the poison attack power of the player */
    public int getPlayerPoisonPower() {
        return playerPoisonPower;
    }

    /** @return the long-range attack power of the player */
    public int getPlayerLongRangePower() {
        return playerLongRangePower;
    }

    /** @return the attack power of the enemy */
    public int getEnemyPower() {
        return enemyPower;
    }

    /** @return the speed of the enemy */
    public int getEnemySpeed() {
        return enemySpeed;
    }

    /** @return the name of the enemy */
    public String getEnemyName() {
        return enemyName;
    }

    /**
     * Builds a new {@link CombatModel} using the configured parameters.
     *
     * @return a new instance of CombatModel
     */
    public CombatModel build() {
        return new CombatModel(this);
    }

    /** @param size the size of the combat area
    *   @return newSize
    */
    public CombatBuilder setSize(final int size) {
        this.newSize = size;
        return this;
    }

    /** @param staminaMax the maximum stamina of the player */
    public CombatBuilder setStaminaMax(final int staminaMax) {
        this.staminaMax = staminaMax;
        return this;
    }

    /** @param playerPower the base attack power of the player */
    public CombatBuilder setPlayerPower(final int playerPower) {
        this.playerPower = playerPower;
        return this;
    }

    /** @param playerPoisonPower the poison attack power of the player */
    public CombatBuilder setPlayerPoisonPower(final int playerPoisonPower) {
        this.playerPoisonPower = playerPoisonPower;
        return this;
    }

    /** @param playerLongRangePower the long-range attack power of the player */
    public CombatBuilder setPlayerLongRangePower(final int playerLongRangePower) {
        this.playerLongRangePower = playerLongRangePower;
        return this;
    }

    /** @param enemyPower the attack power of the enemy */
    public CombatBuilder setEnemyPower(final int enemyPower) {
        this.enemyPower = enemyPower;
        return this;
    }

    /** @param enemySpeed the speed of the enemy
     * @return this
    */
    public CombatBuilder setEnemySpeed(final int enemySpeed) {
        this.enemySpeed = enemySpeed;
        return this;
    }

    /** @param enemyName the name of the enemy */
    public CombatBuilder setEnemyName(final String enemyName) {
        this.enemyName = enemyName;
        return this;
    }
}
