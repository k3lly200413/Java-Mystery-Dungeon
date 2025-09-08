package it.unibo.progetto_oop.overworld.playground.PlacementStrategy;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.overworld.playground.DungeonLogic.Room;
import it.unibo.progetto_oop.overworld.playground.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playground.data.StructureData;
import it.unibo.progetto_oop.overworld.playground.data.TileType;

public class ImplRoomPlacement implements RoomPlacementStrategy {

    @Override
    public void placeRooms(StructureData grid, List<Room> outRooms, Random rand,
                           FloorConfig config) {

        for (int i = 0; i < config.nRooms(); i++) {
            Room newRoom = genRoom(rand,config);

            boolean overlapping = false;
            for (Room room : outRooms) {
                if (newRoom.intersects(room)) {
                    overlapping = true;
                    break;
                }
            }

            if (!overlapping) {
                outRooms.add(newRoom);
                for (int y = newRoom.getY(); y < newRoom.getY() + newRoom.getHeight(); y++) {
                    for (int x = newRoom.getX(); x < newRoom.getX() + newRoom.getWidth(); x++) {
                        if (grid.inBounds(x, y)) {
                            grid.set(x, y, TileType.ROOM);
                        }
                    }
                }
            }
        }
    }

    private Room genRoom(Random rand, FloorConfig cfg) {
        final int padding = 1; // bordo di 1 cella se possibile

        // calcola range dimensioni, clampati alla griglia
        int maxW = Math.max(1, Math.min(cfg.maxRoomW(), cfg.width()  - 2 * padding));
        int minW = Math.max(1, Math.min(cfg.minRoomW(), maxW));
        int maxH = Math.max(1, Math.min(cfg.maxRoomH(), cfg.height() - 2 * padding));
        int minH = Math.max(1, Math.min(cfg.minRoomH(), maxH));

        int w = minW + rand.nextInt(maxW - minW + 1);
        int h = minH + rand.nextInt(maxH - minH + 1);

        // range posizioni, con fallback a nessun padding se la mappa è troppo piccola
        int minX = padding, maxX = cfg.width()  - padding - w;
        if (maxX < minX) { minX = 0; maxX = cfg.width()  - w; }
        int minY = padding, maxY = cfg.height() - padding - h;
        if (maxY < minY) { minY = 0; maxY = cfg.height() - h; }

        int x = (maxX <= minX) ? minX : rand.nextInt(maxX - minX + 1) + minX;
        int y = (maxY <= minY) ? minY : rand.nextInt(maxY - minY + 1) + minY;

        return new Room(x, y, w, h);
    }
}

    
