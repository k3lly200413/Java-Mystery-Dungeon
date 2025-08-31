package it.unibo.progetto_oop.Overworld.Enemy.StatePattern;

import java.util.Set;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.Enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.combat.position.Position;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.*;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil.MoveDirection;


public class FollowerState implements GenericEnemyState{
    private MoveDirection currentDirection;
    private final VisibilityUtil visibilityUtil; 
    private final MovementUtil movementUtil;
    private final MovementStrategy movementStrategy;
    private final boolean isVertical;
    // costants
    private final static int NEIGHBOUR_DISTANCE = 4; // Example value, adjust as needed
    private final static int COMBAT_DISTANCE = 1; // Example value, adjust as needed

    public FollowerState(VisibilityUtil visibilityUtil, MovementUtil movementUtil, MovementStrategy movementStrategy, boolean isVertical){
        this.visibilityUtil = visibilityUtil;
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
        Position nextPos = this.visibilityUtil.firstMove(context.getCurrentPosition(), player.getPosition());
        Set<Position> walls = context.getWalls();

        // if the player is in the enemy's line of sight, move towards the player
        if ( player != null 
            && this.visibilityUtil.inLos(context.getCurrentPosition(), player.getPosition(), walls, NEIGHBOUR_DISTANCE)
            && !walls.contains(nextPos)){

            // if the player and the enemy are close enough -> enter combat state
<<<<<<< HEAD
            if (this.visibilityUtil.neighbours(context.getCurrentPosition(), player.getPosition(), COMBAT_DISTANCE)){
                context.setState(new CombatTransitionState(context.getState()));
                context.setPosition(player.getPosition());
=======
            if (this.visibilityUtil.neighbours(enemy.getCurrentPosition(), player.getPosition(), COMBAT_DISTANCE)){
                enemy.setState(new CombatTransitionState(enemy.getState()), model);
                enemy.setPosition(player.getPosition());
>>>>>>> 193bbdc31a0a30b1ddfa2952e5f3c0e623bcbbaa
            } else {
                context.setPosition(nextPos); // not close enough -> move closer towards the player
            }
        } else{ // else, continue patrolling
<<<<<<< HEAD
            this.currentDirection = this.movementStrategy.executeMove(context, walls, player, this.currentDirection);
=======
            this.currentDirection = this.movementStrategy.executeMove(enemy, model, this.currentDirection);
>>>>>>> 193bbdc31a0a30b1ddfa2952e5f3c0e623bcbbaa
        }
    }

    @Override
    public void onPlayerMoved(Enemy context, Player player) {}

    @Override
    public EnemyType getType() {
        return EnemyType.FOLLOWER;
    }
    
}
