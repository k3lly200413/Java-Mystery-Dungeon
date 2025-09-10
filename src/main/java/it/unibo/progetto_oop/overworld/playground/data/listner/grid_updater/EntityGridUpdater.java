package it.unibo.progetto_oop.overworld.playground.data.listner.grid_updater;

import java.util.Objects;

import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.data.StructureData_strategy.StructureData;

public final class EntityGridUpdater implements GridUpdater {
    /**
     * The StructureData instance representing the grid to be updated.
     */
    private final StructureData entity;

    /**
     * Constructs an EntityGridUpdater with the specified StructureData.
     * @param entityGrid the StructureData to be updated, must not be null
     */
    public EntityGridUpdater(final StructureData entityGrid) {
        this.entity = Objects.requireNonNull(entityGrid);
    }

    @Override
    public void onPlayerMove(final Position from, final Position to) {
        entity.set(from.x(), from.y(), TileType.NONE);
        entity.set(to.x(),   to.y(),   TileType.PLAYER);
    }

    @Override
    public void onEnemyMove(final Position from, final Position to) {
        TileType tmp = entity.get(from.x(), from.y());
        if (tmp == TileType.ENEMY || tmp == TileType.BOSS) {
            entity.set(from.x(), from.y(), TileType.NONE);
        }
        entity.set(to.x(), to.y(), tmp);
    }

    @Override
    public void onItemRemoved(final Position at) {
        if (entity.get(at.x(), at.y()) == TileType.ITEM) {
            entity.set(at.x(), at.y(), TileType.NONE);
        }
    }

    @Override
    public void onEnemyRemoved(final Position at) {
        if (entity.get(at.x(), at.y()) == TileType.ENEMY) {
            entity.set(at.x(), at.y(), TileType.NONE);
        }
    }
}
