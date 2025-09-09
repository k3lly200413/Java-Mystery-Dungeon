package it.unibo.progetto_oop.combat.potion_strategy;

import it.unibo.progetto_oop.overworld.player.adapter_pattern.PossibleUser;

/**
 * Class representing a potion strategy that cures the player's poison status.
 */
public class CurePoison implements PotionStrategy {

    @Override
    public final void applyEffect(final PossibleUser user) {
        user.setPlayerPoisoned(false);
    }
}
