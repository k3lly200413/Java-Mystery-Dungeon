package it.unibo.progetto_oop.overworld.grid_notifier;

import it.unibo.progetto_oop.overworld.playGround.Data.Position;

@FunctionalInterface
public interface ListEnemyUpdater {
    void onEnemyRemoved(Position at);
}
