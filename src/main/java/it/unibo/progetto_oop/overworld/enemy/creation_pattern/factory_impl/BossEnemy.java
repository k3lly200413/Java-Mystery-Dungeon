package it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl;

import it.unibo.progetto_oop.overworld.playGround.data.Position;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;


public class BossEnemy extends GenericEnemy {
    /**
     * Constructor of the BossEnemy class.
     * @param newMaxHealth max health
     * @param newCurrentHealth current health
     * @param newPower power
     * @param newInitialPosition initial position
     * @param newGridNotifier grid notifier
     */
    public BossEnemy(final int newMaxHealth, final int newCurrentHealth,
    final int newPower, final Position newInitialPosition,
    final GridNotifier newGridNotifier) {
        super(newMaxHealth, newCurrentHealth, newPower,
        newInitialPosition, newGridNotifier);
    }

    @Override
    public final boolean isBoss() {
        return true;
    }
}
