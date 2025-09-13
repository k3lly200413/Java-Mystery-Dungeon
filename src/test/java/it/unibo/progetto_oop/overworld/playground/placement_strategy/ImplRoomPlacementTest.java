package it.unibo.progetto_oop.overworld.playground.placement_strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.overworld.playground.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ImplArrayListStructureData;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.StructureData;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Room;

// CHECKSTYLE: MagicNumber OFF
class ImplRoomPlacementTest {

    private FloorConfig cfg(final int w, final int h, final int nRooms) {
        return new FloorConfig.Builder()
                .size(w, h)
                .rooms(nRooms)
                .roomSize(5, 5, 10, 10)
                .build();
    }

    private static int count(final StructureData g, final TileType t) {
        int c = 0;
        for (int y = 0; y < g.height(); y++) {
            for (int x = 0; x < g.width(); x++) {
                if (g.get(x, y) == t) {
                    c++;
                }
            }
        }
        return c;
    }

    private static boolean roomsOverlap(final List<Room> rooms) {
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = i + 1; j < rooms.size(); j++) {
                if (rooms.get(i).intersects(rooms.get(j))) {
                    return true;
                }
            }
        }
        return false;
    }

    @Test
    void testPlaceRooms() {
        final StructureData grid = new ImplArrayListStructureData(40, 30);
        final var outRooms = new ArrayList<Room>();
        final var rand = new Random(123);
        final var config = cfg(40, 30, 6);
        new ImplRoomPlacement().placeRooms(grid, outRooms, rand, config);

        assertTrue(outRooms.size() <= config.nRooms());
        assertFalse(roomsOverlap(outRooms));

        for (final Room r : outRooms) {
            for (int y = r.getY(); y < r.getY() + r.getHeight(); y++) {
                for (int x = r.getX(); x < r.getX() + r.getWidth(); x++) {
                    assertTrue(grid.inBounds(x, y));
                    assertEquals(TileType.ROOM, grid.get(x, y));
                }
            }
        }
    }

    @Test
    void testSmallMap() {
        final StructureData grid = new ImplArrayListStructureData(7, 7);
        final var outRooms = new ArrayList<Room>();
        final var rand = new Random(123);

        final var config = cfg(7, 7, 10);

        new ImplRoomPlacement().placeRooms(grid, outRooms, rand, config);

        for (final Room r : outRooms) {
            assertTrue(r.getWidth() >= 1 && r.getHeight() >= 1);
            assertTrue(r.getX() >= 0 && r.getY() >= 0);
            assertTrue(r.getX() + r.getWidth() <= grid.width());
            assertTrue(r.getY() + r.getHeight() <= grid.height());
        }
    }

    @Test
    void testZeroRooms() {
        final StructureData grid = new ImplArrayListStructureData(20, 15);
        final var outRooms = new ArrayList<Room>();

        assertThrows(IllegalArgumentException.class, () -> {
            cfg(7, 7, 0);
        });
        assertEquals(0, outRooms.size());
        assertEquals(0, count(grid, TileType.ROOM));
    }
}
