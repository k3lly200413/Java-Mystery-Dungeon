package it.unibo.progetto_oop.combat.potion_factory;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.potion_strategy.AttackBuff;
import it.unibo.progetto_oop.combat.potion_strategy.CurePoison;
import it.unibo.progetto_oop.combat.potion_strategy.Healing;
import it.unibo.progetto_oop.combat.potion_strategy.Potion;
import it.unibo.progetto_oop.overworld.playGround.data.Position;
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
