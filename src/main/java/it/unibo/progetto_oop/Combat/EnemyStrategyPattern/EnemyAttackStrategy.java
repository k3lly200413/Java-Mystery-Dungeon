package it.unibo.progetto_oop.Combat.EnemyStrategyPattern;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;

public interface  EnemyAttackStrategy {

    /**
     * Performs an attack using the specified context.
     *
     * @param context the combat controller context
     */
    void performAttack(CombatController context);
}
