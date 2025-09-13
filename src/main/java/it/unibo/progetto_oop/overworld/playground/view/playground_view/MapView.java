package it.unibo.progetto_oop.overworld.playground.view.playground_view;

import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ReadOnlyGrid;

/**
 * Interface representing a view for rendering the map of the playground.
 */
public interface MapView {
    /**
     * Renders the map view based on the provided grid structure.
     *
     * @param grid the structure data representing the floor grid to render
     */
    void render(ReadOnlyGrid grid);

    /**
     * Sets the entity grid to be displayed on the map view.
     *
     * @param entity the structure data representing the entity grid
     */
    void setEntityGrid(ReadOnlyGrid entity);

    /**
     * Sets the camera target position for the map view.
     *
     * @param p the position to center the camera on
     */
    void setCameraTarget(Position p);

    /**
     * Sets the zoom level for the map view.
     *
     * @param z the zoom level (e.g., 1.0 for normal size, 2.0 for double size)
     */
    void setZoom(double z);
}
