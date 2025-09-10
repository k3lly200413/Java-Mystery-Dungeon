package it.unibo.progetto_oop.overworld.playground.placement_strategy;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.overworld.playground.data.ImplArrayListStructureData;
import it.unibo.progetto_oop.overworld.playground.data.StructureData;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Room;

public class ImplTunnelPlacementTest {
    
    private static int count(StructureData g, TileType t) {
        int c = 0;
        for (int y = 0; y < g.height(); y++) {
            for (int x = 0; x < g.width(); x++)
                if (g.get(x, y) == t)
                    c++;
        }
        return c;
    }

    private static void carveRoom(StructureData g, Room r) {
        for (int y = r.getY(); y < r.getY() + r.getHeight(); y++) {
            for (int x = r.getX(); x < r.getX() + r.getWidth(); x++) {
                g.set(x, y, TileType.ROOM);
            }
        }
    }

    private static int cx(Room r) {
        return r.getX() + r.getWidth() / 2;
    }

    private static int cy(Room r) {
        return r.getY() + r.getHeight() / 2;
    }

    private static boolean rowHasTunnelFromTo(StructureData g, int y, int x1, int x2) {
        int a = Math.min(x1, x2), b = Math.max(x1, x2);
        for (int x = a; x <= b; x++) {
            if (g.get(x, y) == TileType.WALL)
                return false;
        }
        return true;
    }

    private static boolean colHasTunnelFromTo(StructureData g, int x, int y1, int y2) {
        int a = Math.min(y1, y2), b = Math.max(y1, y2);
        for (int y = a; y <= b; y++) {
            if (g.get(x, y) == TileType.WALL)
                return false;
        }
        return true;
    }

    @Test
    void testConnectTwoRooms() {
        StructureData grid = new ImplArrayListStructureData(40, 25);
        grid.fill(TileType.WALL);

        Room r1 = new Room(5, 5, 6, 5);
        Room r2 = new Room(25, 15, 7, 6);
        carveRoom(grid, r1);
        carveRoom(grid, r2);

        int roomsBefore = count(grid, TileType.ROOM);
        int tunnelsBefore = count(grid, TileType.TUNNEL);

        var rooms = List.of(r1, r2);
        new ImplTunnelPlacement().connect(grid, rooms, new Random(123));

        // rooms are the same
        assertEquals(roomsBefore, count(grid, TileType.ROOM));
        // new tunnels
        assertTrue(count(grid, TileType.TUNNEL) > tunnelsBefore);

        // verify at least one L path between the two rooms
        int x1 = cx(r1), y1 = cy(r1);
        int x2 = cx(r2), y2 = cy(r2);
        boolean pathVariantA = rowHasTunnelFromTo(grid, y1, x1, x2) && colHasTunnelFromTo(grid, x2, y1, y2);
        boolean pathVariantB = colHasTunnelFromTo(grid, x1, y1, y2) && rowHasTunnelFromTo(grid, y2, x1, x2);
        assertTrue(pathVariantA || pathVariantB);
    }

    @Test
    void testNoOpWithZeroOrOneRoom() {
        // 0 stanze
        StructureData g = new ImplArrayListStructureData(20, 15);
        g.fill(TileType.WALL);
        int c = count(g, TileType.TUNNEL);
        new ImplTunnelPlacement().connect(g, List.of(), new Random(1));
        assertEquals(c, count(g, TileType.TUNNEL));

        // 1 stanza
        StructureData g2 = new ImplArrayListStructureData(20, 15);
        g2.fill(TileType.WALL);
        Room c2 = new Room(3, 3, 4, 4);
        carveRoom(g2, c2);
        int t2 = count(g2, TileType.TUNNEL);
        new ImplTunnelPlacement().connect(g2, List.of(c2), new Random(2));
        assertEquals(t2, count(g2, TileType.TUNNEL));
    }


    @Test
    void testConnectMultipleRooms() {
        StructureData grid = new ImplArrayListStructureData(50, 30);
        grid.fill(TileType.WALL);

        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room(3, 3, 6, 5));
        rooms.add(new Room(18, 6, 7, 6));
        rooms.add(new Room(30, 18, 6, 5));
        rooms.add(new Room(40, 10, 6, 6));
        rooms.forEach(r -> carveRoom(grid, r));

        new ImplTunnelPlacement().connect(grid, rooms, new Random(7));

        // for each pair of consecutive rooms, check there is a path
        for (int i = 0; i < rooms.size() - 1; i++) {
            Room a = rooms.get(i);
            Room b = rooms.get(i + 1);
            int x1 = cx(a), y1 = cy(a);
            int x2 = cx(b), y2 = cy(b);
            boolean pathVariantA = rowHasTunnelFromTo(grid, y1, x1, x2) && colHasTunnelFromTo(grid, x2, y1, y2);
            boolean pathVariantB = colHasTunnelFromTo(grid, x1, y1, y2) && rowHasTunnelFromTo(grid, y2, x1, x2);
            assertTrue(pathVariantA || pathVariantB);
        }

        // room cells are unchanged
        for (Room r : rooms) {
            for (int y = r.getY(); y < r.getY() + r.getHeight(); y++) {
                for (int x = r.getX(); x < r.getX() + r.getWidth(); x++) {
                    assertEquals(TileType.ROOM, grid.get(x, y));
                }
            }
        }
    }

}
