package it.unibo.progetto_oop.Overworld.Enemy.StatePattern;

import java.util.Set;

import it.unibo.progetto_oop.Overworld.Enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.MVC.viewManager;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.*;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil.MoveDirection;


public class FollowerState implements GenericEnemyState{
    private MoveDirection currentDirection;
    private final VisibilityUtil visibilityUtil; 
    private final MovementUtil movementUtil;
    private final MovementStrategy movementStrategy;
    private final boolean isVertical;
    private final viewManager game;
    
    // costants
    private final static int NEIGHBOUR_DISTANCE = 4; // Example value, adjust as needed
    private final static int COMBAT_DISTANCE = 1; // Example value, adjust as needed

    public FollowerState(VisibilityUtil visibilityUtil, MovementUtil movementUtil, MovementStrategy movementStrategy, boolean isVertical, viewManager game){
        this.visibilityUtil = visibilityUtil;
        this.movementUtil = movementUtil;
        this.isVertical = isVertical;
        this.movementStrategy = movementStrategy;
        this.game = game;
    }

    /** 
     * initially the follower acts like a patroller
     */
    @Override
    public void enterState(Enemy context, OverworldModel model) {
        System.out.println("Entering PatrolState");
        currentDirection = movementUtil.getInitialGeneralMoveDirection(context.getCurrentPosition(), model.getWalls(), this.isVertical);
        if (this.currentDirection == MoveDirection.NONE){
            this.currentDirection = this.isVertical ? MoveDirection.DOWN : MoveDirection.UP;
        }
    }

    @Override
    public void exitState(Enemy context) {
        System.out.println("Entering FollowerState");
    }

    @Override
    public void update(Enemy enemy, OverworldModel model, Player player) {
        Position nextPos = this.visibilityUtil.firstMove(enemy.getCurrentPosition(), player.getPosition());

        // if the player is in the enemy's line of sight, move towards the player
        if ( player != null 
            && this.visibilityUtil.inLos(enemy.getCurrentPosition(), player.getPosition(), model, NEIGHBOUR_DISTANCE)
            && !model.getWalls().contains(nextPos)){

            // if the player and the enemy are close enough -> enter combat state
            if (this.visibilityUtil.neighbours(enemy.getCurrentPosition(), player.getPosition(), COMBAT_DISTANCE)){
                enemy.setState(new CombatTransitionState(game, enemy.getState()), model);
                enemy.setPosition(player.getPosition());
            } else {
                enemy.setPosition(nextPos); // not close enough -> move closer towards the player
            }
        } else{ // else, continue patrolling
            this.currentDirection = this.movementStrategy.executeMove(enemy, model, this.game, this.currentDirection);
        }
    }

    @Override
    public void onPlayerMoved(Enemy context, Player player, OverworldModel model) {}

    @Override
    public EnemyType getType() {
        return EnemyType.FOLLOWER;
    }
    
}
