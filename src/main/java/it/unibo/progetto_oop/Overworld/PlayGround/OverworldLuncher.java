package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;

import it.unibo.progetto_oop.Overworld.MVC.OverworldController;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.MVC.ViewManager;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.FloorConfig;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.Dungeon;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.FloorGenerator;
import it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy.*;
import it.unibo.progetto_oop.Overworld.PlayGround.view.SwingMapView;
import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;

public final class OverworldLuncher {

    private final FloorConfig config = new FloorConfig.Builder().build();
    private final Random rand = new Random();

    private FloorGenerator createFloorGenerator() {
        RandomPlacementStrategy rps = new ImplRandomPlacement();
        RoomPlacementStrategy   rrs = new ImplRoomPlacement();
        TunnelPlacementStrategy tps = new ImplTunnelPlacement();
        return new FloorGenerator(rrs, tps, rps, rand);
    }

    private OverworldModel createModel(Dungeon dungeon) {
        OverworldModel model = new OverworldModel(List.<Enemy>of(), List.<Item>of());
        model.bindDungeon(dungeon);
        return model;
    }

    private SwingMapView createView() {
        return new SwingMapView(config.tileSize());
    }

    public void start() {
        FloorGenerator gen = createFloorGenerator();
        Dungeon dungeon = new Dungeon(gen, config);
        OverworldModel model = createModel(dungeon);

        SwingUtilities.invokeLater(() -> {
            SwingMapView view = createView();

            MapController mapController = new MapController(view, model);
            mapController.start();

            ViewManager viewManager = new ViewManager();
            viewManager.start(view);

            new OverworldController(model, view, viewManager);
        });
    }
}
