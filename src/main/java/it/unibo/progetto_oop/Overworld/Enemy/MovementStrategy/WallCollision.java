package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy;

import java.util.Optional;
import java.util.stream.IntStream;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;

// singleton to get walls from anywhere it's needed
public final class WallCollision {
    private static StructureData gridView;   // read-only

    private WallCollision() {}

    public static void setWalls(StructureData newGridView) {
        gridView = newGridView;
    }

    public static boolean inBounds(final Position p) {
        if (gridView == null) return false;
        return p.x() >= 0 && p.y() >= 0 && p.x() < gridView.width() && p.y() < gridView.height();
    }

    // Regola entrabile = inBounds && non WALL
    public static boolean canEnter(final Position to) {
        if (!inBounds(to))
            return false;
        return gridView.get(to.x(), to.y()) != TileType.WALL;
    }

    public static boolean canEnemyEnter(final Position to) {
        return canEnter(to) && gridView.get(to.x(), to.y()) != TileType.TUNNEL;
    }

    /* 
    public static Optional<Position> closestWall(final Position from, int dx, int dy){
        int x = from.x();
        int y = from.y();

        int lastX = x;
        int lastY = y;

        while (true) {
            x += dx;
            y += dy;

            if (!inBounds(new Position(x, y))) {
                break; 
            }

            // have we found a wall?
            if (gridView.get(x, y) == TileType.WALL) {
                return Optional.of(new Position(x, y));
            }

            lastX = x;
            lastY = y;
        }

        return Optional.empty();
    }*/

    
    public static Optional<Position> closestWall(Position from, int dx, int dy) {
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