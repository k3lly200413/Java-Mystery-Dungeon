package it.unibo.progetto_oop.Combat.EnemyStrategyPattern;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;

public class PhysicalAttackStrategy implements EnemyAttackStrategy {

    /**
     * Performs a physical attack using the specified context.
     *
     * @param context the combat controller context
     */
    @Override
    public void performAttack(final CombatController context) {
        context.performEnemyAttack();
    }

}
