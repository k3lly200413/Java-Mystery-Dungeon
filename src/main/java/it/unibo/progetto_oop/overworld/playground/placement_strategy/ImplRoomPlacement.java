package it.unibo.progetto_oop.overworld.playground.placement_strategy;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.overworld.playground.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.StructureData;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Room;

/**
 * Implementation of a room placement strategy for generating rooms
 * within a grid-based map.
 * This class provides methods to place non-overlapping rectangular rooms
 * on the map according to specified configuration parameters.
 */
public class ImplRoomPlacement implements RoomPlacementStrategy {

    @Override
    public final void placeRooms(
            final StructureData grid,
            final List<Room> outRooms,
            final Random rand,
            final FloorConfig config) {
        for (int i = 0; i < config.nRooms(); i++) {
            final Room newRoom = genRoom(rand, config);

            boolean overlapping = false;
            for (final Room r : outRooms) {
                if (newRoom.intersects(r)) {
                    overlapping = true;
                    break;
                }
            }

            if (!overlapping) {
                outRooms.add(newRoom);
                carveRoom(grid, newRoom);
            }
        }
    }

    private static void carveRoom(final StructureData g, final Room r) {
        for (int y = r.getY(); y < r.getY() + r.getHeight(); y++) {
            for (int x = r.getX(); x < r.getX() + r.getWidth(); x++) {
                g.set(x, y, TileType.ROOM);
            }
        }
    }

    private Room genRoom(final Random rand, final FloorConfig cfg) {
        // check room size limits to stay in bounds
        final int maxW = Math.max(1, Math.min(cfg.maxRoomW(), cfg.width()));
        final int minW = Math.max(1, Math.min(cfg.minRoomW(), maxW));
        final int maxH = Math.max(1, Math.min(cfg.maxRoomH(), cfg.height()));
        final int minH = Math.max(1, Math.min(cfg.minRoomH(), maxH));

        final int w = minW + rand.nextInt(maxW - minW + 1);
        final int h = minH + rand.nextInt(maxH - minH + 1);

        final int maxX = cfg.width() - w;
        final int maxY = cfg.height() - h;
        final int x = (maxX > 0) ? rand.nextInt(maxX + 1) : 0;
        final int y = (maxY > 0) ? rand.nextInt(maxY + 1) : 0;

        return new Room(x, y, w, h);
    }
}
