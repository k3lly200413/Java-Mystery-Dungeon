package it.unibo.progetto_oop.Overworld.MVC;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Optional;

import it.unibo.progetto_oop.Combat.Inventory.Inventory;
import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Combat.Position.Position;

public class OverworldModel {
    private final Player player;
    private final OverworldApplication game;

    private List<Item> items = new ArrayList<>();
    private Inventory inventory;

    private final Set<Position> walls;

    private List<Enemy> enemies;
    private List<Enemy> beatenEnemies = new ArrayList<>();
    private Enemy encounteredEnemy;

    private Position tempPosition;

    private boolean combatTransitionPending = false;
    private boolean inCombat;

    public OverworldModel(Player player, List<Enemy> enemies,  List<Item> items, Set<Position> walls, OverworldApplication game, Inventory inventory) {
        this.player = player;
        this.items = items;
        this.inventory = inventory;
        this.walls = walls;
        this.enemies = enemies;
        this.inCombat = false;
        this.game = game;
    }

    // --- getter methods ---

    /**
     * 
     * @return Posizione del giocatore
     */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * 
     * @return the list of items in the overworld
     */
    public List<Item> getItem(){
        return this.items;
    }


    /**
     * 
     * @return the player's inventory
     */

    public Inventory getInventoryInstance(){
        return this.inventory;
    }

    /**
     * 
     * @return set of walls
     */
    public Set<Position> getWalls(){
        return this.walls;
    }

    /**
     * 
     * @return list of enemies in the map
     */
    public List<Enemy> getEnemies(){
        return this.enemies;
    }

    /**
     * @return the encountered enemy
     */
    public Enemy getEncounteredEnemy(){
        return this.encounteredEnemy;
    }

    /**
     * 
     * @return if the player is in combat
     */
    public boolean isInCombat(){
        return this.inCombat;
    }

    /**
     * 
     * @return if a combat transition is pending
     */
    public boolean isCombatTransitionPending(){
        return this.combatTransitionPending;
    }

    // setters

    /** 
     * @param the encountered enemy
     */
    public void setEncounteredEnemy(Enemy encounteredEnemy){
        this.encounteredEnemy = encounteredEnemy;
    }

    /**
     * Clear the InCombat flag, indicating that the player is no longer in combat.
     */
    private void clearInCombatFlag(){
        this.inCombat = false;
    }

    /**
     * Set the InCombat flag to true, indicating that the player has entered combat.
     */
    private void setInCombatFlag(){
        this.inCombat = true;
    }

    /**
     * Clear the combat transition flag, indicating that the combat transition is no longer pending.
     */
    private void clearCombatTransitionFlag(){
        this.combatTransitionPending = false;
    }
    
    /**
     * Set the combat transition flag to true, indicating that a combat transition is pending.
    */
    private void setCombatTransitionFlag(){
        this.combatTransitionPending = false;
    }

    // methods

    /**
     * Check if the player has encountered an enemy at the current position
     * 
     * @return an Optional containing the enemy if found, otherwise an empty Optional
     */
    private Optional<Enemy> checkEnemyHit(){
        return this.enemies.stream().filter(enemy -> enemy.getCurrentPosition().equals(this.tempPosition)).findFirst();
    }

    /**
     * Check if the player has hit a wall at the current position
     * @return true if the player has hit a wall, false otherwise
     */
    private boolean checkWallHit(){
        return this.walls.contains(this.tempPosition);
    }

    /**
     * Remove an enemy from the list of enemies and add it to the list of beaten enemies
     * @param enemyToRemove
     */
    private void removeEnemy(Enemy enemyToRemove){
        if (this.enemies.contains(enemyToRemove)){
            this.enemies.remove(enemyToRemove);
            this.beatenEnemies.add(enemyToRemove);
        }
    }

    /**
     * Remove an item from the overworld and add it to the player's inventory
     * 
     * @param item the item to remove
     */
    private void removeItem(Item itemToRemove){
        this.inventory.addItem(itemToRemove);
        this.items.remove(itemToRemove);
    }

    /**
     * Trigger the enemy turns, allowing them to take actions
     * This method should be called after the player has moved
     */
    private void triggerEnemyTurns(){
        this.enemies.stream().forEach(enemy -> enemy.takeTurn(this, this.player));
    }

    /**
     * Check if an item is found at the player's position
     * @return an Optional containing the item if found, otherwise an empty Optional
     */
    private Optional<Item> ItemFoundAtPlayerPosition(){
        return this.items.stream().filter(
            item -> item.getPosition().equals(this.player.getPosition())
            ).findFirst();
    }

    /**
     * Check if an item is found at the player's position and add it to the inventory
     * If an item is found, remove it from the overworld items list
     */
    private void checkAndAddItem(){
        Optional<Item> itemOpt = this.ItemFoundAtPlayerPosition();

        itemOpt.ifPresent(item -> {
            System.out.println("Item found, picking it up "+item.getName());
            this.removeItem(item);
            this.inventory.printInventory();
        });
    }

    /**
     * Move the player checking if it encounters items, enemies or walls.
     * If it encounters an enemy transition to combat
     * 
     * @param directionX direction of movement on axis x
     * @param directionY direction of movement on axis y
     */
    public void MovePlayer(int directionX, int directionY){
        Position currentPos = player.getPosition();
        tempPosition = new Position(currentPos.x()+directionX, currentPos.y()+directionY);

        // reset flag and encountered enemy
        this.clearCombatTransitionFlag();
        this.encounteredEnemy = null;

        // Check Walls
        if (checkWallHit()) {
            this.tempPosition = player.getPosition();
        }
    
        // Check Enemies
        Optional<Enemy> enemyOpt = checkEnemyHit();
        if (enemyOpt.isPresent()) {
            this.setCombatTransitionFlag();
            this.encounteredEnemy = enemyOpt.get();
            System.out.println("Enemy encounter flagged at "+tempPosition);
            return; 
        }

        // the player can now change position
        this.player.setPosition(tempPosition);

        // check items
        this.checkAndAddItem(); 
        
        // trigger enemy turn
        this.triggerEnemyTurns();
    }




}
