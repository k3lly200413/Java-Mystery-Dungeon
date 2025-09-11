package it.unibo.progetto_oop.overworld.enemy.state_pattern;

import it.unibo.progetto_oop.overworld.enemy.EnemyType;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * Represents the sleeper state for an enemy.
 */
public class SleeperState implements GenericEnemyState {

    @Override
    public final void enterState(final Enemy context) {
        // throw new UnsupportedOperationException("In" + this.getDescription() + "so no action taken");
    }

    @Override
    public final void exitState(final Enemy context) {
        // throw new UnsupportedOperationException("In" + this.getDescription() + "so no action taken");
    }

    @Override
    public final void update(final Enemy context, final Player player) {
        final Position currentPos = context.getCurrentPosition();
        context.getGridNotifier().notifyEnemyMoved(currentPos, currentPos);
    }

    @Override
    public final EnemyType getType() {
        return EnemyType.SLEEPER;
    }

    @Override
    public final String getDescription() {
        return "Sleeper State";
    }
}
