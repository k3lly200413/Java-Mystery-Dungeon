package it.unibo.progetto_oop.overworld.playground.data.listner.grid_updater;

import java.util.Objects;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.StructureData;

/**
 * Implementation of GridUpdater that updates a StructureData grid based on entity movements and removals.
 */
@SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "intentional to allow external modification of the Structure"
        + " Need to modify the entity grid. Only internal use."
    )
public final class EntityGridUpdater implements GridUpdater {
    /**
     * The StructureData instance representing the grid to be updated.
     */
    private final StructureData entity;

    /**
     * Constructs an EntityGridUpdater with the specified StructureData.
     *
     * @param entityGrid the StructureData to be updated, must not be null
     */
    public EntityGridUpdater(final StructureData entityGrid) {
        this.entity = Objects.requireNonNull(entityGrid);
    }

    @Override
    public void onPlayerMove(final Position from, final Position to) {
        entity.set(from.x(), from.y(), TileType.NONE);
        entity.set(to.x(), to.y(), TileType.PLAYER);
    }

    @Override
    public void onEnemyMove(final Position from, final Position to) {
        final TileType tmp = entity.get(from.x(), from.y());
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
