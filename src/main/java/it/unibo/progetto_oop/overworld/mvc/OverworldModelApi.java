package it.unibo.progetto_oop.overworld.mvc;

import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.listner.ChangeFloorListener;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ReadOnlyGrid;

/**
 * API for the Overworld Model.
 */
public interface OverworldModelApi {

    /**
     * Get the base grid view.
     *
     * @return the base grid view
     */
    ReadOnlyGrid getBaseGridView();

    /**
     * Get the entity grid view.
     *
     * @return the entity grid view
     */
    ReadOnlyGrid getEntityGridView();

    /**
     * Get the player position.
     *
     * @return the player position
     */
    Position getPlayerPosition();

    /**
     * Registra il listener per cambio piano.
     *
     * @param l il listener da registrare
     */
    void setChangeFloorListener(ChangeFloorListener l);

    /**
     * change to the next floor.
     *
     * @return true if the floor changed, false otherwise
     */
    boolean nextFloor();
}
