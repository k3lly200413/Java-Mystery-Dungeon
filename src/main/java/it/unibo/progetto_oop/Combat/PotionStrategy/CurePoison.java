package it.unibo.progetto_oop.combat.PotionStrategy;

import it.unibo.progetto_oop.combat.mvc_pattern.CombatModel;
import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;

public class CurePoison implements PotionStrategy {

    @Override
    public final void applyEffect(final PossibleUser user) {
        user.setPlayerPoisoned(false);
    }
}
