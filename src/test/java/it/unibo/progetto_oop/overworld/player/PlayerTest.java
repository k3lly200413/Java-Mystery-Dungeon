package it.unibo.progetto_oop.overworld.player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.potion_factory.ItemFactory;
import it.unibo.progetto_oop.overworld.playground.data.Position;

class PlayerTest {
    /**
     * costant max hp.
     */
    private static final int MAX_HP = 100;

    /**
     * costant max stamina.
     */
    private static final int MAX_STAMINA = 100;

    /**
     * costant power.
     */
    private static final int POWER = 10;

    /**
     * the inventory mock.
     */
    private Inventory inventory;

    /**
     * health potion mock.
     */
    private Item health;

    /**
     * antidote mock.
     */
    private Item antidote;

    /**
     * set up the test.
     */
    @BeforeEach
    void setUp() {
        // Create a inventory
        inventory = new Inventory();

        // Create some items
        final var potionFactory = new ItemFactory();
        health = potionFactory.createItem("Health Potion", new Position(0, 0));
        antidote = potionFactory.createItem("Antidote",
         new Position(1, 0));
    }

    @Test
    void useItemTest() {
        // add the item to the inventory
        inventory.addItem(health);

        assertEquals(1, inventory.getCurrentSize());

        // Create a player with the inventory
        final var player = new Player(MAX_HP, MAX_STAMINA, POWER, inventory);

        // Use the item
        player.useItem(health);

        // Verify that the inventory's useItem
        // method was called with the correct item
        assertEquals(0, inventory.getCurrentSize());
    }

    @Test
    void addItemTest() {
        // add item to the inventory via inventory method
        inventory.addItem(antidote);

        assertEquals(1, inventory.getCurrentSize());

        // Create a player with the inventory
        final var player = new Player(MAX_HP, MAX_STAMINA, POWER, inventory);

        // Add the item via player method
        player.addItem(health);

        // Verify that the inventory's addItem method
        // was called with the correct item
        assertEquals(2, inventory.getCurrentSize());
    }

}
