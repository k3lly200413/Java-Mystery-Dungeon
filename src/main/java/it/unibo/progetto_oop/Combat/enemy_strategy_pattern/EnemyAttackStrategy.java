package it.unibo.progetto_oop.combat.enemy_strategy_pattern;

import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;

public interface  EnemyAttackStrategy {
    public void performAttack(CombatController context);
}
