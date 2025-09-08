package it.unibo.progetto_oop.combat.potion_strategy;

import it.unibo.progetto_oop.overworld.player.adapter_pattern.PossibleUser;

public class CurePoison implements PotionStrategy {

    @Override
    public final void applyEffect(final PossibleUser user) {
        user.setPlayerPoisoned(false);
    }
}
