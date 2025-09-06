package it.unibo.progetto_oop.PlayGround;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.FloorConfig;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.Floor;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.FloorGenerator;
import it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy.ImplRandomPlacement;
import it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy.ImplRoomPlacement;
import it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy.ImplTunnelPlacement;

public class FloorTest {

    private Floor newFloor(int w, int h) {
        FloorConfig conf = new FloorConfig.Builder()
                .size(w, h)
                .rooms(2)
                .roomSize(3, 6, 3, 6)
                .build();

        FloorGenerator gen = new FloorGenerator(new ImplRoomPlacement(), new ImplTunnelPlacement(), new ImplRandomPlacement(), new Random());
        return new Floor(conf, gen, false);
    }
}
