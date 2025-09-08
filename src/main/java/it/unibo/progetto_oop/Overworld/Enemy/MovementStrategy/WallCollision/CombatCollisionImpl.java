package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Overworld.ViewManagerObserver.ViewManagerObserver;

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
    public void initiateCombatTransition(final Enemy enemy,
    final Player player) {
        if (!inCombat) {
            inCombat = true;
            this.viewManagerObserver.onPlayerEnemyContact(enemy);
        }
    }

    @Override
    public void setInCombat(final boolean inCombatValue) {
        this.inCombat = inCombatValue;
    }
    
    @Override
    public void setViewManagerListener(final ViewManagerObserver currentViewManagerObserver) {
        this.viewManagerObserver = currentViewManagerObserver;
    }

    @Override
    public void showOverworld() {
        this.viewManagerObserver.onEnemyDefeat();
    }

}
