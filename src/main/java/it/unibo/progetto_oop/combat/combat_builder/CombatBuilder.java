package it.unibo.progetto_oop.combat.combat_builder;

import it.unibo.progetto_oop.combat.mvc_pattern.CombatModel;

/**
 * Builder class for constructing {@link CombatModel} instances.
 * Provides a fluent API to configure all parameters before creation.
 */
public class CombatBuilder {

    /** The size of the game board or arena. */
    private int size;

    /** The maximum stamina points of the player. */
    private int staminaMax;

    /** The current attack power of the player. */
    private int playerPower;

    /** The poison attack power of the player. */
    private int playerPoisonPower;

    /** The long-range attack power of the player. */
    private int playerLongRangePower;

    /** The current health points of the player. */
    private int playerHealth;

    /** The current health points of the enemy. */
    private int enemyHealth;

    /** The speed of the enemy. */
    private int enemySpeed;

    /** The name of the enemy. */
    private String enemyName;

    /** The maximum health points allowed for the player. */
    private int playerMaxHealth;

    /** The maximum health points allowed for the enemy. */
    private int enemyMaxHealth;

    /**
     * Returns the configured size of the combat area.
     *
     * @return the size of the combat area
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Returns the maximum stamina of the player.
     *
     * @return the maximum stamina
     */
    public int getStaminaMax() {
        return this.staminaMax;
    }

    /**
     * Returns the base attack power of the player.
     *
     * @return the player's base power
     */
    public int getPlayerPower() {
        return this.playerPower;
    }

    /**
     * Returns the poison attack power of the player.
     *
     * @return the player's poison power
     */
    public int getPlayerPoisonPower() {
        return this.playerPoisonPower;
    }

    /**
     * Returns the long-range attack power of the player.
     *
     * @return the player's long-range power
     */
    public int getPlayerLongRangePower() {
        return this.playerLongRangePower;
    }

    /**
     * Returns the current health points of the player.
     *
     * @return the player's current HP
     */
    public int getPlayerCurrentHp() {
        return this.playerHealth;
    }

    /**
     * Returns the current health points of the enemy.
     *
     * @return the enemy's current HP
     */
    public int getEnemyCurrentHp() {
        return this.enemyHealth;
    }

    /**
     * Returns the speed of the enemy.
     *
     * @return the enemy's speed
     */
    public int getEnemySpeed() {
        return this.enemySpeed;
    }

    /**
     * Returns the name of the enemy.
     *
     * @return the enemy's name
     */
    public String getEnemyName() {
        return this.enemyName;
    }

    /**
     * Builds a new {@link CombatModel} using the configured parameters.
     *
     * @return a new instance of {@link CombatModel}
     */
    public CombatModel build() {
        return new CombatModel(this);
    }

    /**
     * Sets the size of the combat area.
     *
     * @param newSize the size to set
     * @return this builder for chaining
     */
    public CombatBuilder setSize(final int newSize) {
        this.size = newSize;
        return this;
    }

    /**
     * Sets the maximum stamina of the player.
     *
     * @param newStaminaMax the maximum stamina to set
     * @return this builder for chaining
     */
    public CombatBuilder setStaminaMax(final int newStaminaMax) {
        this.staminaMax = newStaminaMax;
        return this;
    }

    /**
     * Sets the base attack power of the player.
     *
     * @param newPlayerPower the base power to set
     * @return this builder for chaining
     */
    public CombatBuilder setPlayerPower(final int newPlayerPower) {
        this.playerPower = newPlayerPower;
        return this;
    }

    /**
     * Sets the poison attack power of the player.
     *
     * @param newPlayerPoisonPower the poison power to set
     * @return this builder for chaining
     */
    public CombatBuilder setPlayerPoisonPower(final int newPlayerPoisonPower) {
        this.playerPoisonPower = newPlayerPoisonPower;
        return this;
    }

    /**
     * Sets the long-range attack power of the player.
     *
     * @param newPlayerLongRangePower the long-range power to set
     * @return this builder for chaining
     */
    public CombatBuilder setPlayerLongRangePower(
        final int newPlayerLongRangePower) {
        this.playerLongRangePower = newPlayerLongRangePower;
        return this;
    }

    /**
     * Sets the current health points of the player.
     *
     * @param currentHealth the current HP to set
     * @return this builder for chaining
     */
    public CombatBuilder setPlayerCurrentHealth(final int currentHealth) {
        this.playerHealth = currentHealth;
        return this;
    }

    /**
     * Sets the current health points of the enemy.
     *
     * @param currentHealth the current HP to set
     * @return this builder for chaining
     */
    public CombatBuilder setEnemyCurrentHealth(final int currentHealth) {
        this.enemyHealth = currentHealth;
        return this;
    }

    /**
     * Sets the speed of the enemy.
     *
     * @param newEnemySpeed the speed to set
     * @return this builder for chaining
     */
    public CombatBuilder setEnemySpeed(final int newEnemySpeed) {
        this.enemySpeed = newEnemySpeed;
        return this;
    }

    /**
     * Sets the name of the enemy.
     *
     * @param newEnemyName the name to set
     * @return this builder for chaining
     */
    public CombatBuilder setEnemyName(final String newEnemyName) {
        this.enemyName = newEnemyName;
        return this;
    }

    /**
     * Returns the maximum health points of the player.
     *
     * @return the player's maximum HP
     */
    public int getPlayerMaxHealth() {
        return this.playerMaxHealth;
    }

    /**
     * Returns the maximum health points of the enemy.
     *
     * @return the enemy's maximum HP
     */
    public int getEnemyMaxHealth() {
        return this.enemyMaxHealth;
    }

    /**
     * Sets the maximum health points of the player.
     *
     * @param newMaxHealth the maximum HP to set
     * @return this builder for chaining
     */
    public CombatBuilder setPlayerMaxHealth(final int newMaxHealth) {
        this.playerMaxHealth = newMaxHealth;
        return this;
    }

    /**
     * Sets the maximum health points of the enemy.
     *
     * @param newMaxHealth the maximum HP to set
     * @return this builder for chaining
     */
    public CombatBuilder setEnemyMaxHealth(final int newMaxHealth) {
        this.enemyMaxHealth = newMaxHealth;
        return this;
    }
}
