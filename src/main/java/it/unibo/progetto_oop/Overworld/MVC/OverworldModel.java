package it.unibo.progetto_oop.Overworld.MVC;

import java.util.ArrayList;
import java.util.List;

import it.unibo.progetto_oop.Combat.Inventory.Inventory;
import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class OverworldModel {
    private Player player;
    private List<Item> items = new ArrayList<>();
    private Inventory inventory;
    

    public OverworldModel(Player player, List<Item> items, Inventory inventory){
        this.player = player;
        this.items = items;
        this.inventory = inventory;
    }

    // --- getter methods ---

    /**
     *
     * @return Posizione del giocatore
     */
    public Player getPlayer(){
        return this.player;
    }

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
        return this.inventory;
    }
    
}
