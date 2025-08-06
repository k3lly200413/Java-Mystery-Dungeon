package it.unibo.progetto_oop.Overworld.Player;

import java.util.ArrayList;
import java.util.List;
import it.unibo.progetto_oop.Overworld.Inventory.Inventory;
import it.unibo.progetto_oop.Overworld.PlayerObserver.PlayerObserver;

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
            System.out.println("DEBUG : OBSERVER NON PRESENTE");
        }
    }

    private void notifyHpChange(int currentHP, int maxHP) {
        this.observers.stream().forEach(observer -> observer.playerHpChanged(this.currentHP, this.maxHP));
    }

    private void notifyInventoryChanged() {
        this.observers.stream().forEach(observer -> observer.playerInventoryChanged(this.inventory));
    }
}