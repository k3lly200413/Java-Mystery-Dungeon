package it.unibo.progetto_oop.overworld.playground.data;

@FunctionalInterface
public interface ChangeFloorListener {
    void onFloorChange(StructureData grid);
}
