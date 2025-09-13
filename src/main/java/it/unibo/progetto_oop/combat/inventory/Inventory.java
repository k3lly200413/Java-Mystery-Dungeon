package it.unibo.progetto_oop.combat.inventory;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Player's inventory.
 */
public class Inventory implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Map that contains the items and their quantities.
     */
    private final Map<Item, Integer> items;

    /**
     * Maximum capacity of the inventory.
     */
    private final int capacity;

    /**
     * Constructor of the Inventory class.
     *
     * @param newCapacity the maximum capacity of the inventory
     */
    public Inventory(final int newCapacity) {
        this.items = new HashMap<>();
        // Set to Integer.MAX_VALUE if capacity is not specified
        this.capacity = newCapacity > 0 ? newCapacity : Integer.MAX_VALUE;
    }

    /**
     * Default constructor with infinite capacity.
     */
    public Inventory() {
        this(0);
    }

    /**
     * Create a copy of the inventory.
     *
     * @return a new Inventory object that is a copy of the current one
     */
    public Inventory copy() {
        final Inventory copyInventory = new Inventory(this.capacity);
        for (final Map.Entry<Item, Integer> entry : this.items.entrySet()) {
            copyInventory.items.put(entry.getKey(), entry.getValue());
        }
        return copyInventory;
    }

    /**
     * Get the current size of the inventory (number of different items).
     *
     * @return the current size of the inventory
     */
    public int getCurrentSize() {
        return this.items.size();
    }

    /**
     * Add one item to the inventory.
     *
     * @param item the item to be added
     * @return true if the item was added, false otherwise
     */
    public boolean addItem(final Item item) { // add one item
        if (item == null) {
            return false;
        }
        if (!this.items.containsKey(item) && items.size() >= this.capacity) {
            return false;
        }
        // updating item amount
        final int currentCount = this.items.getOrDefault(item, 0);
        items.put(item, currentCount + 1);
        return true;
    }

    /**
     * Add multiple items to the inventory.
     *
     * @param item the item to be added
     * @param quantity the quantity of the item to be added
     * @return true if the items were added, false otherwise
     */
    public boolean addItem(final Item item, final int quantity) {
        if (item == null) {
            return false;
        }
        if (!this.items.containsKey(item) && items.size() >= capacity) {
            return false;
        }
        // updating item amount
        final int currentCount = this.items.getOrDefault(item, 0);
        this.items.put(item, currentCount + quantity);
        return true;
    }

    /**
     * Decrease the quantity of an item in the inventory by one.
     *
     * @param item the item to be decreased
     * @return true if the item was decreased, false otherwise
     */
    public boolean decreaseItemCount(final Item item) {
        if (item == null) {
            return false;
        }
        if (!this.items.containsKey(item)) {
            return false;
        }
        final int currentAmount = this.items.get(item);

        // when i'll decrease the count will be 0,
        // so remove the item from the inventory
        if (currentAmount <= 1) {
            this.items.remove(item);
            return true;
        }
        this.items.replace(item, currentAmount - 1);
        return true;
    }

    /**
     * Get the quantity of a specific item in the inventory.
     *
     * @param item the item to check
     * @return the quantity of the item in the inventory
     */
    public int getItemCount(final Item item) {
        return this.items.getOrDefault(item, 0);
    }

    /**
     * Check if the inventory contains at least one of the specified item.
     *
     * @param item the item to check
     * @return true if the item is in the inventory, false otherwise
     */
    public boolean hasItem(final Item item) {
        return this.getItemCount(item) > 0;
    }

    /**
     * Check if the item can be used (exists in inventory).
     *
     * @param item the item to check
     * @return true if the item can be used, false otherwise
     */
    public boolean canUseItem(final Item item) {
        return item != null && this.hasItem(item);
    }

    /**
     * Check if the inventory is empty.
     *
     * @return true if the inventory is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    /**
     * Get the maximum capacity of the inventory.
     *
     * @return the maximum capacity of the inventory
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Get the full inventory as a map.
     *
     * @return a map representing the full inventory
     */
    public Map<Item, Integer> getFullInventory() {
        return Collections.unmodifiableMap(new HashMap<>(this.items));
    }

    /**
     * Clear the inventory.
     */
    public void clear() {
        this.items.clear();
    }

    /**
     * Get the description of the item at the specified index.
     *
     * @param index the index of the item
     * @return the description of the item, or an error message if not found
     */
    public String getItemDescription(final int index) {
        final Optional<Item> possibleItem = this.getNthItem(index);
        if (possibleItem.isPresent()) {
            return possibleItem.get()
                .getName() + ":" + possibleItem.get().getDescription();
        } else {
            return "No item at the index : " + index + "Please fix";
        }
    }

    /**
     * Get the nth item in the inventory.
     *
     * @param index the index of the item
     * @return an Optional containing the item if found,
     *         or an empty Optional if not found
     */
    public Optional<Item> getNthItem(final int index) {
        return this.items.keySet().stream().skip(index).findFirst();
    }
}
