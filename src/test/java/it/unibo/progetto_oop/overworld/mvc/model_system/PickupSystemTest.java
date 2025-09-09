package it.unibo.progetto_oop.overworld.mvc.model_system;

import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.potion_strategy.Potion;
import it.unibo.progetto_oop.overworld.mvc.OverworldModel;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PickupSystemTest {
    private Item item1;
    private Item item2;
    private Player player;
    private OverworldModel model;
    private Inventory inventory;
    private PickupSystem pickupSystem;

    @BeforeEach
    void setUp() {
        item1 = mock(Item.class);
        item2 = mock(Item.class);
        player = mock(Player.class);
        model = mock(OverworldModel.class);
        inventory = mock(Inventory.class);
        when(player.getInventory()).thenReturn(inventory);
        pickupSystem = new PickupSystem(new ArrayList<>(List.of(item1, item2)), player, model);
    }

    @Test
    void testGetItem() {
        List<Item> items = pickupSystem.getItem();
        assertEquals(2, items.size());
        assertTrue(items.contains(item1));
        assertTrue(items.contains(item2));
    }

    @Test
    void testSetItems() {
        Item item3 = mock(Item.class);
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
        Position position1 = new Position(1, 1);
        Item item1 = new Potion("name", "desc", position1, null);
        pickupSystem = new PickupSystem(new ArrayList<>(List.of(item1)), player, model);

        boolean removed = pickupSystem.removeItemAt(position1);
        assertTrue(removed);
        assertEquals(0, pickupSystem.getItem().size());
        verify(inventory).addItem(item1);
    }

    @Test
    void testCheckAndAddItem_ItemFound() {
        Position position1 = new Position(1, 1);
        Item item1 = new Potion("name", "desc", position1, null);
        pickupSystem = new PickupSystem(new ArrayList<>(List.of(item1)), player, model);

        when(player.getPosition()).thenReturn(position1);

        pickupSystem.checkAndAddItem();
        verify(inventory).addItem(item1);
        verify(inventory).printInventory();
    }

}
