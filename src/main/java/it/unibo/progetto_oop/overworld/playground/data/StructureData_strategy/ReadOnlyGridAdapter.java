package it.unibo.progetto_oop.overworld.playground.data.StructureData_strategy;

import java.util.Objects;

import it.unibo.progetto_oop.overworld.playground.data.TileType;

/**
 * Adapter class that wraps a StructureData instance
 * and exposes it as a ReadOnlyGrid.
 * This allows read-only access to the grid data
 * without exposing modification methods.
 */
public final class ReadOnlyGridAdapter implements ReadOnlyGrid {

    /**
     * The underlying StructureData instance being adapted.
     */
    private final StructureData delegate;

    /**
     * Private constructor to enforce usage of the static factory method.
     *
     * @param newDelegate the StructureData instance to adapt
     */
    private ReadOnlyGridAdapter(final StructureData newDelegate) {
        this.delegate = Objects.requireNonNull(newDelegate);
    }

    /**
     * Static factory method to create a ReadOnlyGridAdapter
     * from a given StructureData instance.
     *
     * @param newData the StructureData instance to adapt
     * @return a ReadOnlyGrid wrapping the provided StructureData
     */
    public static ReadOnlyGrid of(final StructureData newData) {
        return new ReadOnlyGridAdapter(newData);
    }

    @Override
    public int width() {
        return delegate.width();
    }

    @Override
    public int height() {
        return delegate.height();
    }

    @Override
    public TileType get(final int x, final int y) {
        return delegate.get(x, y);
    }

    @Override
    public boolean inBounds(final int x, final int y) {
        return delegate.inBounds(x, y);
    }
}
