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

    boolean isEnemyPoisoned();

    boolean isPlayerPoison();

    void setPlayerTurn(boolean b);

    CombatState getEnemyState();

    int getBossTurnCounter();

    void setBossTurn(boolean b);

    void increaseBossTurnCounter();

    boolean isBossTurn();

    int getBossAttackCounter();

    int getMaxBossHit();

    void increaseBossAttackCounter();

    void clearBossAttackCount();

    Position getAttackPosition();

    Position getWhoDied();

    void resetPositions();

    Object getEnemyName();

    Object getEnemySpeed();

    void decreasePlayerStamina(int staminaToRemove);

    void setEnemyCurrentHp(int currentHp);

    void setEnemyMaxHp(int maxHp);
}
