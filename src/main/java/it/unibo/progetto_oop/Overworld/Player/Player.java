package it.unibo.progetto_oop.Overworld.Player;

import java.util.ArrayList;
import java.util.List;

import it.unibo.progetto_oop.Overworld.AdapterPattern.OverworldPlayerAdapter;
import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;
import it.unibo.progetto_oop.Overworld.Inventory.Inventory;
import it.unibo.progetto_oop.Overworld.Inventory.Item;
import it.unibo.progetto_oop.Overworld.Player.PlayerObserver.PlayerObserver;
import it.unibo.progetto_oop.Overworld.PotionStrategy.Potion;
import it.unibo.progetto_oop.Overworld.PotionStrategy.PotionEffectStrategy;
import it.unibo.progetto_oop.Combat.PotionStrategy.*;
import it.unibo.progetto_oop.Combat.Position.Position;

// The Player class - Acts as the Subject/Observable
public class Player {
    private int currentHP;
    private int maxHP;
    private Inventory inventory;

    private List<PlayerObserver> observers;

    public Player(int maxHP) {
        this.maxHP = maxHP;
        this.currentHP = this.maxHP;
        this.inventory = new Inventory(); 
        this.observers = new ArrayList<>();
    }

    /** add an observer */
    public void addObservers(PlayerObserver observer){
        if (!this.observers.contains(observer)){ // if not already present
            this.observers.add(observer);
        }
    }

    /** remove an observer */
    public void removeObservers(PlayerObserver observer){
        if (!this.observers.remove(observer)){
            System.out.println("Observer not found");
        }
    }

    /** Notify observers about hp change */
    private void notifyHpChange(int currentHP, int maxHP) {
        this.observers.stream().forEach(observer -> observer.playerHpChanged(this.currentHP, this.maxHP));
    }

    /**
     * Notify observers about inventory changes.
     */
    private void notifyInventoryChanged() {
        this.observers.stream().forEach(observer -> observer.playerInventoryChanged(this.inventory));
    }

    /**
     * Use an item from the player's inventory.
     * 
     * @param item The item to be used.
     */
    public void useItem(Item item){
        // TODO
    }

    /**
     * Add an item to the player's inventory.
     * @param item item to add to the inventory
     */
    public void addItem(Item item){
        // TODO
    }

    /**
     * Heal the player by a specified amount of health.
     * @param hp amount of health to heal
     */
    public void heal(int hp){
        this.setHp(hp);
    }

    /**
     * Set the player's health points.
     * @param amount amount to increase the player's health points
     */
    public void setHp(int amount){
        if (this.currentHP != this.maxHP && this.currentHP != 0){
            this.currentHP = Math.min(this.maxHP, this.currentHP + amount); // if currentHP + amount > maxHP, set it to maxHP
            this.notifyHpChange(this.currentHP, this.maxHP);
        }
        else{
            System.out.println("Nothing changed because either Max health or no health");
        }
        
    }

    // getters

    /**
     * Get the current health points of the player.
     * @return the current health points of the player
     */
    public int getCurrentHp(){
        return this.currentHP;
    }

    /**
     * Get the maximum health points of the player.
     * @return the maximum health points of the player
     */
    public int getMaxHp(){
        return this.maxHP;
    }

    
}