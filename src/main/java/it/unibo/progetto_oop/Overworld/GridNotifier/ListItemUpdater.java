package it.unibo.progetto_oop.Overworld.GridNotifier;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;

@FunctionalInterface
public interface ListItemUpdater {
    void onItemRemoved(Position at);
}
