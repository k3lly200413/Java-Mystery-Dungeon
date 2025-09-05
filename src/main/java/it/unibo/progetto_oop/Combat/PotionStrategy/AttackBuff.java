package it.unibo.progetto_oop.Combat.PotionStrategy;

import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;

public class AttackBuff implements PotionStrategy{

    private final int buffAmount = 50;

    @Override
    public void applyEffect(PossibleUser user) {
        // Logic to apply the attack buff effect
        // Increase player's attack power by buffAmount
        user.increasePlayerPower(buffAmount);
    }
    
}
