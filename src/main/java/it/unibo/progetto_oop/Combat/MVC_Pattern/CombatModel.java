package it.unibo.progetto_oop.Combat.MVC_Pattern;
import java.util.ArrayList;
import java.util.Objects;

import it.unibo.progetto_oop.Combat.Position.Position;
import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;

public class CombatModel implements PossibleUser{

    private int size;
    private Position playerPosition;
    private Position enemyPosition;
    private Position attackPosition;

    private int playerHealth;
    private int enemyHealth;
    private final int maxHealth = 100;

    private int playerStamina;
    private int playerStaminaMax;

    private int playerPower;
    private final int playerPoisonPower;
    private int enemyPoisonPower;
    private int playerLongRangePower;
    private int enemyLongRangePower;
    private int enemyPower;
    private final int enemySpeed; // If needed for future logic
    private final String enemyName; // If needed

    private boolean enemyPoisoned;
    private boolean isPlayerPoison;
    private boolean isPlayerTurn = true; // Added to manage turns
    private final int basicPlayerPower;
    private Position whoDied;

    private boolean isBossTurn;
    private int bossAttackCounter = 0;
    private final int maxBossHit = 3;

    private String currentBossState = "NORMAL";
    private ArrayList<Position> deathRayPath = new ArrayList<>();
    private int bossTurnCounter = 0;

    private boolean poisonAnimation;



    public CombatModel(final int size, final int StaminaMax, final int playerPower,
            final int playerPoisonPower, final int playerLongRangePower,
            final int enemyPower, final int enemySpeed, final String enemyName) {

        this.size = size;
        this.playerStaminaMax = StaminaMax;
        this.playerPower = playerPower;
        this.playerPoisonPower = playerPoisonPower;
        this.enemyPower = enemyPower;
        this.enemySpeed = enemySpeed;
        this.enemyName = enemyName;
        this.basicPlayerPower = this.playerPower;

        resetPositions();
        this.attackPosition = this.playerPosition;

        this.playerHealth = maxHealth;
        this.enemyHealth = maxHealth;
        this.playerStamina = StaminaMax;
        this.enemyPoisonPower = playerPoisonPower;
        this.playerLongRangePower = playerLongRangePower;
        this.enemyLongRangePower = playerLongRangePower;

        this.enemyPoisoned = false;
        this.isPlayerPoison = false;
        this.isBossTurn = false;

        this.deathRayPath = new ArrayList<>();
        this.deathRayPath.add(enemyPosition);

    }

    public final void resetPositions() {
        // Same logic as original Player() method
        this.playerPosition = new Position((this.size / 3) - 2, (this.size / 2));
        this.enemyPosition = new Position(this.size - ((this.size / 3) - 1), (this.size / 2));
    }

    // for combat logic
    public final void increasePlayerHealth(final int amount) {
        this.playerHealth = Math.min(maxHealth, this.playerHealth + amount);
    }

    public final void increasePlayerPower(final int power) {
        this.playerPower += power;
    }

    public final void increaseEnemyPower(final int power) {
        this.enemyPower += power;
    }

    public final void resetPlayerPower() {
        this.playerPower = this.basicPlayerPower; // Reset player power to basic value
    }

    public final void decreasePlayerHealth(final int amount) {
        this.playerHealth = Math.max(0, this.playerHealth - amount); // only decrease health, do not allow negative
                                                                     // values
    }

    public final void increasePlayerMaxStamina(final int amount) {
        this.playerStaminaMax += amount;
    }

    public final void decreasePlayerMaxStamina(final int amount) {
        this.playerStaminaMax = Math.max(0, (this.playerStaminaMax - amount));
    }

    public final void decreasePlayerStamina(final int amount) {
        this.playerStamina = Math.max(0, this.playerStamina - amount); // only decrease stamina, do not allow negative
                                                                       // values
    }

    public final void increasePlayerStamina(final int amount) {
        this.playerStamina = Math.min(this.playerStaminaMax, (this.playerStamina + amount));
    }

    public final void decreaseEnemyHealth(final int amount) {
        this.enemyHealth = Math.max(0, this.enemyHealth - amount); // only decrease health, do not allow negative values
    }

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

    public final void clearBossAttackCount() {
        this.bossAttackCounter = 0;
    }

    public final void increaseBossAttackCounter() {
        this.bossAttackCounter++;
    }

    public final void increaseBossTurnCounter() {
        this.bossTurnCounter++;
    }

    public final void resetBossTurnCounter() {
        this.bossTurnCounter = 0;
    }

    public final void addDeathRayPosition(final Position nextPosition) {
        this.deathRayPath.add(nextPosition);
    }

    public final void clearDeathRayPath() {
        this.deathRayPath.clear();
    }

    // Getters
    public final int getSize() {
        return this.size;
    }

    public final Position getPlayerPosition() {
        return this.playerPosition;
    }

    public final Position getEnemyPosition() {
        return this.enemyPosition;
    }

    public final Position getAttackPosition() {
        return this.attackPosition;
    }

    public final int getPlayerHealth() {
        return this.playerHealth;
    }

    public final int getEnemyHealth() {
        return this.enemyHealth;
    }

    public final int getMaxHealth() {
        return this.maxHealth;
    }

    public final int getPlayerStamina() {
        return this.playerStamina;
    }

    public final int getPlayerStaminaMax() {
        return this.playerStaminaMax;
    }

    public final int getPlayerPower() {
        return this.playerPower;
    }

    public final int getPlayerPoisonPower() {
        return this.playerPoisonPower;
    }

    public final int getEnemyPoisonPower() {
        return this.enemyPoisonPower;
    }

    public final int getPlayerLongRangePower() {
        return this.playerLongRangePower;
    }

    public final int getEnemyLongRangePower() {
        return this.enemyLongRangePower;
    }

    public final int getEnemyPower() {
        return this.enemyPower;
    }

    public final int getEnemySpeed() {
        return this.enemySpeed;
    }

    public final String getEnemyName() {
        return this.enemyName;
    }

    public final boolean isEnemyPoisoned() {
        return this.enemyPoisoned;
    }

    public final boolean isPlayerPoison() {
        return this.isPlayerPoison;
    }

    public final boolean isPlayerTurn() {
        return this.isPlayerTurn;
    }

    public final int getBasicPlayerPower() {
        return this.basicPlayerPower;
    }

    public final Position getWhoDied() {
        return this.whoDied;
    }

    public final boolean isBossTurn() {
        return this.isBossTurn;
    }

    public final int getBossAttackCounter() {
        return this.bossAttackCounter;
    }

    public final int getMaxBossHit() {
        return this.maxBossHit;
    }

    public final String getCurrentBossState() {
        return this.currentBossState;
    }

    public final ArrayList<Position> getDeathRayPath() {
        return this.deathRayPath;
    }

    public final int getBossTurnCounter() {
        return this.bossTurnCounter;
    }

    public final boolean isPoisonAnimation() {
        return this.poisonAnimation;
    }

    // setters
    public final void setPlayerPosition(final Position playerPosition) {
        this.playerPosition = Objects.requireNonNull(playerPosition);
    }

    public final void setEnemyPosition(final Position enemyPosition) {
        this.enemyPosition = Objects.requireNonNull(enemyPosition);
    }

    public final void setAttackPosition(final Position attackPosition) {
        this.attackPosition = Objects.requireNonNull(attackPosition);
    }

    /**
     * Sets the enemy's poisoned state to true
     * if it was not already poisoned and the new value is true.
     * @param newEnemyPoisoned true if the enemy should be poisoned,
     * false otherwise
     */
    public final void setEnemyPoisoned(final boolean newEnemyPoisoned) {
        if (!this.enemyPoisoned && newEnemyPoisoned) {
            this.enemyPoisoned = true;
        }
    }

    /**
     * Sets whether it is the player's turn.
     * @param playerTurn true if it is the player's turn, false otherwise
     */
    public final void setPlayerTurn(final boolean playerTurn) {
        System.out.println("Changed player turn");
        this.isPlayerTurn = playerTurn;
    }

    /**
     * Sets whether the player is poisoned.
     * @param isPoisoned true if the player is poisoned, false otherwise
     */
    @Override
    public final void setPlayerPoisoned(final boolean isPoisoned) {
        this.isPlayerPoison = isPoisoned;
    }

    /**
     * Sets the state of the poison animation.
     * @param newState true if the poison animation should be active,
     * false otherwise
     */
    public final void setPoisonAnimation(final boolean newState) {
        this.poisonAnimation = newState;
    }

    /**
     * Sets whether it is the boss's turn.
     * @param bossTurn true if it is the boss's turn, false otherwise
     */
    public final void setBossTurn(final boolean bossTurn) {
        this.isBossTurn = bossTurn;
    }


    /**
     * Sets the boss attack counter to the specified value.
     * This method is final to prevent unsafe overrides in subclasses.
     * @param newBossAttackCounter the new value for the boss attack counter
     */
    public final void setBossAttackCounter(final int newBossAttackCounter) {
        this.bossAttackCounter = newBossAttackCounter;
    }

    /**
     * Sets the current state of the boss.
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
     * @return max HP
     */
    @Override
    public int getMaxHP() {
        return this.getMaxHealth();
    }

    /**
     * Applies attack damage to either the player or the enemy,
     * depending on the attacker.
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
