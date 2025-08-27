package it.unibo.progetto_oop.Overworld.MVC;

import java.util.List;
import java.util.Set;
import java.util.Optional;

import it.unibo.progetto_oop.Combat.Inventory.Inventory;
import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.MVC.ModelSystem.EnemySystem;
import it.unibo.progetto_oop.Overworld.MVC.ModelSystem.PickupSystem;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Combat.Position.Position;

public class OverworldModel {
    private final Player player;

    // utility systems
    private final PickupSystem pickupSystem;
    private final EnemySystem enemySystem;

    private final Set<Position> walls; // TODO: integrare con Alice

    private Position tempPosition;

    private boolean combatTransitionPending = false;
    private boolean inCombat;

    public OverworldModel(Player player, List<Enemy> enemies, List<Item> items, Set<Position> walls) { // TODO: integrare con Alice
        this.player = player;
        this.walls = walls;
        this.inCombat = false;
        this.pickupSystem = new PickupSystem(items, player);
        this.enemySystem = new EnemySystem(enemies, player, this);
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
        return this.pickupSystem.getItem();
    }


    /**
     * 
     * @return the player's inventory
     */

    public Inventory getInventoryInstance(){
        return this.pickupSystem.getInventoryInstance();
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
        return this.enemySystem.getEnemies();
    }

    /**
     * @return the encountered enemy
     */
    public Enemy getEncounteredEnemy(){
        return this.enemySystem.getEncounteredEnemy();
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
        this.enemySystem.setEncounteredEnemy(encounteredEnemy);
    }

    /**
     * Clear the InCombat flag, indicating that the player is no longer in combat.
     */
    public void clearInCombatFlag(){
        this.inCombat = false;
    }

    /**
     * Set the InCombat flag to true, indicating that the player has entered combat.
     */
    public void setInCombatFlag(){
        this.inCombat = true;
    }


    /**
     * Clear the combat transition flag, indicating that the combat transition is no longer pending.
     */
    public void clearCombatTransitionFlag(){
        this.combatTransitionPending = false;
    }
    
    /**
     * Set the combat transition flag to true, indicating that a combat transition is pending.
    */
    public void setCombatTransitionFlag(){
        this.combatTransitionPending = false;
    }

    // methods

    /**
     * Check if the player has hit a wall at the current position
     * @return true if the player has hit a wall, false otherwise
     */
    private boolean checkWallHit(){
        return this.walls.contains(this.tempPosition);
    }


    /**
     * Move the player checking if it encounters items, enemies or walls.
     * If it encounters an enemy transition to combat
     * 
     * @param directionX direction of movement on axis x
     * @param directionY direction of movement on axis y
     */
    public void movePlayer(int directionX, int directionY){
        Position currentPos = player.getPosition();
        tempPosition = new Position(currentPos.x()+directionX, currentPos.y()+directionY);

        // reset flag and encountered enemy
        this.clearCombatTransitionFlag();
        this.setEncounteredEnemy(null);

        // Check Walls
        if (checkWallHit()) {
            this.tempPosition = player.getPosition();
        }
    
        // Check Enemies
        Optional<Enemy> enemyOpt = this.enemySystem.checkEnemyHit(tempPosition);
        if (enemyOpt.isPresent()) {
            this.setCombatTransitionFlag();
            this.setEncounteredEnemy(enemyOpt.get());
            System.out.println("Enemy encounter flagged at "+tempPosition);
            return; 
        }

        // the player can now change position
        this.player.setPosition(tempPosition);

        // check items
        this.pickupSystem.checkAndAddItem(); 
        
        // trigger enemy turn
        this.enemySystem.triggerEnemyTurns();
    }




}
