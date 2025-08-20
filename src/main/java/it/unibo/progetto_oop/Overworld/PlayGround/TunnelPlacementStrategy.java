package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.List;
import java.util.Random;

public interface TunnelPlacementStrategy  {
    void connect(StructureData grid, List<Room> rooms, Random rand);
}
