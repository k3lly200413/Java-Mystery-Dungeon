package it.unibo.progetto_oop.Overworld.Enemy.StatePattern;

import java.util.Set;

import it.unibo.progetto_oop.Overworld.Enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementStrategy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.Overworld.MVC.OverworldApplication;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Combat.Position.Position;

public class PatrollerState implements GenericEnemyState {
    private MoveDirection currentDirection;
    private final MovementUtil movementUtil; 
    private final boolean isVertical;
    private final MovementStrategy movementStrategy;
    private final OverworldApplication game;

    public PatrollerState(MovementUtil movementUtil, MovementStrategy movementStrategy, boolean isVertical, OverworldApplication game){
        this.movementUtil = movementUtil;
        this.isVertical = isVertical;
        this.movementStrategy = movementStrategy;
        this.game = game;
    }

    @Override
    public void enterState(Enemy context, OverworldModel model) {
        currentDirection = movementUtil.getInitialGeneralMoveDirection(context.getCurrentPosition(), model.getWalls(), this.isVertical);
        if (this.currentDirection == MoveDirection.NONE){
            this.currentDirection = this.isVertical ? MoveDirection.DOWN : MoveDirection.UP;
        }
    }

    @Override
    public void exitState(Enemy context) {
        System.out.println("Exiting Patrol State");
    }

    @Override
    public void update(Enemy enemy, OverworldModel model, Player player) {
        this.currentDirection = this.movementStrategy.executeMove(enemy, model, this.game, this.currentDirection);
    }

    @Override
    public void onPlayerMoved(Enemy context, Player player, OverworldModel model) {}

    @Override
    public EnemyType getType() {
        return EnemyType.PATROLLER;
    }
    
}
