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



    public CombatModel(int size,int StaminaMax, int playerPower,
            int playerPoisonPower, int playerLongRangePower, 
            int enemyPower, int enemySpeed, String enemyName) {
        
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
    public void increasePlayerHealth(int amount){
        this.playerHealth = Math.min(maxHealth, this.playerHealth + amount);
    }

    public void increasePlayerPower(int power){
        this.playerPower += power;
    }

    public void increaseEnemyPower(int power){
        this.enemyPower += power;
    }

    public void resetPlayerPower() {
        this.playerPower = this.basicPlayerPower; // Reset player power to basic value
    }

    public void decreasePlayerHealth(int amount) {
        this.playerHealth = Math.max(0, this.playerHealth - amount);  //only decrease health, do not allow negative values
    }

    public void increasePlayerMaxStamina(int amount) {
        this.playerStaminaMax += amount;
    }

    public void decreasePlayerMaxStamina(int amount) {
        this.playerStaminaMax = Math.max(0, (this.playerStaminaMax - amount));
    }
    public void decreasePlayerStamina(int amount) {
        this.playerStamina = Math.max(0, this.playerStamina - amount);  //only decrease stamina, do not allow negative values
    }

    public void increasePlayerStamina(int amount) {
        this.playerStamina= Math.min(this.playerStaminaMax, (this.playerStamina + amount));
    }

    public void decreaseEnemyHealth(int amount) {
        this.enemyHealth = Math.max(0, this.enemyHealth - amount);    //only decrease health, do not allow negative values
    }
    
    public boolean isGameOver() {
        if(this.playerHealth <= 0){
            this.whoDied = this.getPlayerPosition();
            return true;
        }else if(this.enemyHealth <= 0){
            this.whoDied = this.getEnemyPosition();
            return true;
        }
        return false;
    }

    public void clearBossAttackCount(){
        this.bossAttackCounter = 0;
    }

    public void increaseBossAttackCounter() {
        this.bossAttackCounter++;
    }
    public void increaseBossTurnCounter() {
        this.bossTurnCounter++;
    }
    
    public void resetBossTurnCounter() {
        this.bossTurnCounter = 0;
    }

    public void addDeathRayPosition(Position nextPosition) {
        this.deathRayPath.add(nextPosition);
    }

    public void clearDeathRayPath() {
        this.deathRayPath.clear();
    }

    // Getters
    public int getSize() {
        return this.size;
    }

    public Position getPlayerPosition() {
        return this.playerPosition;
    }

    public Position getEnemyPosition() {
        return this.enemyPosition;
    }

    public Position getAttackPosition() {
        return this.attackPosition;
    }

    public int getPlayerHealth() {
        return this.playerHealth;
    }

    public int getEnemyHealth() {
        return this.enemyHealth;
    }

    public int getMaxHealth() {
        return this.maxHealth;
    }

    public int getPlayerStamina() {
        return this.playerStamina;
    }

    public int getPlayerStaminaMax() {
        return this.playerStaminaMax;
    }

    public int getPlayerPower() {
        return this.playerPower;
    }

    public int getPlayerPoisonPower() {
        return this.playerPoisonPower;
    }

    public int getEnemyPoisonPower() {
        return this.enemyPoisonPower;
    }

    public int getPlayerLongRangePower() {
        return this.playerLongRangePower;
    }

    public int getEnemyLongRangePower() {
        return this.enemyLongRangePower;
    }

    public int getEnemyPower() {
        return this.enemyPower;
    }

    public int getEnemySpeed() {
        return this.enemySpeed;
    }

    public String getEnemyName() {
        return this.enemyName;
    }

    public boolean isEnemyPoisoned() {
        return this.enemyPoisoned;
    }

    public boolean isPlayerPoison() {
        return this.isPlayerPoison;
    }

    public boolean isPlayerTurn() {
        return this.isPlayerTurn;
    }

    public int getBasicPlayerPower() {
        return this.basicPlayerPower;
    }

    public Position getWhoDied() {
        return this.whoDied;
    }

    public boolean isBossTurn() {
        return this.isBossTurn;
    }

    public int getBossAttackCounter() {
        return this.bossAttackCounter;
    }

    public int getMaxBossHit() {
        return this.maxBossHit;
    }

    public String getCurrentBossState() {
        return this.currentBossState;
    }

    public ArrayList<Position> getDeathRayPath() {
        return this.deathRayPath;
    }

    public int getBossTurnCounter() {
        return this.bossTurnCounter;
    }

    public boolean isPoisonAnimation() {
        return this.poisonAnimation;
    }
    
    // setters
    public void setPlayerPosition(Position playerPosition) {
        this.playerPosition = Objects.requireNonNull(playerPosition);
    }

    public void setEnemyPosition(Position enemyPosition) {
        this.enemyPosition = Objects.requireNonNull(enemyPosition);
    }

    public void setAttackPosition(Position attackPosition) {
        this.attackPosition = Objects.requireNonNull(attackPosition);
    }

    public void setEnemyPoisoned(boolean enemyPoisoned) {
        this.enemyPoisoned = enemyPoisoned;
    }

    public void setPlayerTurn(boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
    }

    public void setPlayerPoisoned(boolean isPoisoned) {
        this.isPlayerPoison = isPoisoned;
    }

    public void setPoisonAnimation(boolean newState){
        this.poisonAnimation = newState;
    }

    public void setBossTurn(boolean bossTurn){
        this.isBossTurn = bossTurn;
    }

    public void setBossAttackCounter(int bossAttackCounter) {
        this.bossAttackCounter = bossAttackCounter;
    }

    public void setCurrentBossState(String currentBossState) {
        this.currentBossState = currentBossState;
    }

    @Override
    public int getHp() {
        return this.getPlayerHealth();
    }

    @Override
    public int getMaxHP() {
        return this.getMaxHealth();
    }

    public int applyAttackHealth(
        boolean isPlayerAttacker, int damage) {
    if (isPlayerAttacker) {
        decreaseEnemyHealth(damage);
        return getEnemyHealth();
    } else {
        decreasePlayerHealth(damage);
        return getPlayerHealth();
    }
}

}
