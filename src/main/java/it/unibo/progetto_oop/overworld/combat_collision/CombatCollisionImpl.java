package it.unibo.progetto_oop.overworld.combat_collision;

import it.unibo.progetto_oop.combat.draw_helper.DrawHelper;
import it.unibo.progetto_oop.overworld.ViewManagerObserver;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;


public class CombatCollisionImpl implements CombatCollision {
    /**
     * Helper class to check for neighbouring positions.
     */
    private final DrawHelper neighboursCheck;

    /**
     * Flag to indicate if the enemy is currently in combat.
     */
    private boolean inCombat;

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
     */
    public CombatCollisionImpl() {
        this.neighboursCheck = new DrawHelper();
        this.inCombat = false;
    }

    @Override
    public final boolean checkCombatCollision(final Position player,
    final Position enemy) {
        return this.neighboursCheck.neighbours(player, enemy, COMBAT_DISTANCE);
    }

    @Override
    public final void showCombat(final Enemy enemy,
    final Player player) {
        if (!inCombat) {
            inCombat = true;
            this.viewManagerObserver.onPlayerEnemyContact(enemy);
        }
    }

    @Override
    public final void showOverworld() {
        this.viewManagerObserver.onEnemyDefeat();
    }

    @Override
    public final void setInCombat(final boolean inCombatValue) {
        this.inCombat = inCombatValue;
    }

    @Override
    public final void setViewManagerListener(
    final ViewManagerObserver currentViewManagerObserver) {
        this.viewManagerObserver = currentViewManagerObserver;
    }

    @Override
    public final void showGameOver() {
        this.viewManagerObserver.onPlayerDefeat();
    }
}
