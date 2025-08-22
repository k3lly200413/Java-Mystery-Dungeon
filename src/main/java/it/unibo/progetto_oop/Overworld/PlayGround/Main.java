package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.Random;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        FloorConfig config = new FloorConfig.Builder().build();

        final Random rand = new Random();
        // Generator con le tue Strategy (esempio)
        FloorGenerator gen = new FloorGenerator(8, new ImplRoomPlacement(), new ImplTunnelPlacement(), rand);

        // MODEL: crea la mappa (dentro Floor scegli new ArrayGrid/ByteGrid/SparseGrid)
        Floor floor = new Floor(config, gen);

        // VIEW Swing
        SwingMapView view = new SwingMapView("Mystery Dungeon - Map", 14);

        // CONTROLLER
        MapController controller = new MapController(floor, view);

        javax.swing.SwingUtilities.invokeLater(controller::show);
    }
}

