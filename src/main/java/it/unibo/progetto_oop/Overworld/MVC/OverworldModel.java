package it.unibo.progetto_oop.Overworld.MVC;

import java.util.List;
import java.util.Set;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.MVC.ModelSystem.EnemySystem;
import it.unibo.progetto_oop.Overworld.MVC.ModelSystem.MovementSystem;
import it.unibo.progetto_oop.Overworld.MVC.ModelSystem.PickupSystem;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.GridUpdater;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.combat.Inventory.Inventory;
import it.unibo.progetto_oop.combat.Inventory.Item;
import it.unibo.progetto_oop.combat.position.Position;

public class OverworldModel {
    private final Player player;

    // utility systems
    private final PickupSystem pickupSystem;
    private final EnemySystem enemySystem;
    private final MovementSystem movementSystem;

    private Set<Position> walls; // TODO: integrare con Alice

    // flags
    private boolean inCombat;

    // per accedere alla griglia
    private GridUpdater grid;

    public OverworldModel(Player player, List<Enemy> enemies, List<Item> items, Set<Position> walls) { // TODO: integrare con Alice
        this.player = player;
        this.inCombat = false;

        // initialize floor spawn objects 
        this.walls = null;
        this.pickupSystem = new PickupSystem(null, player, this);
        this.enemySystem = new EnemySystem(null, player, this);
        this.movementSystem = new MovementSystem(null, player, this);

        // fill floor spawn objects
        this.setSpawnObjects(enemies, items, walls);
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
        return this.movementSystem.isCombatTransitionPending();
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
        this.movementSystem.clearCombatTransitionFlag();
    }
    
    /**
     * Set the combat transition flag to true, indicating that a combat transition is pending.
    */
    public void setCombatTransitionFlag(){
        this.movementSystem.setCombatTransitionFlag();
    }

    /**
     * this method is called each time the player changes floor so that the model can update accordingly.
     * @param enemies the enemies on the current floor
     * @param items the items on the current floor
     * @param walls the walls one the current floor
     */
    private void setSpawnObjects(List<Enemy> enemies, List<Item> items, Set<Position> walls) {
        this.walls = walls;
        this.pickupSystem.setItems(items);
        this.enemySystem.setEnemies(enemies);
        this.movementSystem.setWalls(this.walls);
        
    }

    // methods


    /**
     * Move the player checking if it encounters items, enemies or walls.
     * If it encounters an enemy transition to combat
     * 
     * @param directionX direction of movement on axis x
     * @param directionY direction of movement on axis y
     */
    public void movePlayer(int directionX, int directionY){
        this.movementSystem.move(directionX, directionY, this.pickupSystem, this.enemySystem);
    }



    // @autor Alice
    public void setGridUpdater(GridUpdater grid) {
        this.grid = grid;
    }
    public void notifyPlayerMoved(Position from, Position to) {
        if (grid != null) grid.onPlayerMove(from, to);
    }
    public void notifyEnemyMoved(Position from, Position to) {
        if (grid != null) grid.onEnemyMove(from, to);
    }
    public void notifyItemRemoved(Position at) {
        if (grid != null) grid.onItemRemoved(at);
    }
    public void notifyEnemyRemoved(Position at) {
        if (grid != null) grid.onEnemyRemoved(at);
    }

}
