package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.ArrayList;
import java.util.List;

public interface MapView {
    void render(List<ArrayList<TileType>> grid);
}
