package it.unibo.progetto_oop.Overworld.Enemy.StatePattern;

import it.unibo.progetto_oop.Overworld.Enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.Player.Player;

public interface GenericEnemyState {
    public void enterState();
    public void update();
    public void exitState();
    public void onPlayerMoved();
    public EnemyType getType();
}

