package it.unibo.progetto_oop.overworld.playground.view.playground_view;

import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ReadOnlyGrid;

/**
 * Interface representing a view for rendering the map of the playground.
 */
@FunctionalInterface
public interface MapView {
    /**
     * Renders the map view based on the provided grid structure.
     *
     * @param grid the structure data representing the floor grid to render
     */
    void render(ReadOnlyGrid grid);
}
