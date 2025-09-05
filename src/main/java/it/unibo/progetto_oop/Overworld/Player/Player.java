package it.unibo.progetto_oop.Overworld.Player;

import java.util.ArrayList;
import java.util.List;

import it.unibo.progetto_oop.Overworld.AdapterPattern.OverworldPlayerAdapter;
import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;
import it.unibo.progetto_oop.Overworld.Player.PlayerObserver.PlayerObserver;
import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.position.Position;
import it.unibo.progetto_oop.combat.potion_strategy.*;

// The Player class - Acts as the Subject/Observable
public class Player {
    private int currentHP;
    private int maxHP;
    private Position position;

    public Inventory inventory;

    private List<PlayerObserver> observers;

    public Player(int maxHP, Inventory inventory) {
        this.maxHP = maxHP;
        this.currentHP = this.maxHP;
        this.inventory = inventory; 
        this.observers = new ArrayList<>();
    }

    // obserbver methods

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
     * Notify observers about position changes.
     */
    private void notifyPositionChanged() {
        this.observers.stream().forEach(observers -> observers.playerPositionChanged(this));
    }

    /**
     * Use an item from the player's inventory.
     * 
     * @param item The item to be used.
     */
    public void useItem(Item item){ 
        if (this.inventory.hasItem(item)){ // check wether the item is in the inventory
            if (item instanceof Potion) {
                Potion potion = (Potion) item;
                PotionStrategy strategy = potion.getStrategy(); // the kind of potion
                if (strategy != null) {
                    System.out.println("Using potion " + potion.getDescription()); 
                    PossibleUser adaptedPlayer = new OverworldPlayerAdapter(this); 
                    potion.use(adaptedPlayer);
                    this.inventory.decreaseItemCount(item); // TODO: maybe put in the observer pattern
                    this.notifyInventoryChanged();
                }
                else{
                    System.out.println("Strategy is null");
                }
            }
            else {
                System.out.println("Not an istance of Potion, input ignored"); // the only usable objecys are potions
            }
        }
        else{
            System.out.println("Object not in inventory, input ignored");
        }
    }

    /**
     * Add an item to the player's inventory.
     *
     * @param item item to add to the inventory
     */
    public void addItem(Item item){
        this.inventory.addItem(item);
        this.observers.stream().forEach(observer -> observer.playerInventoryChanged(inventory));
    }

    /**
     * Heal the player by a specified amount of health.
     *
     * @param hp amount of health to heal
     */
    public void heal(int hp){
        this.setHp(hp);
    }

    // setters

    /**
     * Set the player's health points.
     *
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

    /**
     * Set the player's position.
     *
     * @param newPos the new position of the player
     */
    public void setPosition(Position newPos){
        this.position = newPos;
        this.notifyPositionChanged();
    }


    // getters

    /**
     * Get the current health points of the player.
     *
     * @return the current health points of the player
     */
    public int getCurrentHp(){
        return this.currentHP;
    }

    /**
     * Get the maximum health points of the player.
     *
     * @return the maximum health points of the player
     */
    public int getMaxHp(){
        return this.maxHP;
    }


    /** 
     * Get the player position
     * @return the position of the player
     */
    public Position getPosition(){
        return this.position;
    }

    
}