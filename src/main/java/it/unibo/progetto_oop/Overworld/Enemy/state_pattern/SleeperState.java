package it.unibo.progetto_oop.overworld.enemy.state_pattern;


import it.unibo.progetto_oop.overworld.enemy.EnemyType;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;

public class SleeperState implements GenericEnemyState {

    @Override
    public void enterState(Enemy context) {
        System.out.println("Entered Sleeper State");
    }

    @Override
    public void exitState(Enemy context) {
        System.out.println("Exiting Sleeper State");
    }

    @Override
    public void update(Enemy context, Player player) {
        Position currentPos = context.getCurrentPosition();
        context.getGridNotifier().notifyEnemyMoved(currentPos, currentPos);
    }

    @Override
    public void onPlayerMoved(Enemy context, Player player) {
        System.out.println("In Sleeper State so no action taken");
    }

    @Override
    public EnemyType getType() {
        return EnemyType.SLEEPER;
    }
    
}
