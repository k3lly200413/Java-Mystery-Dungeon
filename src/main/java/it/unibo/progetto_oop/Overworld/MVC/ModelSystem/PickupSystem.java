package it.unibo.progetto_oop.Overworld.MVC.ModelSystem;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import it.unibo.progetto_oop.Combat.Inventory.Inventory;
import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.Player.Player;

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
     * the Overworld model.
     */
    private final OverworldModel model;

    /**
     * contructor.
     * @param newItems the items on the map
     * @param newPlayer the player
     * @param newModel the Overworld model
     */
    public PickupSystem(final List<Item> newItems,
                        final Player newPlayer, final OverworldModel newModel) {
        this.model = Objects.requireNonNull(newModel, "Model cannot be null");
        this.player = Objects.requireNonNull(
            newPlayer, "Player cannot be null");
        this.items = newItems;
    }

    //---- GETTERS ----

    /**
     * get the items on the map.
     * @return the list of items in the overworld
     */
    public List<Item> getItem(){
        return this.items;
    }

    /**
     * get the player's inventory.
     * @return the player's inventory
     */

    public Inventory getInventoryInstance() {
        return this.player.getInventory();
    }

    // setters

    /**
     * set the items on the current floor
     * @param items the items to set
     */
    public void setItems(List<Item> items) {
        this.items = items;
    }

    // methods

    /**
     * Remove an item from the overworld and add it to the player's inventory.
     * @param itemToRemove the item to remove
     */
    private void removeItem(final Item itemToRemove){
        this.player.getInventory().addItem(itemToRemove);
        this.items.remove(itemToRemove);
        this.model.gridNotifier.
            notifyItemRemoved(itemToRemove.getPosition());  // @autor Alice
    }

    /**
     * Check if an item is found at the player's position
     * @return an Optional containing the item if found, otherwise an empty Optional
     */
    private Optional<Item> ItemFoundAtPlayerPosition(){
        return this.items.stream().filter(
            item -> item.getPosition().equals(this.player.getPosition())
            ).findFirst();
    }

    /**
     * Check if an item is found at the player's position and add it to the inventory
     * If an item is found, remove it from the overworld items list
     */
    public void checkAndAddItem(){
        Optional<Item> itemOpt = this.ItemFoundAtPlayerPosition();

        itemOpt.ifPresent(item -> {
            System.out.println("Item found, picking it up " + item.getName());
            this.removeItem(item);
            this.player.getInventory().printInventory();
        });
    }
}
