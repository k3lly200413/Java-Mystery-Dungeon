package it.unibo.progetto_oop.Overworld.PotionStrategy;

import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;

public class AttackBuff implements PotionEffectStrategy {
    
    private final int power = 1; // Could add a method to personalize this value
    
    /**
     * @param user Instance of CombatModel/ OverworldPlayerAdapter
     */
    public void apllyEffect(PossibleUser user) {
            user.increasePlayerPower(this.power);
            System.out.println("Buffered attack");
    }
    
}
