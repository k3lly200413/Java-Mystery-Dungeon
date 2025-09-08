package it.unibo.progetto_oop.overworld.enemy.movement_strategy.movement_strategy_impl;

import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementStrategy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision.CombatCollision;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision.WallCollision;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;



public class PatrolMovementStrategy implements MovementStrategy{
    private MoveDirection moveDirection; // The direction of this patrol movement
    WallCollision wallChecker;
    CombatCollision combatTransitionChecker;

    public PatrolMovementStrategy(WallCollision newWallChecker, CombatCollision newCombatTransitionChecker) {
        this.wallChecker = newWallChecker;
        this.combatTransitionChecker = newCombatTransitionChecker;
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
        if (wallChecker.canEnemyEnter(targetPos)) {
            // if close enough to the player -> combat
            if (this.combatTransitionChecker.checkCombatCollision(player.getPosition(), targetPos)){
                this.combatTransitionChecker.showCombat(context, player);
            }

            context.setPosition(targetPos);
            context.getGridNotifier().notifyEnemyMoved(currentPos, targetPos);

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
