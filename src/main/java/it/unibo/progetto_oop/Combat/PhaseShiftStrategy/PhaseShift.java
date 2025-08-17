package it.unibo.progetto_oop.Combat.PhaseShiftStrategy;

import it.unibo.progetto_oop.Combat.EnemyStrategyPattern.EnemyAttackStrategy;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;

public class PhaseShift implements  EnemyAttackStrategy{

    private boolean isEnraged;
    private CombatModel model;

    @Override
    public void performAttack(CombatController context) {
        this.model = context.getModel();

        if(!this.isEnraged && model.getEnemyHealth() <= model.getEnemyHealth()/2){

            this.isEnraged = true;
            context.getView().showInfo("Enemy is enraged!");
            context.getModel().increaseEnemyPower(10);
        }
        if (!this.isEnraged) {
            // TODO: Change it to where I can use bigger enemies
            context.performEnemyPhysicalAttack();
        } else {
            context.performEnemySuperAttack();
        }
    }
    
}
