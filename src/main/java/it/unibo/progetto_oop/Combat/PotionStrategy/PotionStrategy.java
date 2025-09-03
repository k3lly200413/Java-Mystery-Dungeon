package it.unibo.progetto_oop.combat.PotionStrategy;

import it.unibo.progetto_oop.combat.mvc_pattern.CombatModel;
import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;

public interface PotionStrategy {
    /**
     * Applies the potion effect to the user.
     *
     * @param user the CombatModel representing the user of the potion
     */
    void applyEffect(PossibleUser user);
}
