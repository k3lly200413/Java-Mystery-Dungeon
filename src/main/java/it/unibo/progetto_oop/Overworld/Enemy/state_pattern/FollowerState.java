package it.unibo.progetto_oop.overworld.enemy.state_pattern;

import it.unibo.progetto_oop.overworld.enemy.EnemyType;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.*;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.overworld.player.Player;


public class FollowerState implements GenericEnemyState{
    private MoveDirection currentDirection;
    private final MovementUtil movementUtil;
    private final MovementStrategy movementStrategy;
    private final boolean isVertical;

    public FollowerState(MovementUtil movementUtil, MovementStrategy movementStrategy, boolean isVertical){
        this.movementUtil = movementUtil;
        this.isVertical = isVertical;
        this.movementStrategy = movementStrategy;
    }

    /** 
     * initially the follower acts like a patroller
     */
    @Override
    public void enterState(Enemy context) {
        System.out.println("Entering Follow State");
        currentDirection = movementUtil.getInitialGeneralMoveDirection(context.getCurrentPosition(), this.isVertical);
        if (this.currentDirection == MoveDirection.NONE){
            this.currentDirection = this.isVertical ? MoveDirection.DOWN : MoveDirection.UP;
        }
    }

    @Override
    public void exitState(Enemy context) {
        System.out.println("Exiting Follow State");
    }

    @Override
    public void update(Enemy context, Player player) {
        this.currentDirection = this.movementStrategy.executeMove(context, player, this.currentDirection);
    }

    @Override
    public void onPlayerMoved(Enemy context, Player player) {}

    @Override
    public EnemyType getType() {
        return EnemyType.FOLLOWER;
    }

}
