package it.unibo.progetto_oop.Overworld.ViewManagerObserver;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;

public interface ViewManagerObserver {
    public void onPlayerEnemyContact(Enemy encounteredEnemy);
    public void onEnemyDefeat();
}
