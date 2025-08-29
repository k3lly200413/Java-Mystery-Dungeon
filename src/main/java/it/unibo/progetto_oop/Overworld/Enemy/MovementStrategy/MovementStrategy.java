package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy;

<<<<<<< HEAD
import it.unibo.progetto_oop.Overworld.MVC.viewManager;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
=======
import it.unibo.progetto_oop.Overworld.Player.Player;

import java.util.Set;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
>>>>>>> c10be239e276a0e972e842f268004520fb4bd541
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil.MoveDirection;;

// TODO: change GameApplication to a more generic type 

public interface MovementStrategy {
    /**
     * Move the enemy
     * @param enemy the enemy to move
     * @param model the overworld model
     * @param game the game application
     * @param currDirection the current direction of movement
     * @return the new direction of movement
     */
<<<<<<< HEAD
    MoveDirection executeMove(Enemy enemy, OverworldModel model, viewManager game, MoveDirection currDirection); // TODO: need to add a parameter for game application
=======
    MoveDirection executeMove(Enemy enemy,Set<Position> walls, Player player, MoveDirection currDirection); // TODO: need to add a parameter for game application
>>>>>>> c10be239e276a0e972e842f268004520fb4bd541
}
