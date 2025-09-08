package it.unibo.progetto_oop.Overworld.ViewManagerObserver;

import it.unibo.progetto_oop.Overworld.enemy.creation_pattern.FactoryImpl.Enemy;

public interface ViewManagerObserver {
    public void onPlayerEnemyContact(Enemy encounteredEnemy);
    public void onEnemyDefeat();
}
