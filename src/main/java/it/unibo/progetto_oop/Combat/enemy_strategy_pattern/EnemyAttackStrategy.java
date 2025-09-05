<<<<<<< HEAD
package it.unibo.progetto_oop.combat.enemy_strategy_pattern;

import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;

@FunctionalInterface
public interface EnemyAttackStrategy {

    /**
     * Performs an attack using the specified context.
     *
     * @param context the combat controller context
     */
    void performAttack(CombatController context);
}
=======
package it.unibo.progetto_oop.Combat.enemy_strategy_pattern;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;

public interface  EnemyAttackStrategy {
    public void performAttack(CombatController context);
}
>>>>>>> CombatTransition
