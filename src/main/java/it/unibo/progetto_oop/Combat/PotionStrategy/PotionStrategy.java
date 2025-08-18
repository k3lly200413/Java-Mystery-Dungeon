package it.unibo.progetto_oop.Combat.PotionStrategy;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;

public interface PotionStrategy {
    void applyEffect(CombatModel user);
}
