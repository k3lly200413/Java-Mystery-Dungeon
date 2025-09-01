package it.unibo.progetto_oop.Overworld.Player.PlayerObserver;

import it.unibo.progetto_oop.combat.Inventory.Inventory;
import it.unibo.progetto_oop.Overworld.Player.Player;

// Interface for objects that want to observe the Player
public interface PlayerObserver {

    /**
     * This method is called when the player's HP changes.
     *
     * @param hp The current HP of the player.
     *
     * @param maxHp The maximum HP of the player.
     */
    public void playerHpChanged(int hp, int maxHp);

    /**
     * This method is called when the player's inventory changes.
     *
     * @param inventory The current inventory of the player.
     */
    public void playerInventoryChanged(Inventory inventory);

    /**
     * This method is called when the player's position changes.
     *
     * @param player The player whose position has changed.
     */
    void playerPositionChanged(Player player);
}
