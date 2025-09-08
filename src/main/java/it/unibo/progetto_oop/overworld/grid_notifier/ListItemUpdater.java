package it.unibo.progetto_oop.overworld.grid_notifier;

import it.unibo.progetto_oop.overworld.playGround.data.Position;

@FunctionalInterface
public interface ListItemUpdater {
    void onItemRemoved(Position at);
}
