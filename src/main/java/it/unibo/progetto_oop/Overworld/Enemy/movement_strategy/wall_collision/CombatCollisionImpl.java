package it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision;

import it.unibo.progetto_oop.combat.draw_helper.DrawHelper;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.view_manager_observer.ViewManagerObserver;


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
     * Observer to manage the view transition.
     */
    private ViewManagerObserver viewManagerObserver;

    /**
     * The distance within which combat is initiated.
     */
    private static final int COMBAT_DISTANCE = 1;

    /**
     * Constructor for CombatCollisionImpl.
     * @param newGridNotifier the grid notifier
     */
    public CombatCollisionImpl() {
        this.neighboursCheck = new DrawHelper();
    }
    
    @Override
    public boolean checkCombatCollision(final Position player,
    final Position enemy) {
        return this.neighboursCheck.neighbours(player, enemy, COMBAT_DISTANCE);
    }

    @Override
    public void showCombat(final Enemy enemy,
    final Player player) {
        if (!inCombat) {
            inCombat = true;
            this.viewManagerObserver.onPlayerEnemyContact(enemy);
        }
    }

    @Override
    public void showOverworld() {
        this.viewManagerObserver.onEnemyDefeat();
    }

    @Override
    public void setInCombat(final boolean inCombatValue) {
        this.inCombat = inCombatValue;
    }
    
    @Override
    public void setViewManagerListener(final ViewManagerObserver currentViewManagerObserver) {
        this.viewManagerObserver = currentViewManagerObserver;
    }
}
