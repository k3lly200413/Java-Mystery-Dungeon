
package it.unibo.progetto_oop.overworld.player;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.potion_strategy.PotionStrategy;
import it.unibo.progetto_oop.overworld.player.adapter_pattern.OverworldPlayerAdapter;
import it.unibo.progetto_oop.overworld.player.adapter_pattern.PossibleUser;
import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * Represents the player in the overworld,
 * manages stats, position, and inventory.
 */
@SuppressFBWarnings("EI_EXPOSE_REP")
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
     * the player max stamina value.
     */
    private int maxStamina;

    /**
     * the player stamina value.
     */
    private int stamina;

    /**
     * the player power value.
     */
    private int power;

    /**
     * the player's position.
     */
    private Position position;

    /**
     * the player's inventory.
     */
    private Inventory inventory;

    /**
     * Constructor for player class.
     *
     * @param maxHp the player's max hp value
     * @param newStamina the players' stamina value
     * @param newPower the player's power value
     * @param newInventory the player's inventory
     */
    public Player(final int maxHp, final int newStamina, final int newPower,
    final Inventory newInventory) {
        this.maxHP = maxHp;
        this.currentHP = this.maxHP;
        this.inventory = newInventory;
        this.stamina = newStamina;
        this.maxStamina = newStamina;
        this.power = newPower;
    }

    /**
     * Use an item from the player's inventory.
     *
     * @param item The item to be used.
     */
    public void useItem(final Item item) {
        // check wether the item is in the inventory
        if (this.inventory.hasItem(item)) {
            final PotionStrategy strategy =
                item.getStrategy(); // the kind of potion
            if (strategy != null) {
                final PossibleUser adaptedPlayer =
                    new OverworldPlayerAdapter(this);
                item.use(adaptedPlayer);
                this.inventory.decreaseItemCount(item);
            }
        }
    }

    /**
     * Add an item to the player's inventory.
     *
     * @param item item to add to the inventory
     */
    public void addItem(final Item item) {
        this.inventory.addItem(item);
    }

    //---- SETTERS ----

    /**
     * Set the player's health points.
     *
     * @param amount new hp value
     */
    public void setHp(final int amount) {
        this.currentHP = amount;
    }

    /**
     * Set the player's maximum health points.
     *
     * @param amount new max hp value
     */
    public void setMaxHp(final int amount) {
        this.maxHP = amount;
    }

    /**
     * Set the player's power.
     *
     * @param amount new power value
     */
    public void setPower(final int amount) {
        this.power = amount;
    }

    /**
     * Set the player's stamina.
     *
     * @param amount new stamina value
     */
    public void setStamina(final int amount) {
        this.stamina = amount;
    }

    /**
     * Set the player's max stamina.
     *
     * @param amount the new max stamina value
     */
    public void setMaxStamina(final int amount) {
        this.maxStamina = amount;
    }

    /**
     * Set the player's position.
     *
     * @param newPos the new position of the player
     */
    public void setPosition(final Position newPos) {
        this.position = newPos;
    }

    /**
     * Set the player's inventory.
     *
     * @param newInventory the player's inventory
     */
    public void setInventory(final Inventory newInventory) {
        this.inventory = newInventory;
    }

    // ---- GETTERS ----

    /**
     * Get the current health points of the player.
     *
     * @return the current health points of the player
     */
    public int getCurrentHp() {
        return this.currentHP;
    }

    /**
     * Get the maximum health points of the player.
     *
     * @return the maximum health points of the player
     */
    public int getMaxHp() {
        return this.maxHP;
    }

    /**
     * Get the stamina of the player.
     *
     * @return the stamina of the player
     */
    public int getStamina() {
        return this.stamina;
    }

    /**
     * Get the max stamina of the player.
     *
     * @return the max stamina of the player
     */
    public int getMaxStamina() {
        return this.maxStamina;
    }

    /**
     * Get the power of the player.
     *
     * @return the power of the player
     */
    public int getPower() {
        return this.power;
    }

    /**
     * Get the player position.
     *
     * @return the position of the player
     */
    public Position getPosition() {
        return this.position;
    }

    /**
     * Get the player's inventory.
     *
     * @return the player's inventory
     */
    public Inventory getInventory() {
        return this.inventory;
    }
}
