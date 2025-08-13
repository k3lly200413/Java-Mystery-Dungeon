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
    private int playerStaminaMax = 100;

    private int playerPower;
    private int playerPoisonPower;
    private int enemyPoisonPower;
    private int playerLongRangePower;
    private int enemyLongRangePower;
    private int enemyPower;
    private final int enemySpeed; // If needed for future logic
    private final String enemyName; // If needed

    private boolean enemyPoisoned;
    private boolean isPlayerPoison;
    private boolean isPlayerTurn = true; // Added to manage turns
    private final int basicPlayerPower = 1;
    private Position whoDied;

    private boolean isBossTurn;
    private int bossAttackCounter = 0;
    private int maxBossHit = 3;

    private String currentBossState = "NORMAL";
    private ArrayList<Position> deathRayPath = new ArrayList<>();
    private int bossTurnCounter = 0;

    private boolean poisonAnimation;



    public CombatModel(int size, Position playerPosition, Position enemyPosition, Position attackPosition,
            int playerHealth, int enemyHealth, int playerStamina, int playerStaminaMax, int playerPower,
            int playerPoisonPower, int enemyPoisonPower, int playerLongRangePower, int enemyLongRangePower,
            int enemyPower, int enemySpeed, String enemyName, boolean enemyPoisoned, boolean isPlayerPoison,
            boolean isPlayerTurn, Position whoDied, boolean isBossTurn, int bossAttackCounter, int maxBossHit,
            String currentBossState, ArrayList<Position> deathRayPath, int bossTurnCounter, boolean poisonAnimation) {
        this.size = size;
        this.playerPosition = playerPosition;
        this.enemyPosition = enemyPosition;
        this.attackPosition = attackPosition;
        this.playerHealth = playerHealth;
        this.enemyHealth = enemyHealth;
        this.playerStamina = playerStamina;
        this.playerStaminaMax = playerStaminaMax;
        this.playerPower = playerPower;
        this.playerPoisonPower = playerPoisonPower;
        this.enemyPoisonPower = enemyPoisonPower;
        this.playerLongRangePower = playerLongRangePower;
        this.enemyLongRangePower = enemyLongRangePower;
        this.enemyPower = enemyPower;
        this.enemySpeed = enemySpeed;
        this.enemyName = enemyName;
        this.enemyPoisoned = enemyPoisoned;
        this.isPlayerPoison = isPlayerPoison;
        this.isPlayerTurn = isPlayerTurn;
        this.whoDied = whoDied;
        this.isBossTurn = isBossTurn;
        this.bossAttackCounter = bossAttackCounter;
        this.maxBossHit = maxBossHit;
        this.currentBossState = currentBossState;
        this.deathRayPath = deathRayPath;
        this.bossTurnCounter = bossTurnCounter;
        this.poisonAnimation = poisonAnimation;
    }

    //getters
    public int getSize() {
        return size;
    }

    public Position getPlayerPosition() {
        return playerPosition;
    }

    public Position getEnemyPosition() {
        return enemyPosition;
    }

    public Position getAttackPosition() {
        return attackPosition;
    }

    public int getPlayerHealth() {
        return playerHealth;
    }

    public int getEnemyHealth() {
        return enemyHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getPlayerPower() {
        return playerPower;
    }

    public int getPlayerPoisonPower() {
        return playerPoisonPower;
    }

    public int getEnemyPower() {
        return enemyPower;
    }

    public int getEnemySpeed() {
        return enemySpeed;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public boolean isEnemyPoisoned() {
        return enemyPoisoned;
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    @Override
    public int getHp() { 
        return playerHealth; 
    }

    @Override
    public int getMaxHP() { 
        return maxHealth; 
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

    public void setPlayerPoisoned(boolean isPoisoned) {
        this.isPlayerPoison = isPoisoned;
    }

    public void clearNossAttackCount(){
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
    

}
