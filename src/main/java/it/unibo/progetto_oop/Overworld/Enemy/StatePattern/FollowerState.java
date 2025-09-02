package it.unibo.progetto_oop.Overworld.Enemy.StatePattern;

import it.unibo.progetto_oop.Overworld.Enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.combat.position.Position;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.*;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil.MoveDirection;


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
        System.out.println("Entering PatrolState");
        currentDirection = movementUtil.getInitialGeneralMoveDirection(context.getCurrentPosition(), context.getWalls(), this.isVertical);
        if (this.currentDirection == MoveDirection.NONE){
            this.currentDirection = this.isVertical ? MoveDirection.DOWN : MoveDirection.UP;
        }
    }

    @Override
    public void exitState(Enemy context) {
        System.out.println("Entering FollowerState");
    }

    @Override
    public void update(Enemy context, Player player) {
        this.currentDirection = this.movementStrategy.executeFollowMove(context, player, this.currentDirection);
    }

    @Override
    public void onPlayerMoved(Enemy context, Player player) {}

    @Override
    public EnemyType getType() {
        return EnemyType.FOLLOWER;
    }

}
