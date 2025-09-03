package it.unibo.progetto_oop.combat.PotionFactory;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.PotionStrategy.AttackBuff;
import it.unibo.progetto_oop.combat.PotionStrategy.CurePoison;
import it.unibo.progetto_oop.combat.PotionStrategy.Healing;
import it.unibo.progetto_oop.combat.PotionStrategy.Potion;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;

/**
 * @author Laura Bertozzi
 */
public class ItemFactory {
    public Item createItem(String itemId, Position position){
        if("Health Potion".equalsIgnoreCase(itemId)){
            return new Potion("Health Potion", "Restores Player Health\nAmount restored: 25", position, new Healing() );
        }
        else if ("Antidote".equalsIgnoreCase(itemId)){
            return new Potion("Antidote", "Cures from poison", position, new CurePoison());
        }
        else if ("Attack Buff".equalsIgnoreCase(itemId)){
            return new Potion("Attack Buff", "Buffs Attack for rest of encounter\nBuff Amount: +10", position, new AttackBuff());
        }
        System.err.println("Warning: Unknown item identifier in factory: " + itemId);
        return null;
    }
}
