package it.unibo.progetto_oop.Overworld.MVC;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;

public interface GridUpdater {
    void onPlayerMove(Position from, Position to);
    void onEnemyMove(Position from, Position to);
    void onItemRemoved(Position at);
}
