package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.ArrayList;
import java.util.Random;

import it.unibo.progetto_oop.Combat.Inventory.Inventory;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
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
import it.unibo.progetto_oop.Overworld.Player.Player;

public final class Main {
    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private Main() {
        throw new UnsupportedOperationException("Utility class");
    }
    /**
     * The main method to run the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(final String[] args) {

        //-------------DA VEDERE !!!----------
        Inventory inventory = new Inventory();
        Player player = new Player(100, inventory);
        OverworldModel overworldModel = new OverworldModel(player, new ArrayList<>(), new ArrayList<>(), null);
        // ---------------!!!-----------------

        // MODEL
        final Random rand = new Random();
        final RandomPlacementStrategy rps = new ImplRandomPlacement();
        final RoomPlacementStrategy rrs = new ImplRoomPlacement();
        final TunnelPlacementStrategy tps = new ImplTunnelPlacement();
        FloorGenerator gen = new FloorGenerator(rrs, tps, rps, rand);
        FloorConfig config = new FloorConfig.Builder().build();
        Dungeon dungeon = new Dungeon(gen, config, player, overworldModel);
        // VIEW
        SwingMapView view = new SwingMapView(
            "Java Mystery Dungeon", config.tileSize()
        );
        // CONTROLLER
        MapController controller = new MapController(view, dungeon);
        javax.swing.SwingUtilities.invokeLater(controller::show);
    }
}

