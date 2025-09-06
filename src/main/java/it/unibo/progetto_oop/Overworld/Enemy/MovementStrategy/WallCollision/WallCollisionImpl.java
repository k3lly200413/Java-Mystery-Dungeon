package it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision;

import java.util.Objects;
import java.util.Optional;

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
    public Optional<Position> closestWall(final Position from, final int dx, final int dy) {
        if ((dx == 0 && dy == 0) || !inBounds(from)) {
            return Optional.empty();
        }
        final int sx = Integer.signum(dx);  //normalize -1, 0, +1
        final int sy = Integer.signum(dy);

        int x = from.x();
        int y = from.y();
        while (true) {
            x += sx;
            y += sy;
            final Position p = new Position(x, y);
            if (!inBounds(p)) {
                return Optional.empty();
            }
            if (baseGrid.get(x, y) == TileType.WALL) {
                return Optional.of(p);
            }
        }
    }
}
