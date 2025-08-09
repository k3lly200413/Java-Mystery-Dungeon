package it.unibo.progetto_oop.Overworld.PotionStrategy;

import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;

public interface PotionEffectStrategy {
    /**
     * @param user Istance of CombatModel to call necessary functions to change player details
     */
    void apllyEffect(PossibleUser user);
}

