package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.Random;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.FloorConfig;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.Dungeon;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.FloorGenerator;
import it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy.ImplRandomPlacement;
import it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy.ImplRoomPlacement;
import it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy.ImplTunnelPlacement;
import it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy.RandomPlacementStrategy;
import it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy.RoomPlacementStrategy;
import it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy.TunnelPlacementStrategy;
import it.unibo.progetto_oop.Overworld.PlayGround.view.SwingMapView;

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

