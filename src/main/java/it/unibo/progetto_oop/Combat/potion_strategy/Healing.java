package it.unibo.progetto_oop.combat.potion_strategy;

import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;

public class Healing implements PotionStrategy {
    /** The amount of health to restore. */
    private static final int HEAL_AMOUNT = 30;

    @Override
    public void applyEffect(PossibleUser user) {
        // Logic to apply the healing effect
        // Restore player's health by healAmount
        user.increasePlayerHealth(HEAL_AMOUNT);
    }
}
