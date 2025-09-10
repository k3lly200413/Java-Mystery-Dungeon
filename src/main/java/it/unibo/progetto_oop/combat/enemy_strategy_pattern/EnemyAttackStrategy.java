package it.unibo.progetto_oop.combat.enemy_strategy_pattern;

import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;

/**
 * Strategy interface for enemy attack behaviors.
 */
@FunctionalInterface
public interface EnemyAttackStrategy {

    /**
     * Performs an attack using the specified context.
     *
     * @param context the combat controller context
     */
    void performAttack(CombatController context);
}
