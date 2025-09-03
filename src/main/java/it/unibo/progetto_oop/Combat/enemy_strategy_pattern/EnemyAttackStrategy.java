package it.unibo.progetto_oop.Combat.enemy_strategy_pattern;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;

public interface  EnemyAttackStrategy {
    public void performAttack(CombatController context);
}
