package it.unibo.progetto_oop.overworld.playground;

import java.util.List;
import java.util.Objects;

import it.unibo.progetto_oop.combat.CombatLauncher;
import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatPresenter;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.mvc.OverworldController;
import it.unibo.progetto_oop.overworld.mvc.OverworldModel;
import it.unibo.progetto_oop.overworld.mvc.ViewManager;
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
     */
    public OverworldLauncher(
            final FloorConfig floorConfig,
            final EntityStatsConfig entityStatsConfig) {
        final RandomPlacementStrategy rps = new ImplRandomPlacement();
        final RoomPlacementStrategy rrs = new ImplRoomPlacement();
        final TunnelPlacementStrategy tps = new ImplTunnelPlacement();
        final FloorGenerator gen = new FloorGenerator(rrs, tps, rps);

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
     * Attaches the playground view to the provided ViewManager.
     *
     * @param vm the ViewManager to attach the playground view to
     */
    public void attachPlaygroundView(final ViewManager vm) {
        vm.setPlayGroundView(this.view);
    }

    /**
     * Wires the overworld components to the provided ViewManager.
     *
     * @param vm the ViewManager to wire the components to
     * 
     * @return the OverworldController managing the overworld interactions
     */
    public OverworldController wireOverworldController(final ViewManager vm) {
        final OverworldController oc = new OverworldController(this.model, this.view, vm);
        this.model.setCombatTransitionListener(oc);
        return oc;
    }

    /**
     * builds and returns a CombatPresenter using the provided CombatLauncher.
     * without exposing the model internals.
     *
     * @param combatLauncher combat launcher used to build the combat presenter
     * 
     * @return the combat presenter ready for use
     */
    public CombatPresenter buildCombat(final CombatLauncher combatLauncher) {
        Objects.requireNonNull(combatLauncher);
        return combatLauncher.buildCombat(
            this.model.getPlayer(),
            this.model.getCombatCollision(),
            this.model.getGridNotifier()
        );
    }
}
