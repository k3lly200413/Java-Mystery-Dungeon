package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.Player.Player;

public interface CombatCollision {
    /**
     * check if the player is close enough to the enemy.
     * @param player the position of the player
     * @param enemy the position of the enemy
     * @return true if the player is close enough to the enemy, false otherwise
     */
    boolean checkCombatCollision(Position player, Position enemy);


    /**
     * initiate the combat transition between the player and the enemy.
     * @param enemy the enemy that will enter combat
     * @param player the player that will enter combat
     */
    void initiateCombatTransition(Enemy enemy, Player player);

    /**
     * set the inCombat flag.
     * @param inCombat true if the enemy is in combat, false otherwise
    */
    void setInCombat(boolean inCombat);

}
