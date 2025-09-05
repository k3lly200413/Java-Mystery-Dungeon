<<<<<<< HEAD
package it.unibo.progetto_oop.combat.enemy_strategy_pattern;

import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;

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
=======
package it.unibo.progetto_oop.Combat.enemy_strategy_pattern;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;

public class PhysicalAttackStrategy implements EnemyAttackStrategy {

    @Override
    public void performAttack(CombatController context) {
        context.performEnemyAttack();
    }
    
}
>>>>>>> CombatTransition
