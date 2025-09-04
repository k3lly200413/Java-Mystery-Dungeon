package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;

public interface CombatCollision {
    public boolean checkCombatCollision(Position player, Position enemy);

    public void initiateCombatTransition(Enemy enemy);

}
