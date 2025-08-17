package it.unibo.progetto_oop.Combat.PotionStrategy;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;

public interface PotionStrategy {
    /**
     * Applies the potion effect to the user.
     *
     * @param user the CombatModel representing the user of the potion
     */
    void applyEffect(CombatModel user);
}
