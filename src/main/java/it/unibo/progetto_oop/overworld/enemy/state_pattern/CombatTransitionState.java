package it.unibo.progetto_oop.overworld.enemy.state_pattern;

import it.unibo.progetto_oop.overworld.enemy.EnemyType;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.player.Player;

public class CombatTransitionState implements GenericEnemyState {
    /**
     * The type of the enemy.
     */
    private final EnemyType enemyType;

    /**
     * Constructor of the CombatTransitionState class.
     * @param newEnemyType the type of the enemy
     */
    public CombatTransitionState(final EnemyType newEnemyType) {
        this.enemyType = newEnemyType;
    }

    @Override
    public final void enterState(final Enemy context) {
    }

    @Override
    public final void exitState(final Enemy context) {
    }

    @Override
    public void update(final Enemy context, final Player player) { }

    @Override
    public void onPlayerMoved(final Enemy context, final Player player) { }

    @Override
    public final EnemyType getType() {
        return this.enemyType;
    }

    @Override
    public final String getDescription() {
        return "Combat Transition State";
    }

}
