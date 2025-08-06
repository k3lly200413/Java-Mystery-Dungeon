package it.unibo.progetto_oop.Overworld.Player;

import java.util.ArrayList;
import java.util.List;
import it.unibo.progetto_oop.Overworld.Inventory.Inventory;
import it.unibo.progetto_oop.Overworld.Inventory.Item;
import it.unibo.progetto_oop.Overworld.Player.PlayerObserver.PlayerObserver;
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

    public void addObservers(PlayerObserver observer){
        if (!this.observers.contains(observer)){
            this.observers.add(observer);
        }
    }

    public void removeObservers(PlayerObserver observer){
        if (!this.observers.remove(observer)){
            System.out.println("Observer not found");
        }
    }

    private void notifyHpChange(int currentHP, int maxHP) {
        this.observers.stream().forEach(observer -> observer.playerHpChanged(this.currentHP, this.maxHP));
    }

    private void notifyInventoryChanged() {
        this.observers.stream().forEach(observer -> observer.playerInventoryChanged(this.inventory));
    }

    public void useItem(Item item){
        // TODO
    }

    public void addItem(Item item){
        // TODO
    }

    public void heal(int hp){
        this.setHp(hp);
    }

    public int getCurrentHp(){
        return this.currentHP;
    }

    public int getMaxHp(){
        return this.maxHP;
    }

    public void setHp(int amount){
        if (this.currentHP != this.maxHP && this.currentHP != 0){
            this.currentHP = Math.max(this.maxHP, this.currentHP + amount);
            this.notifyHpChange(this.currentHP, this.maxHP);
        }
        else{
            System.out.println("Nothing changed because either Max health or no health");
        }
        
    }
}