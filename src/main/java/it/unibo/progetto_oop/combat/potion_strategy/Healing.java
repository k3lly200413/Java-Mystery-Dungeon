package it.unibo.progetto_oop.combat.potion_strategy;

import java.io.Serializable;

import it.unibo.progetto_oop.overworld.player.adapter_pattern.PossibleUser;

/**
 * Class representing a potion strategy that heals the player.
 */

public class Healing implements PotionStrategy, Serializable {
    /** The amount of health to restore. */
    private static final int HEAL_AMOUNT = 30;

    /** Applies the healing effect to the @param user. */
    @Override
    public void applyEffect(final PossibleUser user) {
        // Logic to apply the healing effect
        // Restore player's health by healAmount
        user.increasePlayerHealth(HEAL_AMOUNT);
    }
}
