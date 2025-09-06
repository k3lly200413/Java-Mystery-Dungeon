package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.CombatTransitionState;
import it.unibo.progetto_oop.Overworld.MVC.ViewManager;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.combat.CombatLauncher;
import it.unibo.progetto_oop.combat.draw_helper.DrawHelper;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;

public class CombatCollisionImpl implements CombatCollision{
    private final DrawHelper neighboursCheck;
    private boolean inCombat = false;
    private final Player player;

    private static int COMBAT_DISTANCE = 1; 

    public CombatCollisionImpl(Player player) {
        this.neighboursCheck = new DrawHelper();
        this.player = player;
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

    CombatController combatController = CombatLauncher.buildCombat(player);
        new ViewManager().showCombat(combatController);
    }

    public void setInCombat(boolean inCombat) {
        this.inCombat = inCombat;
    }
}
