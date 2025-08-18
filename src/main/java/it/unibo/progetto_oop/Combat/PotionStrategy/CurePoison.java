package it.unibo.progetto_oop.Combat.PotionStrategy;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;

public class CurePoison implements PotionStrategy {

    @Override
    public final void applyEffect(final CombatModel user) {
        user.setPlayerPoisoned(true);
    }
}
