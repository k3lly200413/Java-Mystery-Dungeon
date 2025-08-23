package it.unibo.progetto_oop.Overworld.Player.PlayerObserver;

import java.util.ArrayList;
import java.util.List;

import it.unibo.progetto_oop.Combat.Inventory.Inventory;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class PlayerObserverImpl implements PlayerObserver { //  TODO
    OverworldModel model;

    public PlayerObserverImpl(OverworldModel model){
        this.model = model;
    }
    public void playerHpChanged(int hp, int maxHp){

    }
    public void playerInventoryChanged(Inventory inventory){
        
    }
    @Override
    public void playerPositionChanged(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'playerPositionChanged'");
    }

    
}
                                                                             