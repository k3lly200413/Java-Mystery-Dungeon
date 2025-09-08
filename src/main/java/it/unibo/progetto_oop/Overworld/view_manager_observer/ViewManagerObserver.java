package it.unibo.progetto_oop.overworld.view_manager_observer;

import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;

public interface ViewManagerObserver {
    public void onPlayerEnemyContact(Enemy encounteredEnemy);
    public void onEnemyDefeat();
}
