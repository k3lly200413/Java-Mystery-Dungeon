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

    private int playerHealth;

    private int enemyHealth;

    /** The attack power of the enemy. */
    private int enemyPower;

    /** The speed of the enemy. */
    private int enemySpeed;

    /** The name of the enemy. */
    private String enemyName;

    /** The maximum health points allowed for both player and enemy. */
    private int playerMaxHealth;

    private int enemyMaxHealth;

    /** @return the configured size of the combat area */
    public int getSize() {
        return size;
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

    public int getPlayerCurrentHp() {
        return playerHealth;
    }

    public int getEnemyCurrentHp() {
        return enemyHealth;
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

    /** @param newSize the size of the combat area
    *   @return the size of the combat area
    */
    public CombatBuilder setSize(final int newSize) {
        this.size = newSize;
        return this;
    }

    /** @param newStaminaMax the maximum stamina of the player
    *  @return the maximum stamina of the player
    */
    public CombatBuilder setStaminaMax(final int newStaminaMax) {
        this.staminaMax = newStaminaMax;
        return this;
    }

    /** @param newPlayerPower the base attack power of the player
    *  @return the base attack power of the player
    */
    public CombatBuilder setPlayerPower(final int newPlayerPower) {
        this.playerPower = newPlayerPower;
        return this;
    }

    /** @param newPlayerPoisonPower the poison attack power of the player
    *  @return the poison attack power of the player
    */
    public CombatBuilder setPlayerPoisonPower(final int newPlayerPoisonPower) {
        this.playerPoisonPower = newPlayerPoisonPower;
        return this;
    }

    /** @param newPlayerLongRangePower the long-range attack power of the player
    *  @return the long-range attack power of the player
    */
    public CombatBuilder setPlayerLongRangePower(
        final int newPlayerLongRangePower) {
        this.playerLongRangePower = newPlayerLongRangePower;
        return this;
    }

    public CombatBuilder setPlayerCurrentHealth(
        final int currentHealth) {
        this.playerHealth = currentHealth;
        return this;
    }

    public CombatBuilder setEnemyCurrentHealth(
        final int currentHealth) {
            this.enemyHealth = currentHealth;
            return this;
        }

    /** @param newEnemyPower the attack power of the enemy
    *  @return the attack power of the enemy
    */
    public CombatBuilder setEnemyPower(final int newEnemyPower) {
        this.enemyPower = newEnemyPower;
        return this;
    }

    /** @param newEnemySpeed the speed of the enemy
    *   @return the speed of the enemy
    */
    public CombatBuilder setEnemySpeed(final int newEnemySpeed) {
        this.enemySpeed = newEnemySpeed;
        return this;
    }

    /** @param newEnemyName the name of the enemy
    *  @return the name of the enemy
    */
    public CombatBuilder setEnemyName(final String newEnemyName) {
        this.enemyName = newEnemyName;
        return this;
    }

    /** @return the maximum health points */
    public int getPlayerMaxHealth() {
        return playerMaxHealth;
    }

    public int getEnemyMaxHealth() {
        return enemyMaxHealth;
    }

    /** @param newMaxHealth the maximum health points to set
     *  @return the maximum health points
     */
    public CombatBuilder setPlayerMaxHealth(final int newMaxHealth) {
        this.playerMaxHealth = newMaxHealth;
        return this;
    }

    public CombatBuilder setEnemyMaxHealth(final int newMaxHealth) {
        this.enemyMaxHealth = newMaxHealth;
        return this;
    }
}
