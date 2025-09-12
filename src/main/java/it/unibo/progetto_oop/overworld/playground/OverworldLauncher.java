package it.unibo.progetto_oop.overworld.playground;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.mvc.OverworldModel;
import it.unibo.progetto_oop.overworld.mvc.generation_entities.EntityStatsConfig;
import it.unibo.progetto_oop.overworld.playground.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Dungeon;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.FloorGenerator;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.ImplRandomPlacement;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.ImplRoomPlacement;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.ImplTunnelPlacement;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.RandomPlacementStrategy;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.RoomPlacementStrategy;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.TunnelPlacementStrategy;
import it.unibo.progetto_oop.overworld.playground.view.playground_view.ImplMapView;

/**
 * Launcher class for initializing and starting the overworld map.
 */
public final class OverworldLauncher {
    /**
     * The model representing the overworld state.
     */
    private final OverworldModel model;

    /**
     * The view responsible for displaying the map.
     */
    private final ImplMapView view;
    /**
     * The controller responsible for managing the map interactions.
     */
    private final MapController mapController;

    /**
     * Constructs an OverworldLuncher with the specified configurations.
     *
     * @param floorConfig       the configuration for the dungeon floor
     * @param entityStatsConfig the configuration for entity stats
     * @param rand              the random generator
     */
    public OverworldLauncher(
            final FloorConfig floorConfig,
            final EntityStatsConfig entityStatsConfig,
            final Random rand) {
        final RandomPlacementStrategy rps = new ImplRandomPlacement();
        final RoomPlacementStrategy rrs = new ImplRoomPlacement();
        final TunnelPlacementStrategy tps = new ImplTunnelPlacement();
        final FloorGenerator gen = new FloorGenerator(rrs, tps, rps, rand);

        final Dungeon dungeon = new Dungeon(gen, floorConfig);
        this.model = new OverworldModel(
                List.<Enemy>of(),
                List.<Item>of(),
                entityStatsConfig);
        this.model.bindDungeon(dungeon);

        this.view = new ImplMapView(floorConfig.tileSize());
        this.mapController = new MapController(this.view, this.model);
    }

    /**
     * Starts the map controller to initialize the overworld interactions.
     */
    public void start() {
        this.mapController.start();
    }

    /**
     * Gets the view responsible for displaying the map.
     *
     * @return the map view
     */
    public ImplMapView getView() {
        return this.view;
    }

    /**
     * Gets the model representing the overworld state.
     *
     * @return the overworld model
     */
    public OverworldModel getModel() {
        return this.model;
    }
}
