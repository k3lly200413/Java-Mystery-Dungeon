package it.unibo.progetto_oop.combat.mvc_pattern;

import it.unibo.progetto_oop.combat.state_pattern.CombatState;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import java.util.Objects;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Adapter that exposes the ReadOnlyCombatModel surface and delegates to a CombatModel.
 */
public final class ReadOnlyCombatModelAdapter implements ReadOnlyCombatModel {

    /**
     * The CombatModel to which this adapter delegates.
     */
    private final CombatModel delegate;

    /**
     * Adapter that exposes the ReadOnlyCombatModel surface and delegates to a CombatModel.
     *
     * @param delegate the CombatModel to delegate to
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "Adapter intentionally holds a reference to the"
        + " CombatModel to delegate read-only"
        + " and lightweight state operations;"
        + " adapter is internal and does not expose the model to external callers."
    )
    public ReadOnlyCombatModelAdapter(final CombatModel delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    /**
     * Returns the current health points of the player.
     *
     * @return the current health points of the player
     */
    @Override 
    public int getPlayerHealth() {
        return delegate.getPlayerHealth();
    }

    /**
     * Returns the current health points of the enemy.
     *
     * @return the current health points of the enemy
     */
    @Override 
    public int getEnemyHealth() {
        return delegate.getEnemyHealth();
    }

    /**
     * Returns the maximum health points of the player.
     *
     * @return the maximum health points of the player
     */
    @Override 
    public int getPlayerMaxHealth() {
        return delegate.getPlayerMaxHealth();
    }

    /**
     * Returns the maximum health points of the enemy.
     *
     * @return the maximum health points of the enemy
     */
    @Override 
    public int getEnemyMaxHealth() {
        return delegate.getEnemyMaxHealth();
    }

    /**
     * Returns the current power points of the player.
     *
     * @return the current power points of the player
     */
    @Override 
    public int getPlayerPower() {
        return delegate.getPlayerPower();
    }

    /**
     * Returns the current power points of the enemy.
     *
     * @return the current power points of the enemy
     */
    @Override 
    public int getEnemyPower() {
        return delegate.getEnemyPower();
    }

    /**
     * Returns the current stamina points of the player.
     *
     * @return the current stamina points of the player
     */
    @Override 
    public int getPlayerStamina() {
        return delegate.getPlayerStamina();
    }

    /**
     * Returns whether it is the player's turn.
     *
     * @return true if it is the player's turn, false otherwise
     */
    @Override 
    public boolean isPlayerTurn() {
        return delegate.isPlayerTurn();
    }

    /**
     * Returns whether the game is over.
     *
     * @return true if the game is over, false otherwise
     */
    @Override 
    public boolean isGameOver() {
        return delegate.isGameOver();
    }

    /**
     * Returns the size of the view.
     */
    @Override 
    public int getSize() {
        return delegate.getSize();
    }

    /**
     * Returns the current position of the player.
     *
     * @return the current position of the player
     */
    @Override 
    public Position getPlayerPosition() {
        return delegate.getPlayerPosition();
    }

    /**
     * Returns the current position of the enemy.
     *
     * @return the current position of the enemy
     */
    @Override 
    public Position getEnemyPosition() {
        return delegate.getEnemyPosition();
    }

    /**
     * Returns whether the enemy is poisoned.
     *
     * @return true if the enemy is poisoned, false otherwise
     */
    @Override 
    public boolean isEnemyPoisoned() {
        return delegate.isEnemyPoisoned();
    }

    /**
     * Returns whether the player is poisoned.
     *
     * @return true if the player is poisoned, false otherwise
     */
    @Override 
    public boolean isPlayerPoison() {
        return delegate.isPlayerPoison();
    }

    /**
     * Sets whether it is the player's turn.
     */
    @Override
    public void setPlayerTurn(final boolean b) {
        delegate.setPlayerTurn(b);
    }

    /**
     * Returns the current state of the enemy.
     *
     * @return the current state of the enemy
     */
    @Override
    public CombatState getEnemyState() {
        return delegate.getEnemyState();
    }

    /**
     * Returns the current turn counter of the boss.
     *
     * @return the current turn counter of the boss
     */
    @Override
    public int getBossTurnCounter() {
        return delegate.getBossTurnCounter();
    }

    /**
     * Sets whether it is the boss's turn.
     */
    @Override
    public void setBossTurn(final boolean b) {
delegate.setBossTurn(b);
    }

    /**
     * Increases the boss's turn counter by one.
     */
    @Override
    public void increaseBossTurnCounter() {
delegate.increaseBossTurnCounter();
    }

    /**
     * Returns whether it is the boss's turn.
     *
     * @return true if it is the boss's turn, false otherwise
     */
    @Override
    public boolean isBossTurn() {
        return delegate.isBossTurn();
    }

    /**
     * Returns the current attack counter of the boss.
     *
     * @return the current attack counter of the boss
     */
    @Override
    public int getBossAttackCounter() {
        return delegate.getBossAttackCounter();
    }

    /**
     * Returns the maximum hit points of the boss.
     *
     * @return the maximum hit points of the boss
     */
    @Override
    public int getMaxBossHit() {
        return delegate.getMaxBossHit();
    }

    /**
     * Increases the boss's attack counter by one.
     */
    @Override
    public void increaseBossAttackCounter() {
delegate.increaseBossAttackCounter();
    }

    /**
     * Clears the boss's attack counter.
     */
    @Override
    public void clearBossAttackCount() {
delegate.clearBossAttackCount();
    }

    /**
     * Returns the current attack position.
     *
     * @return the current attack position
     */
    @Override
    public Position getAttackPosition() {
        return delegate.getAttackPosition();
    }

    /**
     * Returns the position of the character who died.
     */
    @Override
    public Position getWhoDied() {
        return delegate.getWhoDied();
    }

    /**
     * Resets the positions of all characters.
     */
    @Override
    public void resetPositions() {
delegate.resetPositions();
    }

    /**
     * Returns the name of the enemy.
     */
    @Override
    public String getEnemyName() {
        return delegate.getEnemyName();
    }

    /**
     * Returns the speed of the enemy.
     */
    @Override
    public int getEnemySpeed() {
        return delegate.getEnemySpeed();
    }

    /**
     * Decreases the player's stamina by the specified amount.
     *
     * @param staminaToRemove the amount of stamina to remove
     */
    @Override
    public void decreasePlayerStamina(final int staminaToRemove) {
        delegate.decreasePlayerStamina(staminaToRemove);
    }

    /**
     * Sets the enemy's current hit points.
     *
     * @param currentHp the current hit points to set
     */
    @Override
    public void setEnemyCurrentHp(final int currentHp) {
        delegate.setEnemyCurrentHp(currentHp);
    }

    /**
     * Sets the enemy's maximum hit points.
     *
     * @param maxHp the maximum hit points to set
     */
    @Override
    public void setEnemyMaxHp(final int maxHp) {
        delegate.setEnemyMaxHp(maxHp);
    }
}
