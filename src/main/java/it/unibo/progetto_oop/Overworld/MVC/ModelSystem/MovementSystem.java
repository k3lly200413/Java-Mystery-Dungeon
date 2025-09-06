package it.unibo.progetto_oop.Overworld.MVC.ModelSystem;

import java.util.Objects;
import java.util.Optional;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class MovementSystem {
    private final Player player;
    private final OverworldModel model;

    // flags
    private boolean combatTransitionPending = false;
    
    public MovementSystem(Player player, OverworldModel model) {
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.player = Objects.requireNonNull(player, "Player cannot be null");
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

    // methods

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
        Position tempPosition = new Position(currentPos.x() + directionX, currentPos.y() + directionY);

        // reset flag and encountered enemy
        this.clearCombatTransitionFlag();
        enemySystem.setEncounteredEnemy(null);

        // Check Walls
        if (!this.model.getWallCollision().canEnter(tempPosition)) {
            System.out.println("Wall hit");
            return;
        }

        // Check Enemies
        Optional<Enemy> enemyOpt = enemySystem.checkEnemyHit(tempPosition);
        if (enemyOpt.isPresent()) {
            this.setCombatTransitionFlag();
            enemySystem.setEncounteredEnemy(enemyOpt.get());
            System.out.println("Enemy encounter flagged at " + tempPosition);
            return;
        }

        // the player can now change position
        this.player.setPosition(tempPosition);

        if (model.getBaseGridView().get(tempPosition.x(), tempPosition.y()) == TileType.STAIRS) {
            model.getGridNotifier().notifyPlayerMoved(currentPos, tempPosition);
            model.nextFloor();
            System.out.println("floor changed");
            return; // no pickup/enemy turn on old floor
        }

        model.getGridNotifier().notifyPlayerMoved(currentPos, tempPosition);

        // check items
        pickupSystem.checkAndAddItem();

        // trigger enemy turn
        enemySystem.triggerEnemyTurns();
    }

}
