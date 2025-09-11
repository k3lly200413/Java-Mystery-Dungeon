package it.unibo.progetto_oop.overworld.playground.data.StructureData_strategy;

import java.util.Objects;

import it.unibo.progetto_oop.overworld.playground.data.TileType;

public final class ReadOnlyGridAdapter implements ReadOnlyGrid {
    private final StructureData delegate;

    private ReadOnlyGridAdapter(StructureData delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    public static ReadOnlyGrid of(StructureData data) {
        return new ReadOnlyGridAdapter(data);
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
    public TileType get(int x, int y) {
        return delegate.get(x, y);
    }

    @Override
    public boolean inBounds(int x, int y) {
        return delegate.inBounds(x, y);
    }
}
