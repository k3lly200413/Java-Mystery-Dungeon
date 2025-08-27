package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        final Random rand = new Random();
        final RandomPlacementStrategy rps = new ImplRandomPlacement();
        final RoomPlacementStrategy rrs = new ImplRoomPlacement();
        final TunnelPlacementStrategy tps = new ImplTunnelPlacement();

        FloorGenerator gen = new FloorGenerator(rrs, tps, rps, rand);

        FloorConfig config = new FloorConfig.Builder().build();

        Dungeon dungeon = new Dungeon(gen, config);

        // VIEW Swing
        SwingMapView view = new SwingMapView("Java Mystery Dungeon", 14);

        // CONTROLLER
        MapController controller = new MapController(view, dungeon);

        javax.swing.SwingUtilities.invokeLater(controller::show);
    }
}

