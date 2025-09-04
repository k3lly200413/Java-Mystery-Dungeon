package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision;

import it.unibo.progetto_oop.Combat.draw_helper.DrawHelper;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.CombatTransitionState;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;

public class CombatCollision {
    private final DrawHelper neighboursCheck;
    private boolean inCombat = false;

    private static int COMBAT_DISTANCE = 1; 

    public CombatCollision() {
        this.neighboursCheck = new DrawHelper();
    }

    public boolean checkCombatCollision(Position player, Position enemy) {
        return this.neighboursCheck.neighbours(player, enemy, COMBAT_DISTANCE);
    }

    public void initiateCombatTransition(Enemy enemy) {
        if (inCombat == true) {
            return;
        } 
        CombatTransitionState combat = new CombatTransitionState(enemy.getState());
        enemy.setState(combat);
        inCombat = true;
    }
}
