package it.unibo.progetto_oop.overworld.PlayGround.Data;

@FunctionalInterface
public interface ChangeFloorListener {
    void onFloorChange(StructureData grid);
}
