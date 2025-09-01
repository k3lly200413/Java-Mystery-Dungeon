package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy;

import it.unibo.progetto_oop.Overworld.Player.Player;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil.MoveDirection;

public interface MovementStrategy {
    /**
     * Move the enemy
     * @param enemy the enemy to move
     * @param player the player
     * @param currDirection the current direction of movement
     * @return the new direction of movement
     */
    MoveDirection executeMove(Enemy enemy, Player player, MoveDirection currDirection); 

    /**
     * Move the enemy in the follower state
     * @param enemy the enemy to move
     * @param player the player
     * @param currDirection the current direction of movement
     * @return the new direction of movement
     */
    MoveDirection executeFollowMove(Enemy context, Player player, MoveDirection currentDirection);
}
