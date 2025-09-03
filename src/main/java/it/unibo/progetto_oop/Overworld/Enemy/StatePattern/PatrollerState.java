package it.unibo.progetto_oop.Overworld.Enemy.StatePattern;

import it.unibo.progetto_oop.Overworld.Enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementStrategy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class PatrollerState implements GenericEnemyState {
    private MoveDirection currentDirection;
    private final MovementUtil movementUtil; 
    private final boolean isVertical;
    private final MovementStrategy movementStrategy;

    public PatrollerState(MovementUtil movementUtil, MovementStrategy movementStrategy, boolean isVertical){
        this.movementUtil = movementUtil;
        this.isVertical = isVertical;
        this.movementStrategy = movementStrategy;
    }

    @Override
    public void enterState(Enemy context) {
        currentDirection = movementUtil.getInitialGeneralMoveDirection(context.getCurrentPosition(), context.getWalls(), this.isVertical);
        if (this.currentDirection == MoveDirection.NONE){
            this.currentDirection = this.isVertical ? MoveDirection.DOWN : MoveDirection.UP;
        }
    }

    @Override
    public void exitState(Enemy context) {
        System.out.println("Exiting Patrol State");
    }

    @Override
    public void update(Enemy context, Player player) {
        this.currentDirection = this.movementStrategy.executeMove(context, player, this.currentDirection);
    }

    @Override
    public void onPlayerMoved(Enemy context, Player player) {}

    @Override
    public EnemyType getType() {
        return EnemyType.PATROLLER;
    }
    
}