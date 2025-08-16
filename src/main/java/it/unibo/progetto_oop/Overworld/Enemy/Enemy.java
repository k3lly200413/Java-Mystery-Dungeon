package it.unibo.progetto_oop.Overworld.Enemy;

import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Combat.Position.Position;

public interface Enemy {
    public int getCurrentHealth();
    public int getMaxHealth();
    public int getPower();
    public Position getPosition();
    public void setPosition(Position newPosition);
    public void takeTurn(Player player);
    public EnemyType getEnemyType();
}
