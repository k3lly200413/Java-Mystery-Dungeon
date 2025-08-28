package it.unibo.progetto_oop.Combat.PotionStrategy;

import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;

public class CurePoison implements PotionStrategy {

    @Override
    public final void applyEffect(final PossibleUser user) {
        user.setPlayerPoisoned(false);
    }
}
