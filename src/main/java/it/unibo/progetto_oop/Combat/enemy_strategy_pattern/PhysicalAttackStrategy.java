package it.unibo.progetto_oop.combat.enemy_strategy_pattern;

import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;

public class PhysicalAttackStrategy implements EnemyAttackStrategy {

    @Override
    public void performAttack(CombatController context) {
        context.performEnemyAttack();
    }
    
}
