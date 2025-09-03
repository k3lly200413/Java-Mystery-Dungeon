package it.unibo.progetto_oop.Combat.PotionStrategy;

import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;

public class Healing implements PotionStrategy {
    private final int healAmount = 30;

    @Override
    public void applyEffect(PossibleUser user) {
        // Logic to apply the healing effect
        // Restore player's health by healAmount
        user.increasePlayerHealth(healAmount);
    }
}
