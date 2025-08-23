package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final Random rand = new Random();
        FloorGenerator gen = new FloorGenerator(new ImplRoomPlacement(), new ImplTunnelPlacement(), rand);

        FloorConfig config = new FloorConfig.Builder().build();
        List<Floor> floors = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            floors.add(new Floor(config, gen));
        }

        FloorConfig bossConfig = new FloorConfig.Builder().rooms(1).build();
        floors.add(new Floor(bossConfig, gen));

        Dungeon dungeon = new Dungeon(floors);

        // VIEW Swing
        SwingMapView view = new SwingMapView("Mystery Dungeon - Map", 14);
        dungeon.getCurrentFloor().addObserver(view); 

        // CONTROLLER
        MapController controller = new MapController(dungeon, view);

        javax.swing.SwingUtilities.invokeLater(() -> {
            view.setVisible(true);
            new javax.swing.Timer(1000, e -> controller.nextFloor(dungeon)).start();
        });
    }
}

