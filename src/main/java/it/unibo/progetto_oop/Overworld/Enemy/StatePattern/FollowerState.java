package it.unibo.progetto_oop.Overworld.Enemy.StatePattern;

import it.unibo.progetto_oop.Overworld.Enemy.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class FollowerState implements GenericEnemyState{

    @Override
    public void enterState(Enemy context, OverworldModel model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enterState'");
    }

    @Override
    public void exitState(Enemy context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exitState'");
    }

    @Override
    public void update(Enemy enemy, OverworldModel model, Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void onPlayerMoved(Enemy context, Player player, OverworldModel model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onPlayerMoved'");
    }

    @Override
    public EnemyType getType() {
        return EnemyType.FOLLOWER;
    }
    
}
