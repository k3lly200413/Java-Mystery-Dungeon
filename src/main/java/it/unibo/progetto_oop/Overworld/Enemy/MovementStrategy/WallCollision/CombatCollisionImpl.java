package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.CombatTransitionState;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Overworld.ViewManagerObserver.ViewManagerObserver;

import it.unibo.progetto_oop.combat.draw_helper.DrawHelper;


public class CombatCollisionImpl implements CombatCollision{
    private final DrawHelper neighboursCheck;
    private boolean inCombat = false;
    private ViewManagerObserver viewManagerObserver;

    private static int COMBAT_DISTANCE = 1;

    public CombatCollisionImpl() {
        this.neighboursCheck = new DrawHelper();
    }

    public boolean checkCombatCollision(Position player, Position enemy) {
        return this.neighboursCheck.neighbours(player, enemy, COMBAT_DISTANCE);
    }

    public void initiateCombatTransition(Enemy enemy, Player player) {
        if (inCombat == true) {
            return;
        } 
        CombatTransitionState combat = new CombatTransitionState(enemy.getState());
        enemy.setState(combat);
        inCombat = true;

        this.viewManagerObserver.onPlayerEnemyContact(enemy);
    }

    public void setInCombat(boolean inCombat) {
        this.inCombat = inCombat;
    }
    
    public void setViewManagerListener(ViewManagerObserver curranteViewManagerObserver) {
        this.viewManagerObserver = curranteViewManagerObserver;
    }

    public void showOverworld() {
        this.viewManagerObserver.onEnemyDefeat();
    }

}
