package it.unibo.progetto_oop.Overworld.enemy.state_pattern;

import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Overworld.enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.enemy.creation_pattern.factory_impl.Enemy;

public class CombatTransitionState implements GenericEnemyState {
    /**
     * The type of the enemy.
     */
    private EnemyType enemyType;

    /**
     * Constructor of the CombatTransitionState class.
     * @param newEnemyType the type of the enemy
     */
    public CombatTransitionState(final EnemyType newEnemyType) {
        this.enemyType = newEnemyType;
    }

    @Override
    public final void enterState(final Enemy context) {
        System.out.println("Entering CombatTransition");
    }

    @Override
    public final void exitState(final Enemy context) {
        System.out.println("Exiting CombatTransition");
    }

    @Override
    public void update(final Enemy context, final Player player) { }

    @Override
    public void onPlayerMoved(final Enemy context, final Player player) { }

    @Override
    public final EnemyType getType() {
        return this.enemyType;
    }

}
