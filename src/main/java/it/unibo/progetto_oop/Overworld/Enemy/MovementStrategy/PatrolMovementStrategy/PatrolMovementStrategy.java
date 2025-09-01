package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.PatrolMovementStrategy;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementStrategy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.VisibilityUtil;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.CombatTransitionState;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import java.util.Set;



public class PatrolMovementStrategy implements MovementStrategy{
    private MoveDirection moveDirection; // The direction of this patrol movement
    private VisibilityUtil visibilityUtil;

    // costants
    private final static int NEIGHBOUR_DISTANCE = 4; // Example value, adjust as needed
    private final static int COMBAT_DISTANCE = 1; // Example value, adjust as needed

    public PatrolMovementStrategy() {
        this.visibilityUtil = new VisibilityUtil();
    }
    

    @Override
    public MoveDirection executeMove(Enemy context, Player player, MoveDirection currDirection) {
        Position currentPos = context.getCurrentPosition();
        Set<Position> walls = context.getWalls();
        Position targetPos = currentPos; // Initialize target position to current position

        this.moveDirection = currDirection; // Set the current direction

        switch (moveDirection) {
            case UP:
                targetPos = new Position(currentPos.x(), currentPos.y() - 1);
                break;
            case DOWN:
                targetPos = new Position(currentPos.x(), currentPos.y() + 1);
                break;
            case LEFT:
                targetPos = new Position(currentPos.x() -1, currentPos.y());
                break;
            case RIGHT:
                targetPos = new Position(currentPos.x() + 1, currentPos.y());
                break;
            case NONE:
                System.out.println("No direction given ");
                break;
            default:
                break;
        }
        
        // Check if the target position is not the same as the current position and is not a wall
        if (!targetPos.equals(currentPos) && !walls.contains(targetPos)) {
            context.setPosition(targetPos);
            context.getGridNotifier().notifyEnemyMoved(currentPos, targetPos);

            if (player.getPosition().equals(targetPos) || player.getPosition().equals(currentPos)){
                context.setState( new CombatTransitionState(context.getState()));
            }
            return this.moveDirection;
        } 
        // if it's impossible to move in the current direction, reverse it
        else {
            System.out.println("Patrol: Hit wall or no move for " + context + " at " + targetPos + ". Reversing direction.");
            this.moveDirection = reverseDirection(this.moveDirection); 
            System.out.println("Patrol: New direction for " + context + " is " + this.moveDirection);
        }
        return this.moveDirection;
    }

    @Override
    public MoveDirection executeFollowMove(Enemy context, Player player, MoveDirection currentDirection) {
        Position currentPos = context.getCurrentPosition();
        Position targetPos = this.visibilityUtil.firstMove(context.getCurrentPosition(), player.getPosition());

        Set<Position> walls = context.getWalls();

        // if the player is in the enemy's line of sight, move towards the player
        if ( player != null 
            && this.visibilityUtil.inLos(context.getCurrentPosition(), player.getPosition(), walls, NEIGHBOUR_DISTANCE)
            && !walls.contains(targetPos)){

            // if the player and the enemy are close enough -> enter combat state
            if (this.visibilityUtil.neighbours(context.getCurrentPosition(), player.getPosition(), COMBAT_DISTANCE)){

                context.setState(new CombatTransitionState(context.getState()));

                targetPos = player.getPosition();
                context.setPosition(targetPos);
            } else {
                context.setPosition(targetPos); // not close enough -> move closer towards the player
            }

            context.getGridNotifier().notifyEnemyMoved(currentPos, targetPos);
            return currentDirection; 

        } else{ // else, continue patrolling
            return this.executeMove(context, player, currentDirection);
        }
    }

    private MoveDirection reverseDirection(MoveDirection dir) {
        switch (dir) {
            case UP:    
                return MoveDirection.DOWN;
            case DOWN:  
                return MoveDirection.UP;
            case LEFT:  
                return MoveDirection.RIGHT;
            case RIGHT: 
                return MoveDirection.LEFT;
            default:    
                return MoveDirection.NONE; 
        }
    }

    
    
}
