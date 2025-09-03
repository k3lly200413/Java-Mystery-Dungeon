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
        return new Floor(conf, gen);
    }

    @Test
    void onItemRemovedPlayerIsOn() {
        Floor f = newFloor(10, 10);
        Position p = f.getObjectsPositions(TileType.ITEM).get(0);

        f.onItemRemoved(p);
        StructureData grid = f.grid();
        assertEquals(TileType.PLAYER, grid.get(p.x(), p.y()));
    }

    @Test
    void onEnemyRemovedSetsRoom() {
        Floor f = newFloor(10, 10);
        StructureData grid = f.grid();

        Position cell = f.getObjectsPositions(TileType.ROOM).get(0);
        assertNotNull(cell, "almeno una cella ROOM");

        // ENEMY su cella
        grid.set(cell.x(), cell.y(), TileType.ENEMY);
        f.onEnemyRemoved(cell);
        assertEquals(TileType.ROOM, grid.get(cell.x(), cell.y()),
                "Dopo onEnemyRemoved la cella deve tornare ROOM");
    }

    @Test
    void onEnemyRemovedDoesNotWorkOnPlayer() {
        Floor f = newFloor(10, 10);
        StructureData grid = f.grid();

        Position cell = f.getObjectsPositions(TileType.ROOM).get(0);
        assertNotNull(cell, "almeno una cella ROOM");
        grid.set(cell.x(), cell.y(), TileType.PLAYER);
        f.onEnemyRemoved(cell);

        assertEquals(TileType.PLAYER, grid.get(cell.x(), cell.y()),
                "Su cella PLAYER, onEnemyRemoved non deve sovrascrivere");
    }

}