package it.unibo.progetto_oop.combat.inventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.combat.potion_factory.ItemFactory;
import it.unibo.progetto_oop.overworld.playground.data.Position;



class InventoryTest {

    /**
     * health potion mock.
     */
    private Item health;

    /**
     * attack buff mock.
     */
    private Item attack;

    /**
     * antidote mock.
     */
    private Item antidote;

    /**
     * the inventory under test.
     */
    private Inventory inventory;

    @BeforeEach
    void setup() {
        inventory = new Inventory(2); // capacity of 2 different items

        final ItemFactory itemFactory = new ItemFactory();
        health = itemFactory.createItem("Health Potion", new Position(0, 0));
        attack = itemFactory.createItem("Attack Buff", new Position(1, 0));
        antidote = itemFactory.createItem("Antidote", new Position(0, 1));
    }


    @Test
    void addItemTest() {
        // Add first item
        final boolean added1 = inventory.addItem(health);
        assertEquals(true, added1);
        assertEquals(1, inventory.getCurrentSize());

        // Add second item
        final boolean added2 = inventory.addItem(attack);
        assertEquals(true, added2);
        assertEquals(2, inventory.getCurrentSize());

        // Try to add third different item - should fail due to capacity
        final boolean added3 = inventory.addItem(antidote);
        assertEquals(false, added3);
        assertEquals(2, inventory.getCurrentSize());

        // Add another of the first item - should succeed
        final boolean added4 = inventory.addItem(health);
        assertEquals(true, added4);
        assertEquals(2, inventory.getCurrentSize());
        // size remains 2 since it's the same item

        // Decrease count of first item
        final boolean decreased1 = inventory.decreaseItemCount(health);
        assertEquals(true, decreased1);
        assertEquals(2, inventory.getCurrentSize()); // size remains 2

        // Decrease count of first item again - should remove it completely
        final boolean decreased2 = inventory.decreaseItemCount(health);
        assertEquals(true, decreased2);
        assertEquals(1, inventory.getCurrentSize()); // size decreases to 1

        // Now we can add the third item since there's space
        final boolean added5 = inventory.addItem(antidote);
        assertEquals(true, added5);
        assertEquals(2, inventory.getCurrentSize());
    }


    @Test
    void decreaseItemCountTest() {
        inventory.addItem(health); // add 1 health potion
        assertEquals(1, inventory.getCurrentSize());

        // if i remove it, the inventory should be empty
        inventory.decreaseItemCount(health);
        assertEquals(0, inventory.getCurrentSize());

        // now add 2 health potions
        inventory.addItem(health, 2); // add health potion

        // remove one potion, should still have one left
        inventory.decreaseItemCount(health);
        assertEquals(1, inventory.getCurrentSize());
    }

    @Test
    void canUseItemTest() {
        inventory.addItem(health); // add 1 health potion
        assertEquals(1, inventory.getCurrentSize());

        // can use it
        assertEquals(true, inventory.canUseItem(health));

        // use it
        inventory.decreaseItemCount(health);
        assertEquals(0, inventory.getCurrentSize());

        // cannot use it anymore
        assertEquals(false, inventory.canUseItem(health));
    }
}
