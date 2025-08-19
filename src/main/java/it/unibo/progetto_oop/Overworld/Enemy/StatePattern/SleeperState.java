package it.unibo.progetto_oop.Overworld.Enemy.StatePattern;

import it.unibo.progetto_oop.Overworld.Enemy.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class SleeperState implements GenericEnemyState {

    @Override
    public void enterState(Enemy context, OverworldModel model) {
        System.out.println("Entered Sleeper State");
    }

    @Override
    public void exitState(Enemy context) {
        System.out.println("Exiting Sleeper State");
    }

    @Override
    public void update(Enemy enemy, OverworldModel model, Player player) {
        System.out.println("In Sleeper State so no action taken");
    }

    @Override
    public void onPlayerMoved(Enemy context, Player player, OverworldModel model) {
        System.out.println("In Sleeper State so no action taken");
    }

    @Override
    public EnemyType getType() {
        return EnemyType.SLEEPER;
    }
    
}
