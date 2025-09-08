package it.unibo.progetto_oop.overworld.playGround.view;

import it.unibo.progetto_oop.overworld.PlayGround.Data.StructureData;

public interface MapView {
    /**
     * Renders the map view based on the provided grid structure.
     *
     * @param grid the structure data representing the floor grid to render
     */
    void render(StructureData grid);
}
