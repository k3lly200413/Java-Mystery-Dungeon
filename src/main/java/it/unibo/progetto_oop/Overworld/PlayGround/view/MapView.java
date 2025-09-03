package it.unibo.progetto_oop.Overworld.PlayGround.view;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;

public interface MapView {
    /**
     * Renders the map view based on the provided grid structure.
     *
     * @param grid the structure data representing the floor grid to render
     */
    void render(StructureData grid);
}