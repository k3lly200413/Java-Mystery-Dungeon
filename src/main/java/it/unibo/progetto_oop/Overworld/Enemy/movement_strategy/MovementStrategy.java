package it.unibo.progetto_oop.overworld.enemy.movement_strategy;

import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.overworld.player.Player;

public interface MovementStrategy {
    /**
     * Move the enemy.
     * @param context the enemy to move
     * @param player the player
     * @param currDirection the current direction of movement
     * @return the new direction of movement
     */
    MoveDirection executeMove(Enemy context, Player player,
        MoveDirection currDirection);
}
