package it.unibo.progetto_oop.overworld.playground.data;

import java.util.ArrayList;
import java.util.stream.IntStream;

public final class ImplArrayListStructureData implements StructureData {
    private final int w, h;
    private final ArrayList<ArrayList<TileType>> grid;

    public ImplArrayListStructureData(int w, int h) {
        if (w <= 0 || h <= 0)
            throw new IllegalArgumentException("Invalid size");
        this.w = w;
        this.h = h;

        this.grid = new ArrayList<>(h);
        for (int y = 0; y < h; y++) {
            ArrayList<TileType> row = new ArrayList<>(w);
            for (int x = 0; x < w; x++)
                row.add(null);
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

    @Override public boolean inBounds(int x, int y) {
        return x >= 0 && x < w && y >= 0 && y < h;
    }

    @Override public TileType get(int x, int y) {
        return grid.get(y).get(x);
    }

    @Override public void set(int x, int y, TileType t) {
        grid.get(y).set(x, t);
    }

    @Override
    public void fill(TileType t) {
        grid.forEach(row ->
            IntStream.range(0, w).forEach(i -> row.set(i, t))
        );
    }
}
