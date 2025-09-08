package it.unibo.progetto_oop.overworld.playground.data;

public interface GridUpdater {
    void onPlayerMove(Position from, Position to);
    void onEnemyMove(Position from, Position to);
    void onItemRemoved(Position at);
    void onEnemyRemoved(Position at);
}
