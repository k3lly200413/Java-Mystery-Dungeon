package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision;

import java.util.Optional;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;

public interface WallCollision{

    public void setGrid(StructureData newGridView);

    public boolean inBounds(final Position p);

    public boolean canEnter(final Position to);

    public boolean canEnemyEnter(final Position to);

    public Optional<Position> closestWall(Position from, int dx, int dy);

}