package it.unibo.progetto_oop.Overworld.Player.PlayerObserver;


import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.Player.Player;

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
                                                                             