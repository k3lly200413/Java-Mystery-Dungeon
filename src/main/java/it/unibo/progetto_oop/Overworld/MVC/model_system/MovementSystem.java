package it.unibo.progetto_oop.Overworld.mvc.model_system;

import java.util.Objects;
import java.util.Optional;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;
import it.unibo.progetto_oop.Overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.Overworld.mvc.OverworldModel;
import it.unibo.progetto_oop.Overworld.player.Player;

public class MovementSystem {
    /**
     * the player instance.
     */
    private final Player player;

    /**
     * the model instance.
     */
    private final OverworldModel model;

    // ---- flags ---- //

    /**
     * if true a combat transition is pending.
     */
    private boolean combatTransitionPending = false;

    /**
     * Constructor for the MovementSystem.
     * @param newPlayer the player instance
     * @param newModel the model instance
     */
    public MovementSystem(final Player newPlayer,
    final OverworldModel newModel) {
        this.model = Objects.requireNonNull(newModel, "Model cannot be null");
        this.player = Objects.requireNonNull(newPlayer,
            "Player cannot be null");
    }

    // ---- GETTERS ---- //

    /**
     * @return if a combat transition is pending.
     */
    public boolean isCombatTransitionPending() {
        return this.combatTransitionPending;
    }

    // ---- SETTERS ---- //

    /**
     * Clear the combat transition flag,
     * indicating that the combat transition is no longer pending.
     */
    public void clearCombatTransitionFlag() {
        this.combatTransitionPending = false;
    }

    /**
     * Set the combat transition flag to true,
     * indicating that a combat transition is pending.
     */
    public void setCombatTransitionFlag() {
        this.combatTransitionPending = true;
    }

    // ---- METHODS ---- //

    /**
     * Move the player checking if it encounters items, enemies or walls.
     * If it encounters an enemy transition to combat
     * @param directionX direction of movement on axis x
     * @param directionY direction of movement on axis y
     * @param pickupSystem class that handles picking up objects
     * @param enemySystem class that handles encountered enemies
     */
    public void move(final int directionX, final int directionY,
    final PickupSystem pickupSystem, final EnemySystem enemySystem) {
        Position currentPos = player.getPosition();
        Position tempPosition = new Position(currentPos.x() + directionX,
            currentPos.y() + directionY);

        // reset flag and encountered enemy
        this.clearCombatTransitionFlag();
        enemySystem.setEncounteredEnemy(null);

        // Check Walls
        if (!this.model.getWallCollision().canEnter(tempPosition)) {
            System.out.println("Wall hit");
            return;
        }

        // the player can now change position
        this.player.setPosition(tempPosition);

        if (model.getBaseGridView().
            get(tempPosition.x(), tempPosition.y()) == TileType.STAIRS) {
            model.getGridNotifier().notifyPlayerMoved(currentPos, tempPosition);
            model.nextFloor();
            System.out.println("floor changed");
            return; // no pickup/enemy turn on old floor
        }
        model.getGridNotifier().notifyPlayerMoved(currentPos, tempPosition);

        // check items
        pickupSystem.checkAndAddItem();

        // Check Enemies
        Optional<Enemy> enemyOpt = enemySystem.checkEnemyHit(tempPosition);
        if (enemyOpt.isPresent()) {
            this.setCombatTransitionFlag();
            enemySystem.setEncounteredEnemy(enemyOpt.get());
            System.out.println("Enemy encounter flagged at " + tempPosition);
            return;
        }

        // trigger enemy turn
        enemySystem.triggerEnemyTurns();
    }

}
