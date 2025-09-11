package it.unibo.progetto_oop.overworld.playground.data.listner;

import it.unibo.progetto_oop.overworld.playground.data.StructureData_strategy.ReadOnlyGrid;

@FunctionalInterface
public interface ChangeFloorListener {
    /**
     * Called when the floor changes in the structure.
     * @param grid the new structure data representing the current floor
     */
    void onFloorChange(ReadOnlyGrid base);
}
