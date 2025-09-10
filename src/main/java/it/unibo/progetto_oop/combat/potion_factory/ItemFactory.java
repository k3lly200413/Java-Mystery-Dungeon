package it.unibo.progetto_oop.combat.potion_factory;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.potion_strategy.AttackBuff;
import it.unibo.progetto_oop.combat.potion_strategy.CurePoison;
import it.unibo.progetto_oop.combat.potion_strategy.Healing;
import it.unibo.progetto_oop.combat.potion_strategy.Potion;
import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * Factory per la creazione di oggetti Item.
 * @author Laura Bertozzi
 */
public class ItemFactory {
    /**
     * Create an item.
     *
     * @param itemId the id of the item
     * @param position the position of the item
     * @return the created item
     */
    public Item createItem(final String itemId, final Position position) {
        if ("Health Potion".equalsIgnoreCase(itemId)) {
            return new Potion(
                "Health Potion",
                "Restores Player Health\nAmount restored: 25",
                position, new Healing());
        } else if ("Antidote".equalsIgnoreCase(itemId)) {
            return new Potion(
                "Antidote", "Cures from poison", position, new CurePoison());
        } else if ("Attack Buff".equalsIgnoreCase(itemId)) {
            return new Potion(
                "Attack Buff",
                "Buffs Attack for rest of encounter\nBuff Amount: +10",
                position, new AttackBuff());
        }
        return null;
    }
}
