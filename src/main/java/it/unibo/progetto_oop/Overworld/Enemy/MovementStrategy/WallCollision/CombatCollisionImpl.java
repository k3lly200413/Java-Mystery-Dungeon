package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.CombatTransitionState;

import it.unibo.progetto_oop.Overworld.GridNotifier.GridNotifier;
import it.unibo.progetto_oop.Overworld.MVC.ViewManager;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Overworld.ViewManagerObserver.ViewManagerObserver;
import it.unibo.progetto_oop.combat.CombatLauncher;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;

import it.unibo.progetto_oop.combat.draw_helper.DrawHelper;


public class CombatCollisionImpl implements CombatCollision {
    /**
     * Helper class to check for neighbouring positions.
     */
    private final DrawHelper neighboursCheck;

    /**
     * Flag to indicate if the enemy is currently in combat.
     */
    private boolean inCombat = false;

    /**
     * Grid notifier to notify the grid of changes.
     */
    private GridNotifier gridNotifier;
    private ViewManagerObserver viewManagerObserver;

    /**
     * The distance within which combat is initiated.
     */
    private static final int COMBAT_DISTANCE = 1;

    public CombatCollisionImpl(GridNotifier gridNotifier) {
        this.neighboursCheck = new DrawHelper();
        this.gridNotifier = gridNotifier;
    }

    /**
     * Check if the player is close enough to the enemy.
     * @param player the position of the player
     * @param enemy the position of the enemy
     * @return true if the player is close enough to the enemy, false otherwise
     */
    public boolean checkCombatCollision(final Position player,
    final Position enemy) {
        return this.neighboursCheck.neighbours(player, enemy, COMBAT_DISTANCE);
    }

    /**
     * Initiate the combat transition between the player and the enemy.
     * @param enemy the enemy that will enter combat
     * @param player the player that will enter combat
     */
    public void initiateCombatTransition(final Enemy enemy,
    final Player player) {
        if (!inCombat) {
            // CombatTransitionState combat =
            //new CombatTransitionState(enemy.getState());
            // enemy.setState(combat);
            inCombat = true;

        this.viewManagerObserver.onPlayerEnemyContact(enemy);
        CombatController combatController =
            CombatLauncher.buildCombat(player, this, gridNotifier, enemy);
            new ViewManager().showCombat(combatController);
        }
    }

    /**
     * Set the inCombat flag.
     * @param inCombatValue true if the enemy is in combat, false otherwise
     */
    public void setInCombat(final boolean inCombatValue) {
        this.inCombat = inCombatValue;
    }
    
    public void setViewManagerListener(ViewManagerObserver curranteViewManagerObserver) {
        this.viewManagerObserver = curranteViewManagerObserver;
    }

    public void showOverworld() {
        this.viewManagerObserver.onEnemyDefeat();
    }

}
