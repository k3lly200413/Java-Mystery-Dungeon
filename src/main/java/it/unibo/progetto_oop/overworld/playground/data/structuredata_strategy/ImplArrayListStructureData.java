package it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import it.unibo.progetto_oop.overworld.playground.data.TileType;

/**
 * Implementation of the StructureData interface using a 2D ArrayList.
 */
public final class ImplArrayListStructureData implements StructureData {
    /**
     * The width of the structure.
     */
    private final int w;

    /**
     * The height of the structure.
     */
    private final int h;
    /**
     * The grid representing the structure (2D ArrayList of TileType).
     */
    private final List<List<TileType>> grid;

    /**
     * Constructs a new structure object with the specified width and height.
     *
     * @param newW the width of the structure, must be greater than 0
     * @param newH the height of the structure, must be greater than 0
     * @throws IllegalArgumentException if width or height are <= than 0
     */
    public ImplArrayListStructureData(final int newW, final int newH) {
        if (newW <= 0 || newH <= 0) {
            throw new IllegalArgumentException("Invalid size");
        }
        this.w = newW;
        this.h = newH;

        this.grid = new ArrayList<>(h);
        for (int y = 0; y < h; y++) {
            final List<TileType> row = new ArrayList<>(w);
            for (int x = 0; x < w; x++) {
                row.add(TileType.WALL);
            }
            this.grid.add(row);
        }
    }

    @Override
    public TileType get(final int x, final int y) {
        if (!inBounds(x, y)) {
            throw new IndexOutOfBoundsException();
        }
        return grid.get(y).get(x);
    }

    @Override
    public void set(final int x, final int y, final TileType t) {
        if (!inBounds(x, y)) {
            throw new IndexOutOfBoundsException();
        }
        grid.get(y).set(x, t);
    }

    @Override
    public void fill(final TileType t) {
        grid.forEach(row -> IntStream.range(0, w).forEach(i -> row.set(i, t)));
    }

    @Override
    public int width() {
        return this.w;
    }

    @Override
    public int height() {
        return this.h;
    }
}
