package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.ToIntFunction;
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
        TileType tyle = gridView.get(to.x(), to.y());
        return tyle != TileType.WALL && tyle != TileType.ENEMY;
    }

    @Override
    public boolean canEnemyEnter(final Position to) {
        if (!inBounds(to))
            return false;
        return gridView.get(to.x(), to.y()) == TileType.ROOM;
    }
    
    @Override
    public Optional<Position> closestWall(Position from, int dx, int dy) {
        int maxSteps;
        ToIntFunction<Position> axisGetter;
        int startX;
        int startY;

        if (dx!=0){ // if i move orizontally i'll be interested with the width
            maxSteps = gridView.width();
            axisGetter = Position :: x;

            startX = 0;
            startY = from.y(); // will remain the same

        } else { // same as above but with height
            maxSteps = gridView.height();
            axisGetter = Position :: y;

            startX = from.x(); // will remain the same
            startY = 0;
        }

        // test right or down
        return IntStream.rangeClosed(0, maxSteps + 1)
                .mapToObj(step -> new Position(startX + step * dx, startY+ step * dy))
                .filter(pos -> inBounds(pos)) // only in bounds positions
                // i'm filtering all tipes of "obstacles"
                .filter(pos -> gridView.get(pos.x(), pos.y()) == TileType.WALL 
                    || gridView.get(pos.x(), pos.y()) == TileType.ITEM 
                    || gridView.get(pos.x(), pos.y()) == TileType.TUNNEL
                    || gridView.get(pos.x(), pos.y()) == TileType.STAIRS)
                .min(Comparator.comparingInt(wallPos ->
                        calculateDistanceOnAxis(from, wallPos, axisGetter) // i want the closest
                ));
    }

    /**
    * Calculates the distance between two positions on a specific axis.
    * @param p1 the first position
    * @param p2 the second position
    * @param getCoordinate a function to extract the coordinate from a position (e.g., Position::getX or Position::getY)
    * @return the distance between the two positions on the specified axis
    */
    private int calculateDistanceOnAxis(Position p1, Position p2, ToIntFunction<Position> getCoordinate) {
        return Math.abs(getCoordinate.applyAsInt(p1) - getCoordinate.applyAsInt(p2));
    }
    
}