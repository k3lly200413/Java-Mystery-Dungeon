package it.unibo.progetto_oop.overworld.playground.dungeon_logic;

import java.util.List;
import java.util.Objects;

import it.unibo.progetto_oop.overworld.playground.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ImplArrayListStructureData;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ReadOnlyGrid;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ReadOnlyGridAdapter;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.StructureData;

/**
 * Represents a dungeon floor with its grid structure, entity grid, and rooms.
 */
public final class Floor {
    /**
     * The grid representing the structure of the floor.
     */
    private final StructureData grid;

    /**
     * The grid representing entities on the floor.
     */
    private final StructureData entityGrid;

    /**
     * The list of rooms present on the floor.
     */
    private final List<Room> rooms;

    /**
     * Constructs a Floor instance with the given configuration, generator,
     * and final floor flag.
     *
     * @param conf       the configuration for the floor
     * @param gen        the generator used to create the floor
     * @param finalFloor whether this is the final floor
     */
    public Floor(final FloorConfig conf, final FloorGenerator gen,
            final boolean finalFloor) {
        Objects.requireNonNull(gen);

        this.entityGrid = new ImplArrayListStructureData(
                conf.width(), conf.height());
        this.entityGrid.fill(TileType.NONE);
        this.grid = new ImplArrayListStructureData(
                conf.width(), conf.height()); // today ArrayListGrid; then change here

        this.rooms = List.copyOf(
                gen.generate(grid, entityGrid, conf, finalFloor)); // Immutable list of rooms
    }

    /**
     * Returns the grid representing the structure of the floor.
     *
     * @return the grid of the floor
     */
    public ReadOnlyGrid grid() {
        return ReadOnlyGridAdapter.of(grid);
    }

    /**
     * Returns the grid representing entities on the floor.
     *
     * @return the entity grid of the floor
     */
    public ReadOnlyGrid entityGrid() {
        return ReadOnlyGridAdapter.of(entityGrid);
    }

    /**
     * Returns the list of rooms present on the floor.
     *
     * @return an immutable list of rooms
     */
    public List<Room> rooms() {
        return rooms;
    }
}
