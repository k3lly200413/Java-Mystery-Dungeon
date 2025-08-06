package it.unibo.progetto_oop.Overworld.Player.PlayerObserver;

import it.unibo.progetto_oop.Overworld.Inventory.Inventory;

// Interface for objects that want to observe the Player
public interface PlayerObserver {
    public void playerHpChanged(int hp, int maxHp);
    public void playerInventoryChanged(Inventory inventory);
}
