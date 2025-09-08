package it.unibo.progetto_oop.overworld.playGround;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.mvc.OverworldModel;

import it.unibo.progetto_oop.overworld.playGround.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playGround.dungeonLogic.Dungeon;
import it.unibo.progetto_oop.overworld.playGround.dungeonLogic.FloorGenerator;
import it.unibo.progetto_oop.overworld.playGround.placementStrategy.ImplRandomPlacement;
import it.unibo.progetto_oop.overworld.playGround.placementStrategy.ImplRoomPlacement;

import it.unibo.progetto_oop.overworld.playGround.placementStrategy.ImplTunnelPlacement;
import it.unibo.progetto_oop.overworld.playGround.placementStrategy.RandomPlacementStrategy;
import it.unibo.progetto_oop.overworld.playGround.placementStrategy.RoomPlacementStrategy;
import it.unibo.progetto_oop.overworld.playGround.placementStrategy.TunnelPlacementStrategy;
import it.unibo.progetto_oop.overworld.playGround.view.SwingMapView;

public final class OverworldLuncher {
    private final OverworldModel model;
    private final SwingMapView view;
    private final MapController mapController;

    public OverworldLuncher(FloorConfig config, Random rand) {
        RandomPlacementStrategy rps = new ImplRandomPlacement();
        RoomPlacementStrategy   rrs = new ImplRoomPlacement();
        TunnelPlacementStrategy tps = new ImplTunnelPlacement();
        FloorGenerator gen = new FloorGenerator(rrs, tps, rps, rand);

        Dungeon dungeon = new Dungeon(gen, config);
        this.model = new OverworldModel(List.<Enemy>of(), List.<Item>of());
        this.model.bindDungeon(dungeon);

        this.view = new SwingMapView(config.tileSize());
        this.mapController = new MapController(this.view, this.model);
    }

    public void start() {
        this.mapController.start();
    }

    public SwingMapView getView() {
        return this.view;
    }
    public OverworldModel getModel() {
        return this.model;
    }
}
