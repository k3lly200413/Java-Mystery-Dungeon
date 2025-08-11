package it.unibo.progetto_oop.Combat.MVC_Pattern;
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
    private int buff;

    private int playerPower;
    private int playerPoisonPower;
    private int enemyPower;
    private int enemySpeed; // If needed for future logic
    private String enemyName; // If needed

    private boolean enemyPoisoned;
    private boolean isPlayerPoison;
    private boolean isPlayerTurn = true; // Added to manage turns

    public CombatModel(int size, int playerPower, int playerPoisonPower,
            int enemyPower, int enemySpeed, String enemyName) {

        this.size = size;
        this.playerPower = playerPower;
        this.buff = 0;
        this.playerPoisonPower = playerPoisonPower;
        this.enemyPower = enemyPower;
        this.enemySpeed = enemySpeed;
        this.enemyName = enemyName;

        this.playerHealth = maxHealth;
        this.enemyHealth = maxHealth;
        this.enemyPoisoned = false;

        //set start positions
        this.playerPosition = new Position((this.size / 3) - 2, (this.size / 2));  // Player starts on the left side
        this.enemyPosition = new Position(this.size - ((this.size / 3) - 1), (this.size / 2));  // Enemy starts on the right side
        this.attackPosition = new Position(0, 0);
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
        this.buff = power; // the increased power is stored in buff
    }

    public void decreasePlayerHealth(int amount) {
        this.playerHealth = Math.max(0, this.playerHealth - amount);  //only decrease health, do not allow negative values
    }

    public void decreaseEnemyHealth(int amount) {
        this.enemyHealth = Math.max(0, this.enemyHealth - amount);    //only decrease health, do not allow negative values
    }
    
    public boolean isGameOver() {
        return playerHealth <= 0 || enemyHealth <= 0;
    }

    public void setPlayerPoisoned(boolean isPoisoned) {
        this.isPlayerPoison = true;
    }

}
