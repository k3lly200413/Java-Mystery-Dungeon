package it.unibo.progetto_oop.combat.potion_strategy;

import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;

public class Healing implements PotionStrategy {
    /** The amount of health to restore. */
    private final int healAmount = 30;

    /** Applies the healing effect to the @param user. */
    @Override
    public void applyEffect(final PossibleUser user) {
        // Logic to apply the healing effect
        // Restore player's health by healAmount
        user.increasePlayerHealth(healAmount);
    }
}
