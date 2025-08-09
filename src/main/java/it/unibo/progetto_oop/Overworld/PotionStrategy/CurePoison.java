package it.unibo.progetto_oop.Overworld.PotionStrategy;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;
import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;

// The charachter can be poisoned only during combat
public class CurePoison implements PotionEffectStrategy { 

    /**
     * @param user Instance of CombatModel that will be used to apply the effect of curing poison.
     * 
     */
    @Override
    public void apllyEffect(PossibleUser user) {
        if (user instanceof CombatModel){  // Check whether the user is in combat mode
            CombatModel combatUser = (CombatModel) user;
            combatUser.setPlayerPoisoned(false);
        }
        else{
            System.out.println("Input ignored becaused not in combat mode");
        }
    }
    
}
