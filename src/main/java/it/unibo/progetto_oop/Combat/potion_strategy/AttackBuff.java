/**
 * Represents a strategy for applying an attack buff potion.
 */
package it.unibo.progetto_oop.combat.potion_strategy;

import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;

public class AttackBuff implements PotionStrategy {

    /** The amount of attack power to buff. */
    private final int buffAmount = 50;

    /** Applies the attack buff effect to the @param user. */
    @Override
    public void applyEffect(final PossibleUser user) {
        // Logic to apply the attack buff effect
        // Increase player's attack power by buffAmount
        user.increasePlayerPower(buffAmount);
    }
}
