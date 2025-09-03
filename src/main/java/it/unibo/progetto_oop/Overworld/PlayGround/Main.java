package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.combat.Inventory.Item;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.MVC.OverworldController;
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
import it.unibo.progetto_oop.Overworld.MVC.ViewManager;

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
        // MODEL
        FloorConfig config = new FloorConfig.Builder().build();
        
        final Random rand = new Random();
        final RandomPlacementStrategy rps = new ImplRandomPlacement();
        final RoomPlacementStrategy rrs = new ImplRoomPlacement();
        final TunnelPlacementStrategy tps = new ImplTunnelPlacement();
        FloorGenerator gen = new FloorGenerator(rrs, tps, rps, rand);
        
        Dungeon dungeon = new Dungeon(gen, config);
        OverworldModel overworldModel = new OverworldModel(List.<Enemy>of(), List.<Item>of());
        overworldModel.bindDungeon(dungeon);

        // VIEW
        SwingMapView view = new SwingMapView(
            "Java Mystery Dungeon", config.tileSize()
        );
        
        // CONTROLLER
        final MapController controller = new MapController(view, dungeon, overworldModel);
        controller.show();
        
        // -----------CARD LAYOUT------------
        //TODO: se vogliamo usare il cardlayout non ci deve essere show

        ViewManager viewManager = new ViewManager();
        viewManager.start(view);
        OverworldController movementController = new OverworldController(overworldModel, view, viewManager);
    }
}

