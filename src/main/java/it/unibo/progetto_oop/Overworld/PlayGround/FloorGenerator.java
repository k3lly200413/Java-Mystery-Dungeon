package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.ArrayList;
import java.util.Random;

public class FloorGenerator {
    //SINGLETON ????
    private final Random rand = new Random();
    private static final int N_ROOMS = 8;
    private final ArrayList<Room> rooms;
    private final int floorWidth;
    private final int floorHeight;
    private final ArrayList<Pair<Integer, Integer>> path;
    private boolean overlapping;

    public FloorGenerator(int floorWidth, int floorHeight) {
        this.floorHeight = floorHeight;
        this.floorWidth = floorWidth;
        this.rooms = new ArrayList<>();
        this.path = new ArrayList<>();
        this.overlapping = false;
        addRooms();
        generateTunnels();
    }
    
    private Room genRoom(){
        int roomWidth = rand.nextInt(8) + 5;
        int roomHeight = rand.nextInt(6) + 5;
        int roomX = rand.nextInt(floorWidth - roomWidth - 1) + 1;
        int roomY = rand.nextInt(floorHeight - roomHeight - 1) + 1;
        return new Room(roomX, roomY, roomWidth, roomHeight);
    }

    private void addRooms() {
        for (int i = 0; i < N_ROOMS; i++) {
            Room newRoom = genRoom();

            this.overlapping = false;
            for (Room room : rooms) {
                if (newRoom.intersects(room)) {
                    overlapping = true;
                    break;
                }
            }

            if (!this.overlapping) {
                rooms.add(newRoom);
                for (int y = newRoom.y; y < newRoom.y + newRoom.height; y++)
                    for (int x = newRoom.x; x < newRoom.x + newRoom.width; x++)
                        this.path.add(new Pair<>(x, y));
            }
        }
    }
    
    private void generateTunnels() {
        for (int i = 0; i < rooms.size() - 1; i++) {
            Room r1 = rooms.get(i);
            Room r2 = rooms.get(i + 1);
            int x1 = r1.x + r1.width / 2;
            int y1 = r1.y + r1.height / 2;
            int x2 = r2.x + r2.width / 2;
            int y2 = r2.y + r2.height / 2;

            if (rand.nextBoolean()) {
                connectHorizontal(x1, x2, y1);
                connectVertical(y1, y2, x2);
            } else {
                connectVertical(y1, y2, x1);
                connectHorizontal(x1, x2, y2);
            }
        }
    }

    private void connectHorizontal(int x1, int x2, int y) {
        int startX = Math.min(x1, x2);
        int endX = Math.max(x1, x2);
        for (int x = startX; x <= endX; x++) {
            this.path.add(new Pair<>(x,y));
        }
            
    }

    private void connectVertical(int y1, int y2, int x) {
        int startY = Math.min(y1, y2);
        int endY = Math.max(y1, y2);
        for (int y = startY; y <= endY; y++) {
            this.path.add(new Pair<>(x,y));
        }
            
    }
    
    public ArrayList<Pair<Integer, Integer>> getPath() {
        return path;
    }
    
    public ArrayList<Room> getRooms() {
        return rooms;
    }
}
