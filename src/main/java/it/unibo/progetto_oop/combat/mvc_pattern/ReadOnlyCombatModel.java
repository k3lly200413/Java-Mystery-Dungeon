package it.unibo.progetto_oop.combat.mvc_pattern;

import it.unibo.progetto_oop.combat.state_pattern.CombatState;
import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * Minimal read-only view of CombatModel for external consumers.
 */
public interface ReadOnlyCombatModel {
    /**
     * Gets the current health of the player.
     *
     * @return the player's health
     */
    int getPlayerHealth();

    /**
     * Gets the current health of the enemy.
     *
     * @return the enemy's health
     */
    int getEnemyHealth();

    /**
     * Gets the maximum health of the player.
     *
     * @return the player's maximum health
     */
    int getPlayerMaxHealth();

    /**
     * Gets the maximum health of the enemy.
     *
     * @return the enemy's maximum health
     */
    int getEnemyMaxHealth();

    /**
     * Gets the power of the player.
     *
     * @return the player's power
     */
    int getPlayerPower();

    /**
     * Gets the power of the enemy.
     *
     * @return the enemy's power
     */
    int getEnemyPower();

    /**
     * Gets the stamina of the player.
     *
     * @return the player's stamina
     */
    int getPlayerStamina();

    /**
     * Returns true if it's the player's turn, false otherwise.
     *
     * @return true if it's the player's turn
     */
    boolean isPlayerTurn();

    /**
     * Returns true if the game is over, false otherwise.
     *
     * @return true if the game is over
     */
    boolean isGameOver();

    /**
     * Returns the size of the view.
     *
     * @return the size of the view
     */
    int getSize();

    /**
     * Returns the position of the player.
     *
     * @return the position of the player
     */
    Position getPlayerPosition();

    /**
     * Returns the position of the enemy.
     *
     * @return the position of the enemy
     */
    Position getEnemyPosition();

    /**
     * Returns true if the enemy is poisoned, false otherwise.
     *
     * @return true if the enemy is poisoned
     */
    boolean isEnemyPoisoned();

    /**
     * Returns true if the player is poisoned, false otherwise.
     *
     * @return true if the player is poisoned
     */
    boolean isPlayerPoison();

    /**
     * Sets whether it's the player's turn.
     *
     * @param b true if it's the player's turn, false otherwise
     */
    void setPlayerTurn(boolean b);

    /**
     * Returns the current state of the enemy.
     *
     * @return the enemy's current state
     */
    CombatState getEnemyState();

    /**
     * Returns the boss turn counter.
     *
     * @return the boss turn counter
     */
    int getBossTurnCounter();

    /**
     * Sets whether it's the boss's turn.
     *
     * @param b true if it's the boss's turn, false otherwise
     */
    void setBossTurn(boolean b);

    /**
     * Increases the boss turn counter.
     */
    void increaseBossTurnCounter();

    /**
     * Returns true if it's the boss's turn, false otherwise.
     *
     * @return true if it's the boss's turn
     */
    boolean isBossTurn();

    /**
     * Returns the boss attack counter.
     *
     * @return the boss attack counter
     */
    int getBossAttackCounter();

    /**
     * Returns the maximum hit of the boss.
     *
     * @return the maximum hit of the boss
     */
    int getMaxBossHit();

    /**
     * Increases the boss attack counter.
     */
    void increaseBossAttackCounter();

    /**
     * Resests the boss attack counter.
     */
    void clearBossAttackCount();

    /**
     * Returns the position of the long-range attack.
     *
     * @return the position of the long-range attack
     */
    Position getAttackPosition();

    /**
     * Returns the position of who died.
     *
     * @return the position of the character who died
     */
    Position getWhoDied();

    /**
     * Resets the positions of the player, enemy, attack and who died.
     */
    void resetPositions();

    /**
     * Returns the name of the enemy.
     *
     * @return the enemy's name
     */
    String getEnemyName();

    /**
     * Returns the speed of the enemy.
     *
     * @return the enemy's speed
     */
    int getEnemySpeed();

    /**
     * decreases the player's stamina.
     *
     * @param staminaToRemove the amount of stamina to remove
     */
    void decreasePlayerStamina(int staminaToRemove);

    /**
     * Sets the current health of the enemy.
     *
     * @param currentHp the enemy's current health
     */
    void setEnemyCurrentHp(int currentHp);

    /**
     * Sets the maximum health of the enemy.
     *
     * @param maxHp the enemy's maximum health
     */
    void setEnemyMaxHp(int maxHp);

    /**
     * Gets the long range power of the enemy.
     *
     * @return the enemy's long range power
     */
    int getEnemyLongRangePower();
}
