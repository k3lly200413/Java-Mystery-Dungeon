package it.unibo.progetto_oop.overworld.PlayGround.Data;

import java.util.Objects;

public final class EntityGridUpdater implements GridUpdater {
    private final StructureData entity;

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
        if (entity.get(from.x(), from.y()) == TileType.ENEMY) {
            entity.set(from.x(), from.y(), TileType.NONE);
        }
        entity.set(to.x(), to.y(), TileType.ENEMY);
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
