package it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;

import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.StructureData;
import it.unibo.progetto_oop.overworld.playground.data.TileType;

public final class WallCollisionImpl implements WallCollision {

    /**
     * the grid representing the base structure (walls, floors, etc.).
     */
    private StructureData baseGrid;

    /**
     * the grid representing entities (player, enemies, items, etc.).
     */
    private StructureData entityGrid;

    /**
     * Constructor for WallCollisionImpl.
     * @param newBaseGrid the base grid structure
     * @param newEntityGrid the entity grid structure
     */
    public WallCollisionImpl(
    final StructureData newBaseGrid, final StructureData newEntityGrid) {
        this.baseGrid   = Objects.requireNonNull(
            newBaseGrid, "baseGrid cannot be null");
        this.entityGrid = Objects.requireNonNull(
            newEntityGrid, "entityGrid cannot be null");
    }

    @Override
    public void setGrid(final StructureData newGridView) {
        this.baseGrid = Objects.requireNonNull(
            newGridView, "baseGrid cannot be null");
    }

    @Override
    public void setEntityGrid(final StructureData newEntityGrid) {
        this.entityGrid = Objects.requireNonNull(
            newEntityGrid, "entityGrid cannot be null");
    }

    @Override
    public boolean inBounds(final Position p) {
        return p.x() >= 0 && p.y() >= 0
            && p.x() < baseGrid.width()
            && p.y() < baseGrid.height();
    }

    /**
     * Check if a position can be entered by the player.
     * @param to the position to check
     * @return true if the position can be entered, false otherwise
     */
    @Override
    public boolean canEnter(final Position to) {
        if (!inBounds(to)) {
            return false;
        }
        final TileType t = baseGrid.get(to.x(), to.y());
        final TileType eg = entityGrid.get(to.x(), to.y());
        return (t != TileType.WALL) && (eg != TileType.ENEMY);
    }

    /**
     * Check if an enemy can enter a specific position.
     * @param to the position to check
     * @return true if the enemy can enter the position, false otherwise
     */
    @Override
    public boolean canEnemyEnter(final Position to) {
        if (!canEnter(to)) {
            return false;
        }
        // already not a wall and not occupied by an enemy

        final TileType t = baseGrid.get(to.x(), to.y());
        final TileType eg = entityGrid.get(to.x(), to.y());

        // also not an entity and not stairs
        return (eg == TileType.NONE) && (t != TileType.STAIRS);
    }

    /**
     * Finds the closest wall in the specified direction from a given position.
     * @param from the starting position
     * @param dx the x direction (-1, 0, 1)
     * @param dy the y direction (-1, 0, 1)
     * @return an Optional containing the position
     * of the closest wall if found, otherwise an empty
     */
    @Override
    public Optional<Position> closestWall(final Position from,
    final int dx, final int dy) {
        final int maxSteps;
        ToIntFunction<Position> axisGetter;
        final int startX;
        final int startY;

        // if i move orizontally i'll be interested with the width
        if (dx != 0) {
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
                .mapToObj(step ->
                    new Position(startX + step * dx, startY + step * dy))
                .filter(this::inBounds) // only in bounds positions
                // i'm filtering all tipes of "obstacles"
                .filter(pos -> baseGrid.get(pos.x(), pos.y()) == TileType.WALL
                    || baseGrid.get(pos.x(), pos.y()) == TileType.ITEM
                    || baseGrid.get(pos.x(), pos.y()) == TileType.TUNNEL
                    || baseGrid.get(pos.x(), pos.y()) == TileType.STAIRS)
                .min(Comparator.comparingInt(wallPos ->
                        calculateDistanceOnAxis(from, wallPos, axisGetter)
                ));
    }

    /**
    * Calculates the distance between two positions on a specific axis.
    * @param p1 the first position
    * @param p2 the second position
    * @param getCoordinate a function to extract the coordinate
    * from a position (e.g., Position::getX or Position::getY)
    * @return the distance between the two positions on the specified axis
    */
    private int calculateDistanceOnAxis(final Position p1, final Position p2,
    final ToIntFunction<Position> getCoordinate) {
        return Math.abs(
            getCoordinate.applyAsInt(p1) - getCoordinate.applyAsInt(p2));
    }
}
