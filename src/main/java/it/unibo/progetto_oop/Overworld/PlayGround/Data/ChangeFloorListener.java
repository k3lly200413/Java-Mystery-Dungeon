package it.unibo.progetto_oop.Overworld.PlayGround.Data;

@FunctionalInterface
public interface ChangeFloorListener {
    void onFloorChange(StructureData grid);
}
