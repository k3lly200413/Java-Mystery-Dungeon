package it.unibo.progetto_oop.combat.potion_strategy;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;
import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;

/**
 * Class representing a potion strategy that buffs the player's attack power.
 */
public class AttackBuff implements PotionStrategy {

    /** The amount of attack power to buff. */
    private static final int BUFF_AMOUNT = 50;

    @Override
    public void applyEffect(PossibleUser user) {
        // Logic to apply the attack buff effect
        // Increase player's attack power by buffAmount
        user.increasePlayerPower(BUFF_AMOUNT);
    }
    
}
