package it.unibo.progetto_oop.Overworld.Player.PlayerObserver;

import java.util.ArrayList;
import java.util.List;

import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.combat.Inventory.Inventory;

public class PlayerObserverImpl implements PlayerObserver { //  TODO
    OverworldModel model;

    public PlayerObserverImpl(OverworldModel model){
        this.model = model;
    }

    @Override
    public void playerHpChanged(int hp, int maxHp){
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'playerPositionChanged'");
    }

    @Override
    public void playerInventoryChanged(Inventory inventory){
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'playerPositionChanged'");
    }

    @Override
    public void playerPositionChanged(Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'playerPositionChanged'");
    }

    
}
                                                                             