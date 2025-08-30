package it.unibo.progetto_oop.combat.enemy_strategy_pattern;

import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;

public class LongRangeAttackStrategy implements  EnemyAttackStrategy {
    /**
     * Performs a long-range attack.
     *
     * @param context Instance of the controller
     */
    @Override
    public void performAttack(final CombatController context) {
        throw new UnsupportedOperationException(
            "Unimplemented method 'performAttack'");
    }

}
