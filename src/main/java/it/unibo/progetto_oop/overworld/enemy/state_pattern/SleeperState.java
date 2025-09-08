package it.unibo.progetto_oop.overworld.enemy.state_pattern;


import it.unibo.progetto_oop.overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.overworld.enemy.EnemyType;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.player.Player;

public class SleeperState implements GenericEnemyState {

    @Override
    public final void enterState(final Enemy context) {
        System.out.println("Entered Sleeper State");
    }

    @Override
    public final void exitState(final Enemy context) {
        System.out.println("Exiting Sleeper State");
    }

    @Override
    public final void update(final Enemy context, final Player player) {
        Position currentPos = context.getCurrentPosition();
        context.getGridNotifier().notifyEnemyMoved(currentPos, currentPos);
    }

    @Override
    public final void onPlayerMoved(final Enemy context, final Player player) {
        System.out.println("In Sleeper State so no action taken");
    }

    @Override
    public final EnemyType getType() {
        return EnemyType.SLEEPER;
    }
}
