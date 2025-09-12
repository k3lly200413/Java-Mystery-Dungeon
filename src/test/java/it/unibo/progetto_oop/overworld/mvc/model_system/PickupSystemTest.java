package it.unibo.progetto_oop.overworld.mvc.model_system;

import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.potion_strategy.Potion;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

class PickupSystemTest {
    /**
     * mock item 1.
     */
    private Item item1;

    /**
     * mock item 2.
     */
    private Item item2;

    /**
     * mock player.
     */
    private Player player;

    /**
     * mock inventory.
     */
    private Inventory inventory;

    /**
     * system under test.
     */
    private PickupSystem pickupSystem;

    @BeforeEach
    void setUpPickupSystem() {
        item1 = mock(Item.class);
        item2 = mock(Item.class);
        player = mock(Player.class);
        inventory = mock(Inventory.class);
        when(player.getInventory()).thenReturn(inventory);
        pickupSystem = new PickupSystem(
            new ArrayList<>(List.of(item1, item2)), player);
    }

    @Test
    void testGetItem() {
        final List<Item> items = pickupSystem.getItem();
        assertEquals(2, items.size());
        assertTrue(items.contains(item1));
        assertTrue(items.contains(item2));
    }

    @Test
    void testSetItems() {
        final Item item3 = mock(Item.class);
        pickupSystem.setItems(new ArrayList<>(List.of(item3)));
        assertEquals(1, pickupSystem.getItem().size());
        assertTrue(pickupSystem.getItem().contains(item3));
    }

    @Test
    void testGetInventoryInstance() {
        assertEquals(inventory, pickupSystem.getInventoryInstance());
    }

    @Test
    void testRemoveItemAt() {
        final Position position3 = new Position(1, 1);
        final Item item3 = new Potion(
            "name", "desc", position3, null);
        pickupSystem = new PickupSystem(
            new ArrayList<>(List.of(item3)), player);

        final boolean removed = pickupSystem.removeItemAt(position3);
        assertTrue(removed);
        assertEquals(0, pickupSystem.getItem().size());
        verify(inventory).addItem(item3);
    }

    @Test
    void testCheckAndAddItem() {
        final Position position3 = new Position(1, 1);
        final Item item3 = new Potion(
            "name", "desc", position3, null);
        pickupSystem = new PickupSystem(
            new ArrayList<>(List.of(item3)), player);

        when(player.getPosition()).thenReturn(position3);

        pickupSystem.checkAndAddItem();
        verify(inventory).addItem(item3);
        verify(inventory).printInventory();
    }

}
