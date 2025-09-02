package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision;

import java.util.Optional;
import java.util.stream.IntStream;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;


public final class WallCollisionImpl implements WallCollision{
    private StructureData gridView;   // read-only

    public WallCollisionImpl(StructureData newGridView) {
        gridView = newGridView;
    }

    @Override
    public void setGrid(StructureData newGridView) {
        gridView = newGridView;
    }

    @Override
    public boolean inBounds(final Position p) {
        if (gridView == null) return false;
        return p.x() >= 0 && p.y() >= 0 && p.x() < gridView.width() && p.y() < gridView.height();
    }

    // Regola entrabile = inBounds && non WALL
    @Override
    public boolean canEnter(final Position to) {
        if (!inBounds(to))
            return false;
        return gridView.get(to.x(), to.y()) != TileType.WALL;
    }

    @Override
    public boolean canEnemyEnter(final Position to) {
        return canEnter(to) && gridView.get(to.x(), to.y()) != TileType.TUNNEL;
    }
    
    @Override
    public Optional<Position> closestWall(Position from, int dx, int dy) {
        int maxSteps;

        if (dx!=0){ // if i move orizontally i'll be interested with the width
            maxSteps = gridView.width();
        } else { // same as above but with height
            maxSteps = gridView.height();
        }

        return IntStream.rangeClosed(1, maxSteps)
                .mapToObj(step -> new Position(from.x() + step * dx, from.y() + step * dy))
                .filter(pos -> inBounds(pos)) // only in bounds positions
                .filter(pos -> gridView.get(pos.x(), pos.y()) == TileType.WALL) // i'm searching for walls
                .findFirst(); // the first wall i find
    }
    
}