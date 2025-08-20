package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.ArrayList;
import java.util.stream.IntStream;

public final class ImplArrayListStructureData implements StructureData {
    private final ArrayList<ArrayList<TileType>> grid;

    public ImplArrayListStructureData(int w, int h) {
        this.grid = new ArrayList<>(w);
        for (int i = 0; i < w; i++) {
            ArrayList<TileType> row = new ArrayList<>(h);
            this.grid.add(row);
        }

        fill(TileType.WALL);
    }

    @Override
    public int width() {
        return grid.size();
    }

    @Override
    public int height() {
        return grid.get(0).size();
    }

    @Override
    public TileType get(int x, int y) {
        return grid.get(x).get(y);
    }

    @Override
    public void set(int x, int y, TileType t) {
        grid.get(x).set(y, t);
    }

    @Override
    public void fill(TileType t) {
        grid.forEach(row -> 
            IntStream.range(0, row.size())
                .forEach(i -> row.set(i, t))
        );
    }   // facciamo vedere che sappiamo usare le stream API

    @Override
    public boolean inBounds(int x, int y) {
        return x >= 0 && x < width() && y >= 0 && y < height();
    }
}
