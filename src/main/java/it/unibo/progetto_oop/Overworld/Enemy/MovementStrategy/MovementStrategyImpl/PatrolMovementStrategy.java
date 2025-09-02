package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementStrategyImpl;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementStrategy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision.WallCollision;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.CombatTransitionState;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;



public class PatrolMovementStrategy implements MovementStrategy{
    private MoveDirection moveDirection; // The direction of this patrol movement
    WallCollision checker;

    public PatrolMovementStrategy(WallCollision checker) {
        this.checker = checker;
    }
    

    @Override
    public MoveDirection executeMove(Enemy context, Player player, MoveDirection currDirection) {
        Position currentPos = context.getCurrentPosition();
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
        if (checker.canEnemyEnter(targetPos)) {
            context.setPosition(targetPos);
            context.getGridNotifier().notifyEnemyMoved(currentPos, targetPos);

            if (player.getPosition().equals(targetPos) || player.getPosition().equals(currentPos)){
                context.setState( new CombatTransitionState(context.getState()));
            }
            return this.moveDirection;
        } 
        // if it's impossible to move in the current direction, reverse it
        else {
            this.moveDirection = reverseDirection(this.moveDirection);
        }
        return this.moveDirection;
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
