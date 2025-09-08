package it.unibo.progetto_oop.overworld.playGround.Data;

@FunctionalInterface
public interface ChangeFloorListener {
    void onFloorChange(StructureData grid);
}
