package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy;

import it.unibo.progetto_oop.Overworld.Enemy.Enemy;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.MovementUtil.MoveDirection;;

public interface MovementStrategy {
    MoveDirection executeMove(Enemy enemy, OverworldModel model, MoveDirection currDirection); // TODO: need to add a parameter for game application
}
