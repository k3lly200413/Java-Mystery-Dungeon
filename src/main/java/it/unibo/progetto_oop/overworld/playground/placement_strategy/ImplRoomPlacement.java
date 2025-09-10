package it.unibo.progetto_oop.overworld.playground.placement_strategy;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.overworld.playground.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.data.StructureData_strategy.StructureData;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Room;

public class ImplRoomPlacement implements RoomPlacementStrategy {

    @Override
    public final void placeRooms(
            final StructureData grid,
            final List<Room> outRooms,
            final Random rand,
            final FloorConfig config) {
        for (int i = 0; i < config.nRooms(); i++) {
            Room newRoom = genRoom(rand, config);

            boolean overlapping = false;
            for (Room r : outRooms) {
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
        int maxW = Math.max(1, Math.min(cfg.maxRoomW(), cfg.width()));
        int minW = Math.max(1, Math.min(cfg.minRoomW(), maxW));
        int maxH = Math.max(1, Math.min(cfg.maxRoomH(), cfg.height()));
        int minH = Math.max(1, Math.min(cfg.minRoomH(), maxH));

        int w = minW + rand.nextInt(maxW - minW + 1);
        int h = minH + rand.nextInt(maxH - minH + 1);

        int maxX = cfg.width()  - w;
        int maxY = cfg.height() - h;
        int x = (maxX > 0) ? rand.nextInt(maxX + 1) : 0;
        int y = (maxY > 0) ? rand.nextInt(maxY + 1) : 0;

        return new Room(x, y, w, h);
    }
}
