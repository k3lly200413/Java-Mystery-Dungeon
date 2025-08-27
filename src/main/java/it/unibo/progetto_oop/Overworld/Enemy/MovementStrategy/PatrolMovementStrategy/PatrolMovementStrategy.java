package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.PatrolMovementStrategy;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementStrategy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.Overworld.Enemy.StatePattern.CombatTransitionState;
import it.unibo.progetto_oop.Overworld.MVC.OverworldApplication;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Combat.Position.Position;

public class PatrolMovementStrategy implements MovementStrategy{
    private MoveDirection moveDirection; // The direction of this patrol movement
    OverworldModel model;
    OverworldApplication game;
    

    @Override
    public MoveDirection executeMove(Enemy enemy, OverworldModel model, OverworldApplication game, MoveDirection currDirection) {
        Position currentPos = enemy.getCurrentPosition();
        Position targetPos = currentPos; // Initialize target position to current position
        this.moveDirection = currDirection; // Set the current direction
        this.model = model;
        this.game = game;

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
        if (!targetPos.equals(currentPos) && !model.getWalls().contains(targetPos)) {
            enemy.setPosition(targetPos);
            if (model.getPlayer().getPosition().equals(targetPos) || model.getPlayer().getPosition().equals(currentPos)){
                enemy.setState( new CombatTransitionState(this.game, enemy.getState()), this.model);
            }
            return this.moveDirection;
        } 
        // if it's impossible to move in the current direction, reverse it
        else {
            System.out.println("Patrol: Hit wall or no move for " + enemy + " at " + targetPos + ". Reversing direction.");
            this.moveDirection = reverseDirection(this.moveDirection); 
            System.out.println("Patrol: New direction for " + enemy + " is " + this.moveDirection);
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
