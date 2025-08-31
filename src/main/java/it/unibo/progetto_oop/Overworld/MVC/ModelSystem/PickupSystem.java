package it.unibo.progetto_oop.Overworld.MVC.ModelSystem;

import java.util.List;
import java.util.Optional;

<<<<<<< HEAD
import it.unibo.progetto_oop.Combat.Inventory.Inventory;
import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
=======
>>>>>>> 193bbdc31a0a30b1ddfa2952e5f3c0e623bcbbaa
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.combat.Inventory.Inventory;
import it.unibo.progetto_oop.combat.Inventory.Item;

public class PickupSystem {
    private List<Item> items; // items present in the map
    private final Player player;
    private final OverworldModel model; //@autor Alice

    public PickupSystem(List<Item> items, Player player, OverworldModel model) {
        this.items = items;
        this.player = player;
        this.model = model;   //@autor Alice
    }

    // getters

    /**
     * 
     * @return the list of items in the overworld
     */
    public List<Item> getItem(){
        return this.items;
    }

    /**
     * 
     * @return the player's inventory
     */

    public Inventory getInventoryInstance(){
        return this.player.inventory;
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
     * Remove an item from the overworld and add it to the player's inventory
     * 
     * @param item the item to remove
     */
    private void removeItem(Item itemToRemove){
        this.player.inventory.addItem(itemToRemove);
        this.items.remove(itemToRemove);
        this.model.notifyItemRemoved(itemToRemove.getPosition());  // @autor Alice
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
            System.out.println("Item found, picking it up "+item.getName());
            this.removeItem(item);
            this.player.inventory.printInventory();
        });
    }
}
