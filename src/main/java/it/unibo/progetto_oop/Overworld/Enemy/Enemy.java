package it.unibo.progetto_oop.Overworld.Enemy;

import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.GenericEnemyState;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Combat.Position.Position;

public interface Enemy {
    public int getCurrentHealth();
    public int getMaxHealth();
    public int getPower();
    public EnemyType getState();
    
    public void takeTurn(Player player);
    
    public Position getPosition();
    public void setPosition(Position newPosition);
    public Position getCurrentPosition();
    
    public void setState(GenericEnemyState newState);
}
