package it.unibo.progetto_oop.Overworld.PotionStrategy;

import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;

public class Healing implements PotionEffectStrategy{
    private final int healingAmount;
    /**
     * 
     * @param healingAmount amount of health to restore
     * 
     */
    public Healing(int healingAmount){
        this.healingAmount = healingAmount;
    }
    /**
     * @param user Instance of CombatModel/ OverworldPlayerAdapter that will be used to apply the effect of healing.
     */
    @Override
    public void apllyEffect(PossibleUser user) {
        
        user.increasePlayerHealth(healingAmount);
    }
}
