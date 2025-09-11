package it.unibo.progetto_oop.overworld.playground;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.overworld.playground.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.data.StructureData_strategy.ImplArrayListStructureData;
import it.unibo.progetto_oop.overworld.playground.data.StructureData_strategy.StructureData;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.FloorGenerator;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Room;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.ImplRandomPlacement;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.ImplRoomPlacement;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.ImplTunnelPlacement;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.RandomPlacementStrategy;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.RoomPlacementStrategy;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.TunnelPlacementStrategy;

public class FloorGeneratorTest {

    /**
     * Number of possible directions.
     */
    private static final int DIRECTION_COUNT = 4;
    /**
     * Configuration for the floor generation.
     */
    private FloorConfig cfg;

    /**
     * Data structure representing the grid of the floor.
     */
    private StructureData grid;
    /**
     * List of rooms generated in the floor.
     */
    private List<Room> rooms;

    /**
     * List of player positions on the floor.
     */
    private List<Position> players;
    /**
     * List of stair positions on the floor.
     */
    private List<Position> stairs;

    /**
     * List of enemy positions on the floor.
     */
    private List<Position> enemies;
    /**
     * List of item positions on the floor.
     */
    private List<Position> items;

    @BeforeEach
    final void setup() {
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

        FloorGenerator gen = new FloorGenerator(
                roomPlacer, tunnelPlacer, objPlacer, rng);

        grid = new ImplArrayListStructureData(cfg.width(), cfg.height());
        rooms = gen.generate(grid, cfg, false);

        players = getPositions(grid, TileType.PLAYER);
        stairs = getPositions(grid, TileType.STAIRS);
        enemies = getPositions(grid, TileType.ENEMY);
        items = getPositions(grid, TileType.ITEM);
    }

    private static List<Position> getPositions(final StructureData g,
                                               final TileType t) {
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

    // Rooms: exist, are within bounds, do not overlap
    @Test
    void roomsAreValidAndNonOverlapping() {
        assertFalse(rooms.isEmpty(), "At least one room");
        for (Room r : rooms) {
            assertTrue(r.getX() >= 0 && r.getY() >= 0,
                       "Coordinates must be non-negative");
            assertTrue(
                r.getX() + r.getWidth() <= cfg.width(),
                "Within width"
            );
            assertTrue(
                r.getY() + r.getHeight() <= cfg.height(),
                "Within height"
            );
        }
        for (int i = 0; i < rooms.size(); i++) {
            for (int j = i + 1; j < rooms.size(); j++) {
                assertFalse(
                    intersects(rooms.get(i), rooms.get(j)),
                    "Rooms must not overlap"
                );
            }
        }
    }

    private static boolean intersects(final Room a, final Room b) {
        boolean sepX = a.getX() + a.getWidth() - 1 < b.getX()
                || b.getX() + b.getWidth() - 1 < a.getX();
        boolean sepY = a.getY() + a.getHeight() - 1 < b.getY()
                || b.getY() + b.getHeight() - 1 < a.getY();
        return !(sepX || sepY);
    }

    // one stairs
    @Test
    void objectCounts() {
        assertEquals(1, stairs.size(), "At least one stairs");
    }

    // objects not on walls
    @Test
    void placedObjectsAreNotOnWalls() {
        for (Position p : concat(players, stairs, enemies, items)) {
            assertNotEquals(
                TileType.WALL,
                grid.get(p.x(), p.y()),
                "Object on wall tile"
            );
        }
    }

    @SafeVarargs
    private static <T> List<T> concat(final List<T>... lists) {
        List<T> newList = new ArrayList<>();
        for (List<T> l : lists) {
            newList.addAll(l);
        }
        return newList;
    }

   // each room has at least one border cell adjacent to a corridor
    @Test
    void roomsHaveOneAdjacentCorridor() {
        assertFalse(rooms.isEmpty(), "At least one room");

        final int[] dx = {1, -1, 0, 0};
        final int[] dy = {0, 0, 1, -1};

        for (int i = 0; i < rooms.size(); i++) {
            Room room = rooms.get(i);
            boolean hasDoor = false;

            for (Position p : room) {
                for (int k = 0; k < DIRECTION_COUNT && !hasDoor; k++) {
                    int nx = p.x() + dx[k];
                    int ny = p.y() + dy[k];

                    Position nearP = new Position(nx, ny);
                    if (inMap(nearP)) {
                        TileType t = grid.get(nearP.x(), nearP.y());
                        boolean isCorridor = (t == TileType.TUNNEL);
                        boolean isOtherRoom = (t == TileType.ROOM
                            && !room.contains(nearP));

                        if (isCorridor || isOtherRoom) {
                            hasDoor = true;
                        }
                    }
                }
            }
            assertTrue(hasDoor, "Room has no adjacent corridor");
        }
    }

    private boolean inMap(final Position p) {
        return p.x() >= 0 && p.y() >= 0
            && p.x() < grid.width() && p.y() < grid.height();
    }


}
