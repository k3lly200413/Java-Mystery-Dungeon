package it.unibo.progetto_oop.overworld.playground;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.mvc.OverworldModel;
import it.unibo.progetto_oop.overworld.playground.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Dungeon;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.FloorGenerator;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.ImplRandomPlacement;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.ImplRoomPlacement;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.ImplTunnelPlacement;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.RandomPlacementStrategy;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.RoomPlacementStrategy;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.TunnelPlacementStrategy;
import it.unibo.progetto_oop.overworld.playground.view.SwingMapView;

public final class OverworldLuncher {
    private final OverworldModel model;
    private final SwingMapView view;
    private final MapController mapController;

    public OverworldLuncher(FloorConfig floorConfig, EntityStatsConfig entityStatsConfig, Random rand) {
        RandomPlacementStrategy rps = new ImplRandomPlacement();
        RoomPlacementStrategy   rrs = new ImplRoomPlacement();
        TunnelPlacementStrategy tps = new ImplTunnelPlacement();
        FloorGenerator gen = new FloorGenerator(rrs, tps, rps, rand);

        Dungeon dungeon = new Dungeon(gen, floorConfig);
        this.model = new OverworldModel(List.<Enemy>of(), List.<Item>of(), entityStatsConfig);
        this.model.bindDungeon(dungeon);

        this.view = new SwingMapView(floorConfig.tileSize());
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
