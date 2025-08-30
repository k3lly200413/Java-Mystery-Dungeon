/**
 * Strategy pattern used for enemy attack behaviors.
 */
package it.unibo.progetto_oop.combat.enemy_strategy_pattern;

import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;

public interface  EnemyAttackStrategy {

    /**
     * Performs an attack using the specified context.
     *
     * @param context the combat controller context
     */
    void performAttack(CombatController context);
}
