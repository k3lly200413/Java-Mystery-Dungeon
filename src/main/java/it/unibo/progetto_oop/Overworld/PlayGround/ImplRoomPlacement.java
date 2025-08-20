package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.List;
import java.util.Random;

public class ImplRoomPlacement implements RoomPlacementStrategy {

    @Override
    public void placeRooms(StructureData grid, List<Room> outRooms, Random rand,
                           int floorWidth, int floorHeight, int nRooms) {

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
        int roomWidth  = rand.nextInt(8) + 5;
        int roomHeight = rand.nextInt(6) + 5;
        int roomX = rand.nextInt(floorWidth  - roomWidth  - 1) + 1;
        int roomY = rand.nextInt(floorHeight - roomHeight - 1) + 1;
        return new Room(roomX, roomY, roomWidth, roomHeight);
    }
    
}
