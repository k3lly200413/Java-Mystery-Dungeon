package it.unibo.progetto_oop.overworld.playground;

import java.util.List;
import java.util.Objects;
import javax.swing.SwingUtilities;

import it.unibo.progetto_oop.overworld.mvc.OverworldEntitiesGenerator;
import it.unibo.progetto_oop.overworld.mvc.OverworldModel;
import it.unibo.progetto_oop.overworld.playground.data.ChangeFloorListener;
import it.unibo.progetto_oop.overworld.playground.data.StructureData;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Floor;
import it.unibo.progetto_oop.overworld.playground.view.SwingMapView;

public final class MapController implements ChangeFloorListener {
    /**
     * The view responsible for rendering the map.
     */
    private final SwingMapView view;

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
    public MapController(final SwingMapView mapView,
                         final OverworldModel overworldModel) {
        this.view = Objects.requireNonNull(mapView);
        this.model = Objects.requireNonNull(overworldModel);
    }

    public void start() {
        model.setChangeFloorListener(this);
        model.setFloorInitializer(this::initFloor);
        model.nextFloor();
    }

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
        view.setZoom(9);
        SwingUtilities.invokeLater(() -> view.render(baseGrid));
    }
}
