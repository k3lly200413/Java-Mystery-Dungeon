package it.unibo.progetto_oop.overworld.playground.placement_strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ImplArrayListStructureData;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.StructureData;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Room;

// CHECKSTYLE: MagicNumber OFF
class ImplTunnelPlacementTest {

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

    private static void carveRoom(final StructureData g, final Room r) {
        for (int y = r.getY(); y < r.getY() + r.getHeight(); y++) {
            for (int x = r.getX(); x < r.getX() + r.getWidth(); x++) {
                g.set(x, y, TileType.ROOM);
            }
        }
    }

    private static int cx(final Room r) {
        return r.getX() + r.getWidth() / 2;
    }

    private static int cy(final Room r) {
        return r.getY() + r.getHeight() / 2;
    }

    private static boolean rowHasTunnelFromTo(final StructureData g, final int y, final int x1, final int x2) {
        final int a = Math.min(x1, x2);
        final int b = Math.max(x1, x2);
        for (int x = a; x <= b; x++) {
            if (g.get(x, y) == TileType.WALL) {
                return false;
            }
        }
        return true;
    }

    private static boolean colHasTunnelFromTo(final StructureData g, final int x, final int y1, final int y2) {
        final int a = Math.min(y1, y2);
        final int b = Math.max(y1, y2);
        for (int y = a; y <= b; y++) {
            if (g.get(x, y) == TileType.WALL) {
                return false;
            }
        }
        return true;
    }

    @Test
    void testConnectTwoRooms() {
        final StructureData grid = new ImplArrayListStructureData(40, 25);
        grid.fill(TileType.WALL);

        final Room r1 = new Room(5, 5, 6, 5);
        final Room r2 = new Room(25, 15, 7, 6);
        carveRoom(grid, r1);
        carveRoom(grid, r2);

        final int roomsBefore = count(grid, TileType.ROOM);
        final int tunnelsBefore = count(grid, TileType.TUNNEL);

        final var rooms = List.of(r1, r2);
        new ImplTunnelPlacement().connect(grid, rooms, new Random(123));

        // rooms are the same
        assertEquals(roomsBefore, count(grid, TileType.ROOM));
        // new tunnels
        assertTrue(count(grid, TileType.TUNNEL) > tunnelsBefore);

        // verify at least one L path between the two rooms
        final int x1 = cx(r1);
        final int y1 = cy(r1);
        final int x2 = cx(r2);
        final int y2 = cy(r2);
        final boolean pathVariantA = rowHasTunnelFromTo(grid, y1, x1, x2)
                && colHasTunnelFromTo(grid, x2, y1, y2);
        final boolean pathVariantB = colHasTunnelFromTo(grid, x1, y1, y2)
                && rowHasTunnelFromTo(grid, y2, x1, x2);
        assertTrue(pathVariantA || pathVariantB);
    }

    @Test
    void testNoOpWithZeroOrOneRoom() {
        // 0 rooms
        final StructureData g = new ImplArrayListStructureData(20, 15);
        g.fill(TileType.WALL);
        final int c = count(g, TileType.TUNNEL);
        new ImplTunnelPlacement().connect(g, List.of(), new Random(1));
        assertEquals(c, count(g, TileType.TUNNEL));

        // 1 room
        final StructureData g2 = new ImplArrayListStructureData(20, 15);
        g2.fill(TileType.WALL);
        final Room c2 = new Room(3, 3, 4, 4);
        carveRoom(g2, c2);
        final int t2 = count(g2, TileType.TUNNEL);
        new ImplTunnelPlacement().connect(g2, List.of(c2), new Random(2));
        assertEquals(t2, count(g2, TileType.TUNNEL));
    }

    @Test
    void testConnectMultipleRooms() {
        final StructureData grid = new ImplArrayListStructureData(50, 30);
        grid.fill(TileType.WALL);

        final List<Room> rooms = new ArrayList<>();
        rooms.add(new Room(3, 3, 6, 5));
        rooms.add(new Room(18, 6, 7, 6));
        rooms.add(new Room(30, 18, 6, 5));
        rooms.add(new Room(40, 10, 6, 6));
        rooms.forEach(r -> carveRoom(grid, r));

        new ImplTunnelPlacement().connect(grid, rooms, new Random(7));

        // for each pair of consecutive rooms, check there is a path
        for (int i = 0; i < rooms.size() - 1; i++) {
            final Room a = rooms.get(i);
            final Room b = rooms.get(i + 1);
            final int x1 = cx(a);
            final int y1 = cy(a);
            final int x2 = cx(b);
            final int y2 = cy(b);
            final boolean pathVariantA = rowHasTunnelFromTo(grid, y1, x1, x2)
                    && colHasTunnelFromTo(grid, x2, y1, y2);
            final boolean pathVariantB = colHasTunnelFromTo(grid, x1, y1, y2)
                    && rowHasTunnelFromTo(grid, y2, x1, x2);
            assertTrue(pathVariantA || pathVariantB);
        }

        // room cells are unchanged
        for (final Room r : rooms) {
            for (int y = r.getY(); y < r.getY() + r.getHeight(); y++) {
                for (int x = r.getX(); x < r.getX() + r.getWidth(); x++) {
                    assertEquals(TileType.ROOM, grid.get(x, y));
                }
            }
        }
    }

}
