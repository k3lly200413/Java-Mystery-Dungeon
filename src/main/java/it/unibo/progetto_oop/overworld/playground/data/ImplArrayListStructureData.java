package it.unibo.progetto_oop.overworld.playground.data;

import java.util.ArrayList;
import java.util.stream.IntStream;

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
    private final ArrayList<ArrayList<TileType>> grid;

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
            ArrayList<TileType> row = new ArrayList<>(w);
            for (int x = 0; x < w; x++) {
                row.add(null);
            }
            this.grid.add(row);
        }

        fill(TileType.WALL);
    }

    @Override
    public int width() {
        return w;
    }

    @Override
    public int height() {
        return h;
    }

    @Override
    public boolean inBounds(final int x, final int y) {
        return x >= 0 && x < w && y >= 0 && y < h;
    }

    @Override
    public TileType get(final int x, final int y) {
        return grid.get(y).get(x);
    }

    @Override
    public void set(final int x, final int y, final TileType t) {
        grid.get(y).set(x, t);
    }

    @Override
    public void fill(final TileType t) {
        grid.forEach(row -> IntStream.range(0, w).forEach(i -> row.set(i, t)));
    }
}
