package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Overworld.ViewManagerObserver.ViewManagerObserver;

public interface CombatCollision {
    public boolean checkCombatCollision(Position player, Position enemy);

    public void initiateCombatTransition(Enemy enemy, Player player);

    public void setInCombat(boolean inCombat);

    public void setViewManagerListener(ViewManagerObserver curranteViewManagerObserver);

    public void showOverworld();

}
