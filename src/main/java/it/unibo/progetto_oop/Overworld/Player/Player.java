package it.unibo.progetto_oop.Overworld.Player;

import java.util.ArrayList;
import java.util.List;

import it.unibo.progetto_oop.Overworld.AdapterPattern.OverworldPlayerAdapter;
import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.Player.PlayerObserver.PlayerObserver;
import it.unibo.progetto_oop.combat.PotionStrategy.PotionStrategy;
import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.PotionStrategy.Potion;

// The Player class - Acts as the Subject/Observable
public class Player {
    /**
     * the player current hp value.
     */
    private int currentHP;

    /**
     * the player max hp value.
     */
    private int maxHP;

    /**
     * the player's position.
     */
    private Position position;

    /**
     * the player's inventory.
     */
    private Inventory inventory;

    /**
     * the list of observers.
     */
    private List<PlayerObserver> observers;

    /**
     * constructor for player class.
     * @param maxHp
     * @param newInventory
     */
    public Player(final int maxHp, final Inventory newInventory) {
        this.maxHP = maxHp;
        this.currentHP = this.maxHP;
        this.inventory = newInventory;
        this.observers = new ArrayList<>();
    }

    // obserbver methods

    /**
     * add an observer.
     * @param observer the observer to add
     */
    public void addObservers(final PlayerObserver observer) {
        if (!this.observers.contains(observer)) { // if not already present
            this.observers.add(observer);
        }
    }

    /** remove an observer.
     *  @param observer the observer to remove
    */
    public void removeObservers(final PlayerObserver observer) {
        if (!this.observers.remove(observer)) {
            System.out.println("Observer not found");
        }
    }

    /** Notify observers about hp change.
     * @param currentHp the current value for health point
     * @param maxHp the maximum value for health point
     */
    private void notifyHpChange(final int currentHp, final int maxHp) {
        this.observers.stream()
            .forEach(
                observer -> observer.playerHpChanged(
                    currentHp, maxHp));
    }

    /**
     * Notify observers about inventory changes.
     */
    private void notifyInventoryChanged() {
        this.observers.stream().
            forEach(
                observer -> observer.playerInventoryChanged(this.inventory));
    }

    /**
     * Notify observers about position changes.
     */
    private void notifyPositionChanged() {
        this.observers.stream().
            forEach(
                observer -> observer.playerPositionChanged(this));
    }

    /**
     * Use an item from the player's inventory.
     * @param item The item to be used.
     */
    public void useItem(final Item item) {
        // check wether the item is in the inventory
        if (this.inventory.hasItem(item)) {
            if (item instanceof Potion) {
                Potion potion = (Potion) item;
                PotionStrategy strategy =
                    potion.getStrategy(); // the kind of potion
                if (strategy != null) {
                    System.out.println("Using potion "
                        + potion.getDescription());
                    PossibleUser adaptedPlayer =
                        new OverworldPlayerAdapter(this);
                    potion.use(adaptedPlayer);
                    //TODO maybe put in the observer pattern
                    this.inventory.decreaseItemCount(item);
                    this.notifyInventoryChanged();
                } else {
                    System.out.println("Strategy is null");
                }
            } else {
                // the only usable objects are potions
                System.out.println("Not an istance of Potion, input ignored");
            }
        } else {
            System.out.println("Object not in inventory, input ignored");
        }
    }

    /**
     * Add an item to the player's inventory.
     * @param item item to add to the inventory
     */
    public void addItem(final Item item) {
        this.inventory.addItem(item);
        this.observers.stream()
            .forEach(observer -> observer.playerInventoryChanged(inventory));
    }

    /**
     * Heal the player by a specified amount of health.
     * @param hp amount of health to heal
     */
    public void heal(final int hp) {
        this.setHp(hp);
    }

    //---- SETTERS ----

    /**
     * Set the player's health points.
     * @param amount amount to increase the player's health points
     */
    public void setHp(final int amount) {
        if (this.currentHP != this.maxHP && this.currentHP != 0) {
            // if currentHP + amount > maxHP, set it to maxHP
            this.currentHP = Math.min(this.maxHP, this.currentHP + amount);
            this.notifyHpChange(this.currentHP, this.maxHP);
        } else {
            System.out.println(
                "Nothing changed because either Max health or no health");
        }
    }

    /**
     * Set the player's position.
     * @param newPos the new position of the player
     */
    public void setPosition(final Position newPos) {
        this.position = newPos;
        this.notifyPositionChanged();
    }

    /**
     * Set the player's inventory.
     * @param newInventory the player's inventory
     */
    public void setInventory(final Inventory newInventory) {
        this.inventory = newInventory;
    }


    // ---- GETTERS ----

    /**
     * Get the current health points of the player.
     * @return the current health points of the player
     */
    public int getCurrentHp() {
        return this.currentHP;
    }

    /**
     * Get the maximum health points of the player.
     * @return the maximum health points of the player
     */
    public int getMaxHp() {
        return this.maxHP;
    }


    /**
     * Get the player position.
     * @return the position of the player
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * get the player's inventory.
     * @return the player's inventory
     */
    public Inventory getInventory() {
        return this.inventory;
    }
}
