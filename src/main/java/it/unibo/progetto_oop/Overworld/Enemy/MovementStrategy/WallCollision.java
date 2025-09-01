package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy;

import java.util.HashSet;
import java.util.Set;

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
    public boolean canEnter(final Position to) {
        if (!inBounds(to))
            return false;
        return gridView.get(to.x(), to.y()) != TileType.WALL;
    }
}