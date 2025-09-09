package it.unibo.progetto_oop.combat.mvc_pattern;
import java.util.ArrayList;
import java.util.Objects;

import it.unibo.progetto_oop.combat.combat_builder.CombatBuilder;
import it.unibo.progetto_oop.overworld.player.adapter_pattern.PossibleUser;
import it.unibo.progetto_oop.overworld.playground.data.Position;


/**
 * The CombatModel class represents the model component
 * in the MVC pattern for a combat system.
 * It manages the state and logic of the combat,
 * including player and enemy attributes, positions,
 * health, stamina, and turn management.
 */
public class CombatModel implements PossibleUser {

    /** Offset applied to the player's initial X coordinate. */
    private static final int PLAYER_X_OFFSET = 2;

    /** Divisor used to calculate the initial X
     *  positions of player and enemy. */
    private static final int DIVISOR = 3;

    /** Offset applied to the enemy's initial X coordinate. */
    private static final int ENEMY_OFFSET = 1;

    /** Divisor used to calculate the initial Y
     *  position (half of the board size). */
    private static final int HALF_DIVISOR = 2;

    /** The size of the game board or arena. */
    private final int size;

    /** The current position of the player. */
    private Position playerPosition;

    /** The current position of the enemy. */

    private Position enemyPosition;

    /** The current position of the active attack. */
    private Position attackPosition;

    /** The current health points of the player. */
    private int playerHealth;

    private int playerMaxHealth;

    /** The current health points of the enemy. */
    private int enemyHealth;

    private int enemyMaxHealth;

    /** The maximum health points allowed for both player and enemy. */
    private int maxHealth;

    /** The current stamina points of the player. */
    private int playerStamina;

    /** The maximum stamina points of the player. */
    private int playerStaminaMax;

    /** The current attack power of the player. */
    private int playerPower;

    /** The poison attack power of the player. */
    private final int playerPoisonPower;

    /** The poison attack power of the enemy. */
    private final int enemyPoisonPower;

    /** The long-range attack power of the player. */
    private final int playerLongRangePower;

    /** The long-range attack power of the enemy. */
    private final int enemyLongRangePower;

    /** The current attack power of the enemy. */
    private int enemyPower;

    /** The speed of the enemy (reserved for future logic). */
    private final int enemySpeed;

    /** The name of the enemy (reserved for future use). */
    private final String enemyName;

    /** Whether the enemy is poisoned. */
    private boolean enemyPoisoned;

    /** Whether the player is poisoned. */
    private boolean isPlayerPoison;

    /** Indicates if it is currently the player's turn. */
    private boolean isPlayerTurn = true;

    /** The base power value of the player. */
    private final int basicPlayerPower;

    /** The position of the last character who died. */
    private Position whoDied;

    /** Indicates if it is currently the boss's turn. */
    private boolean isBossTurn;

    /** Counter tracking the number of boss attacks. */
    private int bossAttackCounter;

    /** The maximum number of hits the boss can perform in one sequence. */
    private final int maxBossHit = 3;

    /** The current state of the boss (e.g., NORMAL, ENRAGED). */
    private String currentBossState = "NORMAL";

    /** The path of the boss's death ray attack. */
    private ArrayList<Position> deathRayPath = new ArrayList<>();

    /** Counter tracking the number of turns taken by the boss. */
    private int bossTurnCounter;

    /** Whether the poison animation is active. */
    private boolean poisonAnimation;

    /**
     * Constructs a CombatModel with specified parameters.
     *
     * @param builder the CombatBuilder instance containing
     *                the configuration parameters
     */
    public CombatModel(final CombatBuilder builder) {

        this.playerMaxHealth = builder.getPlayerMaxHealth();
        this.enemyMaxHealth = builder.getEnemyMaxHealth();
        this.size = builder.getSize();
        this.playerStaminaMax = builder.getStaminaMax();
        this.playerPower = builder.getPlayerPower();
        this.playerPoisonPower = builder.getPlayerPoisonPower();
        this.enemyPower = builder.getEnemyPower();
        this.enemySpeed = builder.getEnemySpeed();
        this.enemyName = builder.getEnemyName();
        this.basicPlayerPower = this.playerPower;
        this.playerHealth = builder.getPlayerCurrentHp();
        this.enemyHealth = builder.getEnemyCurrentHp();

        resetPositions();
        this.attackPosition = this.playerPosition;

        this.playerStamina = builder.getStaminaMax();
        this.enemyPoisonPower = builder.getPlayerPoisonPower();
        this.playerLongRangePower = builder.getPlayerLongRangePower();
        this.enemyLongRangePower = builder.getPlayerLongRangePower();

        this.enemyPoisoned = false;
        this.isPlayerPoison = false;
        this.isBossTurn = false;

        this.deathRayPath = new ArrayList<>();
        this.deathRayPath.add(enemyPosition);

    }

/**
 * Resets player and enemy positions to their default values.
 * Same logic as the original Player() constructor.
 */
public final void resetPositions() {
    this.playerPosition = new Position((this.size / DIVISOR) - PLAYER_X_OFFSET,
    (this.size / HALF_DIVISOR));
    this.enemyPosition = new Position(
    this.size - (this.size / DIVISOR) + ENEMY_OFFSET,
    this.size / HALF_DIVISOR);
}

/**
 * Increases the player's health by the specified amount,
 * without exceeding the maximum health.
 *
 * @param amount the health points to add
 */
@Override
public final void increasePlayerHealth(final int amount) {
    this.playerHealth = Math.min(this.playerMaxHealth, this.playerHealth + amount);
}

/**
 * Increases the player's health by the specified amount,
 * without exceeding the maximum health.
 *
 * @param amount the health points to add
 */
@Override
public final void increasePlayerMaxHealth(final int amount) {
    this.playerMaxHealth = this.playerMaxHealth + amount;
}

/**
 * Increases the player's power by the specified amount.
 *
 * @param power the power points to add
 */
@Override
public final void increasePlayerMaxPower(final int power) {
    this.playerPower += power;
}

/**
 * Increases the enemy's power by the specified amount.
 *
 * @param power the power points to add
 */
public final void increaseEnemyPower(final int power) {
    this.enemyPower += power;
}

/**
 * Resets the player's power to its base value.
 */
public final void resetPlayerPower() {
    this.playerPower = this.basicPlayerPower;
}

/**
 * Decreases the player's health by the specified amount,
 * without allowing the value to go below zero.
 *
 * @param amount the health points to subtract
 */
public final void decreasePlayerHealth(final int amount) {
    this.playerHealth = Math.max(0, this.playerHealth - amount);
}

/**
 * Increases the player's maximum stamina by the specified amount.
 *
 * @param amount the stamina points to add
 */
@Override
public final void increasePlayerMaxStamina(final int amount) {
    this.playerStaminaMax += amount;
}

/**
 * Decreases the player's maximum stamina by the specified amount,
 * without allowing the value to go below zero.
 *
 * @param amount the stamina points to subtract
 */
public final void decreasePlayerMaxStamina(final int amount) {
    this.playerStaminaMax = Math.max(0, this.playerStaminaMax - amount);
}

/**
 * Decreases the player's stamina by the specified amount,
 * without allowing the value to go below zero.
 *
 * @param amount the stamina points to subtract
 */
public final void decreasePlayerStamina(final int amount) {
    this.playerStamina = Math.max(0, this.playerStamina - amount);
}

/**
 * Increases the player's stamina by the specified amount,
 * without exceeding the maximum stamina.
 *
 * @param amount the stamina points to add
 */
public final void increasePlayerStamina(final int amount) {
    this.playerStamina = Math.min(this.playerStaminaMax,
        (this.playerStamina + amount));
}

/**
 * Decreases the enemy's health by the specified amount,
 * without allowing the value to go below zero.
 *
 * @param amount the health points to subtract
 */
public final void decreaseEnemyHealth(final int amount) {
    this.enemyHealth = Math.max(0, this.enemyHealth - amount);
}

/**
 * Checks if the game is over by verifying if either
 * the player or the enemy has 0 or less health.
 * Sets whoDied to the position of the entity that died.
 *
 * @return true if the game is over, false otherwise
 */
public final boolean isGameOver() {
    if (this.playerHealth <= 0) {
        this.whoDied = this.getPlayerPosition();
        return true;
    } else if (this.enemyHealth <= 0) {
        this.whoDied = this.getEnemyPosition();
        return true;
    }
    return false;
}

    /**
     * Resets the boss attack counter to zero.
     */
    public final void clearBossAttackCount() {
        this.bossAttackCounter = 0;
    }

    /**
     * Increases the boss attack counter by one.
     */
    public final void increaseBossAttackCounter() {
        this.bossAttackCounter++;
    }

    /**
     * Increases the boss turn counter by one.
     */
    public final void increaseBossTurnCounter() {
        this.bossTurnCounter++;
    }

    /**
     * Resets the boss turn counter to zero.
     */
    public final void resetBossTurnCounter() {
        this.bossTurnCounter = 0;
    }

    /**
     * Adds a position to the boss's death ray path.
     *
     * @param nextPosition the next position to add to the death ray path
     */
    public final void addDeathRayPosition(final Position nextPosition) {
        this.deathRayPath.add(nextPosition);
    }

    /**
     * Clears the boss's death ray path.
     */
    public final void clearDeathRayPath() {
        this.deathRayPath.clear();
    }

    // Getters
    /**
     * Returns the size of the combat area.
     *
     * @return the size of the combat area
     */
    public final int getSize() {
        return this.size;
    }

    /**
     * Returns the player's current position.
     *
     * @return the player's position
     */
    public final Position getPlayerPosition() {
        return this.playerPosition;
    }

    /**
     * Returns the enemy's position.
     *
     * @return the enemy's position
     */
    public final Position getEnemyPosition() {
        return this.enemyPosition;
    }

    /**
     * Returns the current attack position.
     *
     * @return the attack position
     */
    public final Position getAttackPosition() {
        return this.attackPosition;
    }

    /**
     * Returns the player's current health.
     *
     * @return the player's health
     */
    public final int getPlayerHealth() {
        return this.playerHealth;
    }

    /**
     * Returns the enemy's current health.
     *
     * @return the enemy's health
     */
    public final int getEnemyHealth() {
        return this.enemyHealth;
    }

    /**
     * Returns the maximum health value.
     *
     * @return the maximum health
     */
    public final int getMaxHealth() {
        return this.maxHealth;
    }

    /**
     * Returns the player's current stamina.
     *
     * @return the player's stamina
     */
    public final int getPlayerStamina() {
        return this.playerStamina;
    }

    /**
     * Returns the player's maximum stamina.
     *
     * @return the player's maximum stamina
     */
    public final int getPlayerStaminaMax() {
        return this.playerStaminaMax;
    }

    /**
     * Returns the player's current power.
     *
     * @return the player's power
     */
    public final int getPlayerPower() {
        return this.playerPower;
    }

    /**
     * Returns the player's poison power.
     *
     * @return the player's poison power
     */
    public final int getPlayerPoisonPower() {
        return this.playerPoisonPower;
    }

    /**
     * Returns the enemy's poison power.
     *
     * @return the enemy's poison power
     */
    public final int getEnemyPoisonPower() {
        return this.enemyPoisonPower;
    }

    /**
     * Returns the player's long range power.
     *
     * @return the player's long range power
     */
    public final int getPlayerLongRangePower() {
        return this.playerLongRangePower;
    }

    /**
     * Returns the enemy's long range power.
     *
     * @return the enemy's long range power
     */
    public final int getEnemyLongRangePower() {
        return this.enemyLongRangePower;
    }

    /**
     * Returns the enemy's power.
     *
     * @return the enemy's power
     */
    public final int getEnemyPower() {
        return this.enemyPower;
    }

    /**
     * Returns the enemy's speed.
     *
     * @return the enemy's speed
     */
    public final int getEnemySpeed() {
        return this.enemySpeed;
    }

    /**
     * Returns the enemy's name.
     *
     * @return the enemy's name
     */
    public final String getEnemyName() {
        return this.enemyName;
    }

    /**
     * Returns whether the enemy is poisoned.
     *
     * @return true if the enemy is poisoned, false otherwise
     */
    public final boolean isEnemyPoisoned() {
        return this.enemyPoisoned;
    }

    /**
     * Returns whether the player is poisoned.
     *
     * @return true if the player is poisoned, false otherwise
     */
    public final boolean isPlayerPoison() {
        return this.isPlayerPoison;
    }

    /**
     * Returns whether it is the player's turn.
     *
     * @return true if it is the player's turn, false otherwise
     */
    public final boolean isPlayerTurn() {
        return this.isPlayerTurn;
    }

    /**
     * Returns the player's basic power.
     *
     * @return the player's basic power
     */
    public final int getBasicPlayerPower() {
        return this.basicPlayerPower;
    }

    /**
     * Returns the position of the entity that died.
     *
     * @return the position of the entity that died
     */
    public final Position getWhoDied() {
        return this.whoDied;
    }

    /**
     * Returns whether it is the boss's turn.
     *
     * @return true if it is the boss's turn, false otherwise
     */
    public final boolean isBossTurn() {
        return this.isBossTurn;
    }

    /**
     * Returns the current boss attack counter.
     *
     * @return the boss attack counter
     */
    public final int getBossAttackCounter() {
        return this.bossAttackCounter;
    }

    /**
     * Returns the maximum number of boss hits.
     *
     * @return the maximum boss hit count
     */
    public final int getMaxBossHit() {
        return this.maxBossHit;
    }

    /**
     * Returns the current state of the boss.
     *
     * @return the current boss state
     */
    public final String getCurrentBossState() {
        return this.currentBossState;
    }

    /**
     * Returns the path of the boss's death ray attack.
     *
     * @return the death ray path as a list of positions
     */
    public final ArrayList<Position> getDeathRayPath() {
        return this.deathRayPath;
    }

    /**
     * Returns the boss turn counter.
     *
     * @return the boss turn counter
     */
    public final int getBossTurnCounter() {
        return this.bossTurnCounter;
    }

    /**
     * Returns whether the poison animation is active.
     *
     * @return true if the poison animation is active, false otherwise
     */
    public final boolean isPoisonAnimation() {
        return this.poisonAnimation;
    }

    // setters
    /**
     * Sets the player's position.
     *
     * @param newPlayerPosition the new position for the player
     */
    public final void setPlayerPosition(final Position newPlayerPosition) {
        this.playerPosition = Objects.requireNonNull(newPlayerPosition);
    }

    /**
     * Sets the enemy's position.
     *
     * @param newEnemyPosition the new position for the enemy
     */
    public final void setEnemyPosition(final Position newEnemyPosition) {
        this.enemyPosition = Objects.requireNonNull(newEnemyPosition);
    }

    /**
     * Sets the attack position.
     *
     * @param newAttackPosition the new attack position
     */
    public final void setAttackPosition(final Position newAttackPosition) {
        this.attackPosition = Objects.requireNonNull(newAttackPosition);
    }

    /**
     * Sets the enemy's poisoned state to true
     * if it was not already poisoned and the new value is true.
     *
     * @param newEnemyPoisoned true if the enemy should be poisoned,
     * false otherwise
     */
    public final void setEnemyPoisoned(final boolean newEnemyPoisoned) {
        if (!this.enemyPoisoned && newEnemyPoisoned) {
            this.enemyPoisoned = true;
        }
    }

    public final void setEnemyMaxHp(final int newHpToSet) {
        this.enemyMaxHealth = newHpToSet;
    }

    public final void setEnemyCurrentHp(final int newCurrentHp) {
        this.enemyHealth = newCurrentHp;
    }

    /**
     * Sets whether it is the player's turn.
     *
     * @param playerTurn true if it is the player's turn, false otherwise
     */
    public final void setPlayerTurn(final boolean playerTurn) {
        this.isPlayerTurn = playerTurn;
    }

    /**
     * Sets whether the player is poisoned.
     *
     * @param isPoisoned true if the player is poisoned, false otherwise
     */
    @Override
    public final void setPlayerPoisoned(final boolean isPoisoned) {
        this.isPlayerPoison = isPoisoned;
    }

    public final void setPlayerMaxHp(final int newMaxHp) {
        this.playerMaxHealth = newMaxHp;
    }

    public final void setPlayerCurrentHp(final int newCurrentHealth) {
        this.playerHealth = newCurrentHealth;
    }

    /**
     * Sets the state of the poison animation.
     *
     * @param newState true if the poison animation should be active,
     * false otherwise
     */
    public final void setPoisonAnimation(final boolean newState) {
        this.poisonAnimation = newState;
    }

    /**
     * Sets whether it is the boss's turn.
     *
     * @param bossTurn true if it is the boss's turn, false otherwise
     */
    public final void setBossTurn(final boolean bossTurn) {
        this.isBossTurn = bossTurn;
    }

    /**
     * Sets the boss attack counter to the specified value.
     * This method is final to prevent unsafe overrides in subclasses.
     *
     * @param newBossAttackCounter the new value for the boss attack counter
     */
    public final void setBossAttackCounter(final int newBossAttackCounter) {
        this.bossAttackCounter = newBossAttackCounter;
    }

    /**
     * Sets the current state of the boss.
     *
     * @param newCurrentBossState the new state of the boss
     */
    public void setCurrentBossState(final String newCurrentBossState) {
        this.currentBossState = newCurrentBossState;
    }

    @Override
    public final int getHp() {
        return this.getPlayerHealth();
    }

    /**
     * Return the Max HP of the entity.
     *
     * @return max HP
     */
    @Override
    public int getMaxHP() {
        return this.getMaxHealth();
    }

    public int getPlayerMaxHealth() {
        return this.playerMaxHealth;
    }

    public int getEnemyMaxHealth() {
        return this.enemyMaxHealth;
    }

    /**
     * Applies attack damage to either the player or the enemy,
     * depending on the attacker.
     *
     * @param isPlayerAttacker true if the player is attacking,
     * false if the enemy is attacking
     * @param damage the amount of damage to apply
     * @return the remaining health of the attacked entity
     */
    public final int applyAttackHealth(
        final boolean isPlayerAttacker, final int damage) {
    if (isPlayerAttacker) {
        decreaseEnemyHealth(damage);
        return getEnemyHealth();
    } else {
        decreasePlayerHealth(damage);
        return getPlayerHealth();
    }
}

}
