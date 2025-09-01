package it.unibo.progetto_oop.Overworld.MVC.ModelSystem;

import java.util.Optional;
import java.util.Set;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class MovementSystem {
    private final Player player;
    private Set<Position> walls; // TODO: integrare con Alice
    private Position tempPosition;
    private final OverworldModel model;

    // flags
    private boolean combatTransitionPending = false;
    


    public MovementSystem(Set<Position> walls, Player player, OverworldModel model) {
        this.player = player;
        this.walls = walls;
        this.model = model;
    }


    // getters

    /**
     * 
     * @return if a combat transition is pending
     */
    public boolean isCombatTransitionPending(){
        return this.combatTransitionPending;
    }

    // setters

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
        this.combatTransitionPending = true;
    }

    /**
     * Set the walls on the current floor
     * @param walls the walls to set
     */
    public void setWalls(Set<Position> walls){
        this.walls = walls;
    }


    // methods
    
    /**
     * Check if the player has hit a wall at the current position
     * @return true if the player has hit a wall, false otherwise
     */
    private boolean checkWallHit(){
        return this.walls.contains(this.tempPosition);
    }

    // i could implement an observer pattern to notify pickupSystem and enemySystem

    /**
     * Move the player checking if it encounters items, enemies or walls.
     * If it encounters an enemy transition to combat
     * 
     * @param directionX direction of movement on axis x
     * @param directionY direction of movement on axis y
     * @param pickupSystem class that handles picking up objects
     * @param enemySystem class that handles encountered enemies
     */
    public void move(int directionX, int directionY, PickupSystem pickupSystem, EnemySystem enemySystem){
        Position currentPos = player.getPosition();
        tempPosition = new Position(currentPos.x()+directionX, currentPos.y()+directionY);

        // reset flag and encountered enemy
        this.clearCombatTransitionFlag();
        enemySystem.setEncounteredEnemy(null);

        // Check Walls
        if (checkWallHit()) {
            this.tempPosition = player.getPosition();
        }
    
        // Check Enemies
        Optional<Enemy> enemyOpt = enemySystem.checkEnemyHit(tempPosition);
        if (enemyOpt.isPresent()) {
            Enemy enemy = enemyOpt.get();

            this.setCombatTransitionFlag();
            
            enemySystem.setEncounteredEnemy(enemy);

            System.out.println("Enemy encounter flagged at "+tempPosition);
            return; 
        }

        Position from = player.getPosition(); // @autor Alice
        // the player can now change position
        this.player.setPosition(tempPosition);
        model.gridNotifier.notifyPlayerMoved(from, tempPosition); // @autor Alice

        // check items
        pickupSystem.checkAndAddItem(); 
        
        // trigger enemy turn
        enemySystem.triggerEnemyTurns();
    }
}
