package JMD;

import java.util.ArrayList;

public final class Floor {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private ArrayList<ArrayList<TileType>> playGround;
    private final FloorGenerator genF;
    // potrebbe interessarmi sapere quali sono le celle di una stanza in modo da mettere gli oggetti lì e non nei corridoi

    public Floor() {
        this.genF = new FloorGenerator(WIDTH, HEIGHT);
        initFloor();

        // 1) Disegna le stanze come FLOOR
        this.genF.getRooms().forEach(room -> {
            for (int r = room.y; r < room.y + room.height; r++) {
                for (int c = room.x; c < room.x + room.width; c++) {
                    if (c >= 0 && c < WIDTH && r >= 0 && r < HEIGHT) {
                        playGround.get(r).set(c, TileType.FLOOR);
                    }
                }
            }
        });

        // 2) Disegna i corridoi come CORRIDOR
        this.genF.getPath().forEach(coord -> {
            int x = coord.getX();
            int y = coord.getY();
            if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
                // se già è FLOOR (stanza), lo lascio FLOOR
                if (playGround.get(y).get(x) == TileType.WALL) {
                    playGround.get(y).set(x, TileType.TUNNEL);
                }
            }
        });
    }


    public void initFloor() {
        this.playGround = new ArrayList<>();
        for (int r = 0; r < HEIGHT; r++) {
            ArrayList<TileType> row = new ArrayList<>();
            for (int c = 0; c < WIDTH; c++) {
                row.add(TileType.WALL); // default tutte mura
            }
            this.playGround.add(row);
        }
    }

    public ArrayList<ArrayList<TileType>> getPlayGround() {
        return playGround;
    }

    // utile ancora in debug console
    public void printAscii() {
        if (this.playGround == null || this.playGround.isEmpty()) {
            System.out.println("(playGround vuoto)");
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (ArrayList<TileType> row : playGround) {
            for (TileType tile : row) {
                sb.append(tile.toString());
            }
            sb.append('\n');
        }
        System.out.print(sb.toString());
    }
}
