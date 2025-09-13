package it.unibo.progetto_oop.overworld.playground.data.listner;

import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ReadOnlyGrid;

/**
 * Listener interface for handling floor change events in the structure.
 */
@FunctionalInterface
public interface ChangeFloorListener {

    /**
     * Called when the floor changes, providing the new base grid structure.
     *
     * @param base the new base grid structure after the floor change
     */
    void onFloorChange(ReadOnlyGrid base);
}
