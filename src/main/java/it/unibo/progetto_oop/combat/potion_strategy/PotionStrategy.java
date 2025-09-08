package it.unibo.progetto_oop.combat.potion_strategy;

import it.unibo.progetto_oop.Overworld.player.adapter_pattern.PossibleUser;

@FunctionalInterface
public interface PotionStrategy {
    /**
     * Applies the potion effect to the user.
     *
     * @param user the CombatModel representing the user of the potion
     */
    void applyEffect(PossibleUser user);
}
