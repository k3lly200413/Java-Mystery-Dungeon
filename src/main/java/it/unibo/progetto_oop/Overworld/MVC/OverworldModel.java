package it.unibo.progetto_oop.Overworld.MVC;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Optional;

import it.unibo.progetto_oop.Combat.Inventory.Inventory;
import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Overworld.Enemy.Enemy;
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

    public boolean isInCombat(){
        return this.inCombat;
    }

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

    public void clearInCombatFlag(){
        this.inCombat = false;
    }

    public void setInCombatFlag(){
        this.inCombat = true;
    }

    public void clearCombatTransitionFlag(){
        this.combatTransitionPending = false;
    }
    
    public void setCombatTransitionFlag(){
        this.combatTransitionPending = false;
    }

    // methods

    private Optional<Enemy> checkEnemyHit(){
        return this.enemies.stream().filter(enemy -> enemy.getCurrentPosition().equals(this.tempPosition)).findFirst();
    }

    private void removeEnemy(Enemy enemyToRemove){
        if (this.enemies.contains(enemyToRemove)){
            this.enemies.remove(enemyToRemove);
            this.beatenEnemies.add(enemyToRemove);
        }
    }

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

        // TODO: check items
        // TODO: trigger enemy turn
    }


}
