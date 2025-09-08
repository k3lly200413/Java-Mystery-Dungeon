package it.unibo.progetto_oop.Overworld.ViewManagerObserver;

import it.unibo.progetto_oop.Overworld.enemy.creation_pattern.factory_impl.Enemy;

public interface ViewManagerObserver {
    public void onPlayerEnemyContact(Enemy encounteredEnemy);
    public void onEnemyDefeat();
}
