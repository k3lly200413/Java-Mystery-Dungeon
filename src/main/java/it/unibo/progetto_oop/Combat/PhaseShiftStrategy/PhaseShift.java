package it.unibo.progetto_oop.Combat.PhaseShiftStrategy;

import it.unibo.progetto_oop.Combat.EnemyStrategyPattern.EnemyAttackStrategy;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;

public class PhaseShift implements  EnemyAttackStrategy{

    /**
     * Indicates whether the enemy is enraged.
     */
    private boolean isEnraged;
    /**
     * The combat model associated with the enemy.
     */
    private CombatModel model;
    /**
     * The additional power gained when the enemy becomes enraged.
     */
    private final int power = 10;

    /**
     * Performs an attack using the specified context.
     *
     * @param context the combat controller context
     */
    @Override
    public void performAttack(final CombatController context) {
        this.model = context.getModel();

        if (!this.isEnraged && model.
        getEnemyHealth() <= model.getEnemyHealth() / 2) {

            this.isEnraged = true;
            context.getView().showInfo("Enemy is enraged!");
            context.getModel().increaseEnemyPower(power);
        }
        if (!this.isEnraged) {
            context.performEnemyPhysicalAttack();
        } else {
            context.performEnemySuperAttack();
        }
    }

}
