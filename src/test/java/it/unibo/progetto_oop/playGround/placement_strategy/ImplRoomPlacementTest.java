package it.unibo.progetto_oop.playground.placement_strategy;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.overworld.playground.data.ImplArrayListStructureData;
import it.unibo.progetto_oop.overworld.playground.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playground.data.StructureData;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Room;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.ImplRoomPlacement;

public class ImplRoomPlacementTest {

    private StructureData newGrid(int w, int h) {
        return new ImplArrayListStructureData(w, h);
    }

    private FloorConfig cfg(int w, int h, int nRooms, int minW, int minH, int maxW, int maxH) {
        // ADATTA ai nomi reali del tuo builder
        return new FloorConfig.Builder()
                .size(w, h)
                .rooms(nRooms)
                .roomSize(minW, minH, maxW, maxH)
                .build();
    }

    private static int count(StructureData g, TileType t) {
        int c = 0;
        for (int y = 0; y < g.height(); y++) {
            for (int x = 0; x < g.width(); x++) {
                if (g.get(x, y) == t) c++;
            }
        }
        return c;
    }

    private static boolean roomsOverlap(List<Room> rooms) {
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = i + 1; j < rooms.size(); j++) {
                if (rooms.get(i).intersects(rooms.get(j))) return true;
            }
        }
        return false;
    }

    @Test
    @DisplayName("placeRooms: nessuna sovrapposizione, celle ROOM in-bounds, numero ≤ nRooms")
    void testPlaceRoomsBasic() {
        StructureData grid = newGrid(40, 30);
        var outRooms = new ArrayList<Room>();
        var rand = new Random(123);

        var config = cfg(40, 30, 20, 3, 3, 6, 6);

        new ImplRoomPlacement().placeRooms(grid, outRooms, rand, config);

        // Non più di nRooms
        assertTrue(outRooms.size() <= config.nRooms(), "Troppe stanze generate");

        // Nessuna sovrapposizione
        assertFalse(roomsOverlap(outRooms), "Le stanze si sovrappongono");

        // Celle delle stanze impostate a ROOM ed entro i limiti
        for (Room r : outRooms) {
            for (int y = r.getY(); y < r.getY() + r.getHeight(); y++) {
                for (int x = r.getX(); x < r.getX() + r.getWidth(); x++) {
                    assertTrue(grid.inBounds(x, y), "Cella fuori mappa: ("+x+","+y+")");
                    assertEquals(TileType.ROOM, grid.get(x, y), "Cella non marcata come ROOM");
                }
            }
        }

        // Deve esserci almeno qualche cella ROOM se ha generato almeno una stanza
        if (!outRooms.isEmpty()) {
            assertTrue(count(grid, TileType.ROOM) > 0);
        }
    }

    @Test
    @DisplayName("placeRooms: mappa piccola -> dimensioni clampate e in-bounds")
    void testSmallMapClamping() {
        StructureData grid = newGrid(7, 7);
        var outRooms = new ArrayList<Room>();
        var rand = new Random(7);

        // Richieste più grandi della mappa: genRoom clampa dimensioni/posizioni
        var config = cfg(7, 7, 10, 5, 5, 10, 10);

        new ImplRoomPlacement().placeRooms(grid, outRooms, rand, config);

        // Tutte le stanze devono essere in-bounds e con width/height >=1
        for (Room r : outRooms) {
            assertTrue(r.getWidth() >= 1 && r.getHeight() >= 1, "Room troppo piccola");
            assertTrue(r.getX() >= 0 && r.getY() >= 0, "Posizione negativa");
            assertTrue(r.getX() + r.getWidth()  <= grid.width(),  "Room oltre il bordo in X");
            assertTrue(r.getY() + r.getHeight() <= grid.height(), "Room oltre il bordo in Y");
        }
    }

    @Test
    @DisplayName("placeRooms: nRooms=0 non modifica la griglia")
    void testZeroRooms() {
        StructureData grid = newGrid(20, 15);
        var outRooms = new ArrayList<Room>();
        var rand = new Random(0);

        var config = cfg(20, 15, 0, 3, 3, 6, 6);

        new ImplRoomPlacement().placeRooms(grid, outRooms, rand, config);

        assertEquals(0, outRooms.size(), "Non dovevano essere create stanze");
        assertEquals(0, count(grid, TileType.ROOM), "La griglia non doveva avere celle ROOM");
    }
}
