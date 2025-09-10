package it.unibo.progetto_oop.overworld.playground;

import java.util.List;
import java.util.Objects;
import javax.swing.SwingUtilities;

import it.unibo.progetto_oop.overworld.mvc.OverworldModel;
import it.unibo.progetto_oop.overworld.mvc.generation_entities.OverworldEntitiesGenerator;
import it.unibo.progetto_oop.overworld.playground.data.StructureData_strategy.StructureData;
import it.unibo.progetto_oop.overworld.playground.data.listner.ChangeFloorListener;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Floor;
import it.unibo.progetto_oop.overworld.playground.view.playground_view.ImplMapView;

public final class MapController implements ChangeFloorListener {

    /**
     * Default zoom level for the map view.
     */
    private static final int DEFAULT_ZOOM_LEVEL = 9;

    /**
     * The view responsible for rendering the map.
     */
    private final ImplMapView view;

    /**
     * The model representing the overworld state.
     */
    private final OverworldModel model;

    /**
     * Constructs a MapController with the specified view and model.
     *
     * @param mapView the view responsible for rendering the map
     * @param overworldModel the model representing the overworld state
     */
    public MapController(final ImplMapView mapView,
                         final OverworldModel overworldModel) {
        this.view = Objects.requireNonNull(mapView);
        this.model = Objects.requireNonNull(overworldModel);
    }

    /**
     * Starts the map controller by setting up the floor listener
     * and initializing the first floor.
     */
    public void start() {
        model.setChangeFloorListener(this);
        model.setFloorInitializer(this::initFloor);
        model.nextFloor();
    }

    /**
     * Advances the game to the next floor.
     */
    public void next() {
        model.nextFloor();
    }

    private void initFloor(final Floor floor) {
        model.setSpawnObjects(List.of(), List.of());
        new OverworldEntitiesGenerator(
            floor,
            model.getPlayer(),
            model,
            model.getGridNotifier()
        );
    }

    @Override
    public void onFloorChange(final StructureData baseGrid) {
        view.setEntityGrid(model.getEntityGridView());
        view.setCameraTarget(model.getPlayer().getPosition());
        view.setZoom(DEFAULT_ZOOM_LEVEL);
        SwingUtilities.invokeLater(() -> view.render(baseGrid));
    }
}
