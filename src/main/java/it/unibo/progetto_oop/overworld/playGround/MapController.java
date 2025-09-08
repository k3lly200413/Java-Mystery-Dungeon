package it.unibo.progetto_oop.overworld.playGround;

import java.util.List;
import java.util.Objects;
import javax.swing.SwingUtilities;

import it.unibo.progetto_oop.overworld.PlayGround.Data.ChangeFloorListener;
import it.unibo.progetto_oop.overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.overworld.PlayGround.DungeonLogic.Floor;
import it.unibo.progetto_oop.overworld.PlayGround.view.SwingMapView;
import it.unibo.progetto_oop.overworld.mvc.OverworldEntitiesGenerator;
import it.unibo.progetto_oop.overworld.mvc.OverworldModel;

public final class MapController implements ChangeFloorListener {
    private final SwingMapView view;
    private final OverworldModel model;

    public MapController(final SwingMapView mapView, final OverworldModel model) {
        this.view = Objects.requireNonNull(mapView);
        this.model = Objects.requireNonNull(model);
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
