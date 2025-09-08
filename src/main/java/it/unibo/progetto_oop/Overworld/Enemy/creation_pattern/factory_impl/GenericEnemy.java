package it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl;

import java.util.Objects;

import it.unibo.progetto_oop.overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.overworld.enemy.EnemyType;
import it.unibo.progetto_oop.overworld.enemy.state_pattern.GenericEnemyState;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.player.Player;


public class GenericEnemy implements Enemy {
    /**
     * maxHealth of the enemy.
     */
    private int maxHealth;

    /**
     * power of the enemy.
     */
    private final int power;

    /**
     * initial position of the enemy.
     */
    private Position initialPosition;

    /**
     * current position of the enemy.
     */
    private Position currentPosition;

    /**
     * current health of the enemy.
     */
    private int currentHealth;

    /**
     * current state of the enemy.
     */
    private GenericEnemyState currentState;

    /**
     * grid notifier to notify the grid of changes.
     */
    private GridNotifier gridNotifier;

    /**
     * Constructor of the BossEnemy class.
     * @param newMaxHealth max health
     * @param newCurrentHealth current health
     * @param newPower power
     * @param newInitialPosition initial position
     * @param newGridNotifier grid notifier
     */
    public GenericEnemy(final int newMaxHealth, final int newCurrentHealth,
    final int newPower, final Position newInitialPosition,
    final GridNotifier newGridNotifier) {
        if (newMaxHealth <= 0) {
            throw new IllegalArgumentException("hp must be > 0");
        }
        if (newPower <= 0) {
            throw new IllegalArgumentException("power must be > 0");
        }

        Objects.requireNonNull(
            newInitialPosition, "spawnPosition cannot be null");
        Objects.requireNonNull(newGridNotifier, "gridNotifier cannot be null");

        this.maxHealth = newMaxHealth;
        this.power = newPower;
        this.initialPosition = newInitialPosition;
        this.currentHealth = newCurrentHealth;
        this.currentPosition = this.initialPosition;
        this.gridNotifier = newGridNotifier;
    }

    //----getters----//
    @Override
    public final int getCurrentHealth() {
        return this.currentHealth;
    }

    @Override
    public final int getMaxHealth() {
        return this.maxHealth;
    }

    @Override
    public final int getPower() {
        return this.power;
    }

    @Override
    public final Position getCurrentPosition() {
        return this.currentPosition;
    }

    @Override
    public final EnemyType getState() {
        return this.currentState.getType();
    }


    @Override
    public final GridNotifier getGridNotifier() {
        return this.gridNotifier;
    }


    // ----setters---- //

    @Override
    public final void setPosition(final Position newPosition) {
        this.currentPosition = newPosition;
    }

    @Override
    public final void setState(final GenericEnemyState newState) {
        if (newState == null || newState == this.currentState) {
            return;
        }

        if (this.currentState != null) {
            this.currentState.exitState(this);
        }

        this.currentState = newState;

        this.currentState.enterState(this);
    }

    @Override
    public final void setGridNotifier(final GridNotifier newGridNotifier) {
        this.gridNotifier = newGridNotifier;
    }


    // ----methods---- //

    @Override
    public final void takeTurn(final Player player) {
        this.currentState.update(this, player);
    }

    @Override
    public final void playerMoved(final Player player) {
        if (this.currentState != null) {
            this.currentState.onPlayerMoved(this, player);
        }
    }
}
