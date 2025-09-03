package it.unibo.progetto_oop.Combat.EnemyStrategyPattern;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;

public class PhysicalAttackStrategy implements EnemyAttackStrategy {

    @Override
    public void performAttack(CombatController context) {
        context.performEnemyAttack();
    }
    
}
