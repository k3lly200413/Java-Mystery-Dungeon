package it.unibo.progetto_oop.overworld.mvc.model_system;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import it.unibo.progetto_oop.overworld.playGround.Data.Position;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.mvc.OverworldModel;
import it.unibo.progetto_oop.overworld.player.Player;

public class EnemySystem {
    /**
     * the player instance.
     */
    private final Player player;


    /**
     * the model instance.
     */
    private final OverworldModel model;

    /**
     * the list of enemies present in the map.
     */
    private List<Enemy> enemies;

    /**
     * the enemy that the player has encountered.
     */
    private Enemy encounteredEnemy;

    /**
     * Constructor for the EnemySystem.
     * @param newEnemies the list of enemies present in the map
     * @param newPlayer the player instance
     * @param newModel the model instance
     */
    public EnemySystem(final List<Enemy> newEnemies,
    final Player newPlayer, final OverworldModel newModel) {
        this.model = Objects.requireNonNull(
            newModel, "Model cannot be null");
        this.player = Objects.requireNonNull(
                newPlayer, "Player cannot be null");
        this.enemies = Objects.requireNonNull(
                newEnemies, "Enemies cannot be null");
        this.encounteredEnemy = null;
    }

    // getters

    /**
     * @return list of enemies in the map
     */
    public List<Enemy> getEnemies() {
        return this.enemies;
    }

    /**
     * @return the encountered enemy
     */
    public Enemy getEncounteredEnemy() {
        return this.encounteredEnemy;
    }


    // setters

    /**
     * Set the encountered enemy.
     * @param newEncounteredEnemy the encountered enemy
     */
    public void setEncounteredEnemy(final Enemy newEncounteredEnemy) {
        this.encounteredEnemy = newEncounteredEnemy;
        if (this.model.isCombatTransitionPending()) {
            this.model.getCombatCollision().
                showCombat(newEncounteredEnemy, this.player);
        }
    }

    /**
     * Set the enemies on the current floor.
     * @param newEnemies the enemies to set
     */
    public void setEnemies(final List<Enemy> newEnemies) {
        this.enemies = newEnemies;
    }


    // ----methods---- //

    /**
     * Check if the player has encountered an enemy at the current position.
     * @param tempPosition the position to check for enemy encounter
     * @return an Optional containing the enemy if found,
     * otherwise an empty Optional
     */
    public Optional<Enemy> checkEnemyHit(final Position tempPosition) {
        return this.enemies.stream().filter(enemy ->
            this.model.getCombatCollision().checkCombatCollision(
            enemy.getCurrentPosition(), tempPosition))
            .findFirst();
    }

    /**
     * Remove an enemy from the list of enemies and
     * add it to the list of beaten enemies.
     * @param enemyToRemove
     * @return true if the enemy was removed, false otherwise
     */
    public boolean removeEnemyAt(final Position enemyToRemove) {
        return this.enemies.removeIf(enemy ->
            enemy.getCurrentPosition().equals(enemyToRemove));
    }

    /**
     * Trigger the enemy turns, allowing them to take actions.
     * This method should be called after the player has moved
     */
    public void triggerEnemyTurns() {
        this.enemies.stream().forEach(enemy -> enemy.takeTurn(this.player));
    }


}
