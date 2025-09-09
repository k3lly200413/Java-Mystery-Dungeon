package it.unibo.progetto_oop.combat.potion_strategy;

import it.unibo.progetto_oop.overworld.player.adapter_pattern.PossibleUser;

/**
 * Class representing a potion strategy that buffs the player's attack power.
 */

public class AttackBuff implements PotionStrategy {

    /** The amount of attack power to buff. */
    private static final int BUFF_AMOUNT = 50;

    /** Applies the attack buff effect to the @param user. */
    @Override
    public void applyEffect(final PossibleUser user) {
        // Logic to apply the attack buff effect
        // Increase player's attack power by buffAmount
        user.increasePlayerMaxPower(BUFF_AMOUNT);
    }
}
