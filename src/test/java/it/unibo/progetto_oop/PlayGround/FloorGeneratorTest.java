package it.unibo.progetto_oop.PlayGround;

import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.overworld.playGround.data.*;
import it.unibo.progetto_oop.overworld.playGround.DungeonLogic.*;
import it.unibo.progetto_oop.overworld.playGround.PlacementStrategy.*;

public class FloorGeneratorTest {

    private FloorConfig cfg;
    private StructureData grid;
    private List<Room> rooms;
    private List<Position> players, stairs, enemies, items;

    @BeforeEach
    void setup() {
        cfg = new FloorConfig.Builder()
                .size(50, 50)
                .rooms(8)
                .roomSize(5, 12, 5, 10)
                .tileSize(14)
                .build();

        RoomPlacementStrategy roomPlacer = new ImplRoomPlacement();
        TunnelPlacementStrategy tunnelPlacer = new ImplTunnelPlacement();
        RandomPlacementStrategy objPlacer = new ImplRandomPlacement();
        Random rng = new Random(42);

        FloorGenerator gen = new FloorGenerator(roomPlacer, tunnelPlacer, objPlacer, rng);

        grid = new ImplArrayListStructureData(cfg.width(), cfg.height());
        rooms = gen.generate(grid, cfg, false);

        players = getPositions(grid, TileType.PLAYER);
        stairs = getPositions(grid, TileType.STAIRS);
        enemies = getPositions(grid, TileType.ENEMY);
        items = getPositions(grid, TileType.ITEM);
    }

    private static List<Position> getPositions(StructureData g, TileType t) {
        List<Position> posList = new ArrayList<>();
        for (int y = 0; y < g.height(); y++) {
            for (int x = 0; x < g.width(); x++) {
                if (g.get(x, y) == t) {
                    posList.add(new Position(x, y));
                }
            }
        }
        return posList;
    }

    // 1) Stanze: esistono, sono nei bounds, non si sovrappongono
    @Test
    void roomsAreValidAndNonOverlapping() {
        assertFalse(rooms.isEmpty(), "Deve creare almeno una stanza");
        for (Room r : rooms) {
            assertTrue(r.getX() >= 0 && r.getY() >= 0, "Coordinate non negative");
            assertTrue(r.getX() + r.getWidth() <= cfg.width(), "Dentro larghezza");
            assertTrue(r.getY() + r.getHeight() <= cfg.height(), "Dentro altezza");
        }
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = i + 1; j < rooms.size(); j++) {
                assertFalse(intersects(rooms.get(i), rooms.get(j)), "Stanze non sovrapposte");
            }
        }
    }
    private static boolean intersects(Room a, Room b) {
        boolean sepX = a.getX() + a.getWidth() - 1 < b.getX() || b.getX() + b.getWidth() - 1 < a.getX();
        boolean sepY = a.getY() + a.getHeight() - 1 < b.getY() || b.getY() + b.getHeight() - 1 < a.getY();
        return !(sepX || sepY);
    }

    // 2) una sola scala 
    @Test
    void objectCounts() {
        assertEquals(1, stairs.size(),  "Una sola STAIRS");
    }

    // 3) Oggetti non su WALL
    @Test
    void placedObjectsAreNotOnWalls() {
        for (Position p : concat(players, stairs, enemies, items)) {
            assertNotEquals(TileType.WALL, grid.get(p.x(), p.y()), "Oggetto su cella non WALL");
        }
    }
    
    @SafeVarargs
    private static <T> List<T> concat(List<T>... lists) {
        List<T> newList = new ArrayList<>();
        for (List<T> l : lists) newList.addAll(l);
        return newList;
    }

    // 4) Ogni stanza ha almeno una cella di bordo adiacente a un corridoio
    @Test
    void roomsHaveOneAdjacentCorridor() {
        assertFalse(rooms.isEmpty(), "rooms vuoto");

        final int[] dx = { 1, -1, 0, 0 };
        final int[] dy = { 0, 0, 1, -1 };

        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            boolean hasDoor = false;

            for (Position p : room) {
                if (isBorder(room, p) && !hasDoor) {
                    for (int k = 0; k < 4 && !hasDoor; k++) {
                        int nx = p.x() + dx[k];
                        int ny = p.y() + dy[k];

                        Position nearP = new Position(nx, ny);
                        if (inMap(nearP)) {
                            TileType t = grid.get(nearP.x(), nearP.y());
                            boolean isCorridor = (t == TileType.TUNNEL);
                            boolean isOtherRoom = (t == TileType.ROOM && !room.contains(nearP));

                            if ((isCorridor || isOtherRoom)) {
                                hasDoor = true;
                            }
                        }
                    }
                }
            }

            assertTrue(hasDoor, "La stanza " + i + " non ha un corridoio");
        }
    }

    private static boolean isBorder(Room r, Position p) {
        return p.x() == r.getX() 
            || p.x() == r.getX() + r.getWidth() - 1
            || p.y() == r.getY() 
            || p.y() == r.getY() + r.getHeight() - 1;
    }

    private boolean inMap(Position p) {
        return p.x() >= 0 && p.y() >= 0
            && p.x() < grid.width() && p.y() < grid.height();
    }


}
