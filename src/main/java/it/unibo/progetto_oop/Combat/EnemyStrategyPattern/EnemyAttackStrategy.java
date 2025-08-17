package it.unibo.progetto_oop.Combat.EnemyStrategyPattern;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;

public interface  EnemyAttackStrategy {
    public void performAttack(CombatController context);
}
