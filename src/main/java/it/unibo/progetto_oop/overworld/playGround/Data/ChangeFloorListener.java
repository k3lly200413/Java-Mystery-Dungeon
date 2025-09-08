package it.unibo.progetto_oop.overworld.playGround.data;

@FunctionalInterface
public interface ChangeFloorListener {
    void onFloorChange(StructureData grid);
}
