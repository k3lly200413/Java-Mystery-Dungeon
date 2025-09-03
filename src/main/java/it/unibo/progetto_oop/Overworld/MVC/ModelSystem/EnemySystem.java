package it.unibo.progetto_oop.Overworld.MVC.ModelSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.CombatTransitionState;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Combat.draw_helper.DrawHelper;

public class EnemySystem {
    private final Player player;

    private final OverworldModel model;

    private List<Enemy> enemies; // enemies present in the map
    private List<Enemy> beatenEnemies = new ArrayList<>();
    private Enemy encounteredEnemy;
    DrawHelper neighboursCheck;

    public EnemySystem(List<Enemy> enemies, Player player, OverworldModel model) {
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.player = Objects.requireNonNull(player, "Player cannot be null");
        this.enemies = enemies;
        this.encounteredEnemy = null;
        this.neighboursCheck = new DrawHelper();
    }

    // getters

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


    // setters

    /** 
     * @param the encountered enemy
     */
    public void setEncounteredEnemy(Enemy encounteredEnemy){
        this.encounteredEnemy = encounteredEnemy;
        if(this.model.isCombatTransitionPending()) {
            CombatTransitionState combat = new CombatTransitionState(encounteredEnemy.getState());
            encounteredEnemy.setState(combat);
            combat.enterState(encounteredEnemy);
            
        }
    }

    /**
     * Set the enemies on the current floor
     * @param enemies the enemies to set
     */
    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }


    // methods 

    /**
     * Check if the player has encountered an enemy at the current position
     * 
     * @return an Optional containing the enemy if found, otherwise an empty Optional
     */
    public Optional<Enemy> checkEnemyHit(Position tempPosition){
        return this.enemies.stream().filter(enemy -> 
            this.neighboursCheck.neighbours(enemy.getCurrentPosition(), tempPosition, 1))
            .findFirst();
    }

    /**
     * Remove an enemy from the list of enemies and add it to the list of beaten enemies
     * @param enemyToRemove
     */
    private void removeEnemy(Enemy enemyToRemove){
        if (this.enemies.contains(enemyToRemove)) {
            Position at = enemyToRemove.getCurrentPosition(); // @autor Alice
            this.enemies.remove(enemyToRemove);
            model.gridNotifier.notifyItemRemoved(at); // @autor Alice
            this.beatenEnemies.add(enemyToRemove);
        }
    }

    /**
     * Trigger the enemy turns, allowing them to take actions
     * This method should be called after the player has moved
     */
    public void triggerEnemyTurns(){
        this.enemies.stream().forEach(enemy -> enemy.takeTurn(this.player));
    }


}
