package it.unibo.progetto_oop.overworld.enemy.state_pattern;

import it.unibo.progetto_oop.overworld.enemy.EnemyType;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementStrategy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementUtil;

/**
 * Represents the follower state for an enemy.
 */
public class FollowerState extends PatrollerState {

    /**
     * Constructor for FollowerState.
     *
     * @param newMovementUtil the movement utility
     * @param newMovementStrategy the movement strategy
     * @param vertical whether the follower moves vertically
     */
    public FollowerState(
        final MovementUtil newMovementUtil,
        final MovementStrategy newMovementStrategy,
        final boolean vertical) {
        super(newMovementUtil, newMovementStrategy, vertical);
    }

    /* Initially the follower will act like a patroller.*/

    /**
     * returns the type of the enemy.
     * 
     * @return the enemy type (FOLLOWER)
     */
    @Override
    public EnemyType getType() {
        return EnemyType.FOLLOWER;
    }

    /**
     * Get a description of the enemy's current state.
     * 
     * @return the state description
     */
    @Override
    public final String getDescription() {
        return "Follower State";
    }
}
