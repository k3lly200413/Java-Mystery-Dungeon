package it.unibo.progetto_oop.Combat.enemy_strategy_pattern;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;

public class PhysicalAttackStrategy implements EnemyAttackStrategy {

    @Override
    public void performAttack(CombatController context) {
        context.performEnemyAttack();
    }
    
}
