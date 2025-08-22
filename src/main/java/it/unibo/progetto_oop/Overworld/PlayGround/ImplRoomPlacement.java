package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.List;
import java.util.Random;

public class ImplRoomPlacement implements RoomPlacementStrategy {

    @Override
    public void placeRooms(StructureData grid, List<Room> outRooms, Random rand,
                           FloorConfig config) {

        for (int i = 0; i < nRooms; i++) {
            Room newRoom = genRoom(rand, floorWidth, floorHeight);

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
                            grid.set(x, y, TileType.FLOOR);
                        }
                    }
                }
            }
        }
    }

    private Room genRoom(Random rand, int floorWidth, int floorHeight) {
    // range originali (come i tuoi: 5..12 e 5..10)
    final int MIN_W = 5, MAX_W = 12;
    final int MIN_H = 5, MAX_H = 10;

    // 1) Dimensioni stanza sicure (senza margini)
    //    Clamp ai limiti del floor per evitare range inversi o fuori scala.
    int maxW = Math.max(1, Math.min(MAX_W, floorWidth));
    int minW = Math.max(1, Math.min(MIN_W, maxW));
    int maxH = Math.max(1, Math.min(MAX_H, floorHeight));
    int minH = Math.max(1, Math.min(MIN_H, maxH));

    int roomWidth  = minW + rand.nextInt((maxW - minW) + 1);  // bound >= 1
    int roomHeight = minH + rand.nextInt((maxH - minH) + 1);  // bound >= 1

    // 2) Posizione stanza senza margini (può toccare i bordi)
    int maxX = floorWidth  - roomWidth;   // può essere 0 → stanza “appoggiata” al bordo
    int maxY = floorHeight - roomHeight;

    // se maxX/maxY == 0, nextInt deve restituire 0; se >0, range [0..max]
    int roomX = (maxX <= 0) ? 0 : rand.nextInt(maxX + 1);
    int roomY = (maxY <= 0) ? 0 : rand.nextInt(maxY + 1);

    return new Room(roomX, roomY, roomWidth, roomHeight);
}

    
}
