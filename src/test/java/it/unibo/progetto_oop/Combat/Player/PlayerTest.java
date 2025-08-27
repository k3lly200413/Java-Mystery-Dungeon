package it.unibo.progetto_oop.Combat.Player;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Combat.Inventory.Inventory;
import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Combat.Position.Position;
import it.unibo.progetto_oop.Combat.PotionFactory.ItemFactory;

public class PlayerTest {

    @Test
    void useItemTest() {
        // Create a inventory
        var inventory = new Inventory();


        // Create an item
        var potionFactory = new ItemFactory();
        Item potion = potionFactory.createItem("Health Potion", new Position(0,0));

        // add the item to the inventory
        inventory.addItem(potion);

        assertEquals(1, inventory.getCurrentSize());

        // Create a player with the inventory
        var player = new Player(100, inventory);

        // Use the item
        player.useItem(potion);
        
        // Verify that the inventory's useItem method was called with the correct item
        assertEquals(0, inventory.getCurrentSize());
    }
    
}
