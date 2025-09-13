package it.unibo.progetto_oop.overworld.playground.data.listner;

import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ReadOnlyGrid;

/**
 * Listener interface for handling floor change events in the structure.
 */
@FunctionalInterface
public interface ChangeFloorListener {
    
    /**
     * Called when the floor changes in the structure.
     * @param base the new structure data representing the current floor
     */
    void onFloorChange(ReadOnlyGrid base);
}
