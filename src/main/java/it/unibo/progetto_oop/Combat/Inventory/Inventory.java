package it.unibo.progetto_oop.combat.Inventory;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
/**
 * @author Laura Bertozzi
 */

public class Inventory {
    private final Map<Item, Integer> items;
    private final int capacity;

    public Inventory(int capacity){
        this.items = new HashMap<>();
        this.capacity = capacity > 0 ? capacity : Integer.MAX_VALUE; // Set to Integer.MAX_VALUE if capacity is not specified
        System.out.println("New inventory with capacity: "+this.capacity);
    }

    public Inventory(){
        this(0);
        System.out.println("New inventory with no capacity limit");
    }

    public int getCurrentSize(){
        return this.items.size();
    }

    public boolean addItem(Item item){ // add one item
        if (item == null){ 
            System.out.println("Item is null");
            return false;
        }
        if (!this.items.containsKey(item) && items.size() >= this.capacity){
            System.out.println("Full cannot add new item");
            return false;
        }
        // updating item amount
        int currentCount = this.items.getOrDefault(item, 0); 
        items.put(item, currentCount + 1);
        return true;
    }

    public boolean addItem(Item item, int quantity){ // add "quantity" items
        if (item == null){ 
            System.out.println("Item is null");
            return false;
        }
        if (!this.items.containsKey(item) && items.size() >= capacity){
            System.out.println("Full cannot add new item");
            return false;
        }
        // updating item amount
        int currentCount = this.items.getOrDefault(item, 0); 
        this.items.put(item, currentCount + quantity);
        return true;
    }

    public boolean decreaseItemCount(Item item){
        if (item == null){
            System.out.println("Item is null");
            return false;
        }
        if (!this.items.containsKey(item)){
            System.err.println("Item does not have a key. Please Fix");
            return false;
        }
        int currentAmount = this.items.get(item);

        if (currentAmount <= 1){ // when i'll decrease the count will be 0, so remove the item from the inventory
            this.items.remove(item);
            System.out.println("Item removed becuause you don't have it anymore in the inventory");
            return true;
        } 
        this.items.replace(item, currentAmount - 1);
        System.out.println("Removed from inventory because used");
        return true;
    }

    public int getItemCount(Item item){
        return this.items.getOrDefault(item, 0);
    }

    public boolean hasItem(Item item){
        return this.getItemCount(item) > 0;
    }

    public boolean canUseItem(Item item) {
        if (item == null) {
            System.out.println("Item provided is NULL.");
            return false;
        }
        return this.hasItem(item);
    }

    public boolean isEmpty(){
        return this.items.isEmpty();
    }

    public int getCapacity(){
        return this.capacity;
    }

    public Map<Item, Integer> getFullInventory(){
        return this.items;
    }

    public void clear(){
        this.items.clear();
    }

    public void printInventory() {
        this.items.entrySet().stream().forEach(key -> System.out.println(key.getKey().getName() + ":" + key.getValue()));
    }

    public String getItemDescription(int index){
        Optional<Item> possibleItem = this.getNthItem(index);
        if (possibleItem.isPresent()){
            return possibleItem.get().getName() + ":" + possibleItem.get().getDescription();
        }
        else {
            return "No item at the index : " + index + "Please fix";
        }
    }

    public Optional<Item> getNthItem(int index) {
        return this.items.keySet().stream().skip(index).findFirst();
    }
}
