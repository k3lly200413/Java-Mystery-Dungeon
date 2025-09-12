package it.unibo.progetto_oop.overworld.mvc.model_system;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * Gestisce la logica di raccolta degli oggetti.
 */
public class PickupSystem {
    /**
     * the items on the map.
     */
    private List<Item> items;

    /**
     * the player.
     */
    private final Player player;

    /**
     * Constructor.
     *
     * @param newItems the items on the map
     * @param newPlayer the player
     */
    public PickupSystem(final List<Item> newItems,
                        final Player newPlayer) {
        this.player = Objects.requireNonNull(
            newPlayer, "Player cannot be null");
        this.items = newItems;
    }

    // ---- GETTERS ---- //

    /**
     * Get the items on the map.
     *
     * @return the list of items in the overworld
     */
    public List<Item> getItem() {
        return this.items;
    }

    /**
     * Get the player's inventory.
     *
     * @return the player's inventory
     */

    public Inventory getInventoryInstance() {
        return this.player.getInventory();
    }

    // ---- SETTERS ---- //

    /**
     * Set the items on the current floor.
     *
     * @param newItems the items to set
     */
    public void setItems(final List<Item> newItems) {
        this.items = newItems;
    }

    // methods

    /**
     * Remove an item from the overworld and add it to the player's inventory.
     *
     * @param itemToRemove the item to remove
     * @return true if the item was removed, false otherwise
     */
    public boolean removeItemAt(final Position itemToRemove) {
        this.player.getInventory().addItem(
            this.items.stream()
                .filter(item -> item.getPosition().equals(itemToRemove))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                    "Item not found at position: " + itemToRemove))
        );
        return this.items.removeIf(item ->
            item.getPosition().equals(itemToRemove));
        //this.model.getGridNotifier().notifyItemRemoved(itemToRemove);
    }

    /**
     * Check if an item is found at the player's position.
     *
     * @return an Optional containing the
    item if found, otherwise an empty Optional
     */
    private Optional<Item> itemFoundAtPlayerPosition() {
        return this.items.stream().filter(
            item -> item.getPosition().equals(this.player.getPosition())
            ).findFirst();
    }

    /**
     * Check if an item is found at the player's position
     * and add it to the inventory.
     * If an item is found, remove it from the overworld items list
     */
    public void checkAndAddItem() {
        final Optional<Item> itemOpt = this.itemFoundAtPlayerPosition();

        itemOpt.ifPresent(item -> {
            this.removeItemAt(item.getPosition());
            this.player.getInventory().printInventory();
        });
    }
}
