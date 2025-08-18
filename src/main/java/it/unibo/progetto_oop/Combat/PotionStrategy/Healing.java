package it.unibo.progetto_oop.Combat.PotionStrategy;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;

public class Healing implements PotionStrategy {
    private final int healAmount = 30;

    @Override
    public void applyEffect(CombatModel user) {
        // Logic to apply the healing effect
        // Restore player's health by healAmount
        user.increasePlayerHealth(healAmount);
    }
}
