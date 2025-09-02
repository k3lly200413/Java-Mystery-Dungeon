package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementStrategyImpl;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementStrategy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision.WallCollision;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.VisibilityUtil;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.CombatTransitionState;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class FollowMovementStrategy implements MovementStrategy{
    private VisibilityUtil visibilityUtil;
    private PatrolMovementStrategy patrolMovementStrategy;
    WallCollision checker;

    // costants
    private final static int NEIGHBOUR_DISTANCE = 6; // Example value, adjust as needed
    private final static int COMBAT_DISTANCE = 1; // Example value, adjust as needed

    public FollowMovementStrategy(WallCollision checker) {
        this.visibilityUtil = new VisibilityUtil(checker);
        this.patrolMovementStrategy = new PatrolMovementStrategy(checker);
        this.checker = checker;
    }

    @Override
    public MoveDirection executeMove(Enemy context, Player player, MoveDirection currDirection) {
        Position currentPos = context.getCurrentPosition();
        Position targetPos = this.visibilityUtil.firstMove(context.getCurrentPosition(), player.getPosition());

        Position playerPos = player.getPosition();

        // if the player is in the enemy's line of sight, move towards the player
        if ( player != null 
            && this.visibilityUtil.inLos(currentPos, playerPos, NEIGHBOUR_DISTANCE)
            && checker.canEnemyEnter(targetPos)){

            // if the player and the enemy are close enough -> enter combat state
            /*if (this.visibilityUtil.neighbours(context.getCurrentPosition(), playerPos, COMBAT_DISTANCE)){

                context.setState(new CombatTransitionState(context.getState()));

                targetPos = player.getPosition();
                context.setPosition(targetPos);
            }*/ 
            if (targetPos != playerPos ){
                context.setPosition(targetPos); // not close enough -> move closer towards the player
            } 
            context.getGridNotifier().notifyEnemyMoved(currentPos, targetPos);
            return currDirection; 

        } else{ // else, continue patrolling
            return patrolMovementStrategy.executeMove(context, player, currDirection);
        }
    }
    
}
