package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;

public final class WallCollisionImpl implements WallCollision {

    private StructureData baseGrid;
    private StructureData entityGrid;

    public WallCollisionImpl(final StructureData baseGrid, final StructureData entityGrid) {
        this.baseGrid   = Objects.requireNonNull(baseGrid, "baseGrid cannot be null");
        this.entityGrid = Objects.requireNonNull(entityGrid, "entityGrid cannot be null");
    }

    @Override
    public void setGrid(final StructureData newGridView) {
        this.baseGrid = Objects.requireNonNull(newGridView, "baseGrid cannot be null");
    }

    @Override
    public void setEntityGrid(final StructureData newEntityGrid) {
        this.entityGrid = Objects.requireNonNull(newEntityGrid, "entityGrid cannot be null");
    }

    @Override
    public boolean inBounds(final Position p) {
        return p.x() >= 0 && p.y() >= 0
            && p.x() < baseGrid.width()
            && p.y() < baseGrid.height();
    }

    // Passo valido per tutti
    @Override
    public boolean canEnter(final Position to) {
        if (!inBounds(to))
            return false;
        final TileType t = baseGrid.get(to.x(), to.y());
        return t == TileType.ROOM || t == TileType.TUNNEL || t == TileType.STAIRS;
    }

    // Passo valido per i nemici
    @Override
    public boolean canEnemyEnter(final Position to) {
        if (!canEnter(to))
            return false;
        final TileType occ = entityGrid.get(to.x(), to.y());
        return occ == TileType.NONE || occ == TileType.PLAYER;
    }

    @Override
    public Optional<Position> closestWall(Position from, int dx, int dy) {
        int maxSteps;
        ToIntFunction<Position> axisGetter;
        int startX;
        int startY;

        if (dx!=0){ // if i move orizontally i'll be interested with the width
            maxSteps = baseGrid.width();
            axisGetter = Position :: x;

            startX = 0;
            startY = from.y(); // will remain the same

        } else { // same as above but with height
            maxSteps = baseGrid.height();
            axisGetter = Position :: y;

            startX = from.x(); // will remain the same
            startY = 0;
        }

        // test right or down
        return IntStream.rangeClosed(0, maxSteps + 1)
                .mapToObj(step -> new Position(startX + step * dx, startY+ step * dy))
                .filter(pos -> inBounds(pos)) // only in bounds positions
                // i'm filtering all tipes of "obstacles"
                .filter(pos -> baseGrid.get(pos.x(), pos.y()) == TileType.WALL
                    || entityGrid.get(pos.x(), pos.y()) == TileType.ITEM
                    || baseGrid.get(pos.x(), pos.y()) == TileType.TUNNEL
                    || baseGrid.get(pos.x(), pos.y()) == TileType.STAIRS)
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
