package it.unibo.progetto_oop.Overworld.Enemy.StatePattern;

import it.unibo.progetto_oop.Overworld.Enemy.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class PatrolState implements GenericEnemyState {

    @Override
    public void enterState(Enemy context, OverworldModel model) {
        System.out.println("Entered Patrol State");
        // Initialize patrol path or any other necessary setup for patrolling
        // context.setPatrolPath(...); // Example method to set a patrol path
        
    }

    @Override
    public void exitState(Enemy context) {
        System.out.println("Exiting Patrol State");
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
        return EnemyType.PATROLLER;
    }
    
}
