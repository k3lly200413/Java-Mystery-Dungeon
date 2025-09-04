package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.List;
import java.util.Objects;

import javax.swing.SwingUtilities;

import it.unibo.progetto_oop.Overworld.MVC.OverworldEntitiesGenerator;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.ChangeFloorListener;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.Floor;
import it.unibo.progetto_oop.Overworld.PlayGround.view.SwingMapView;

public final class MapController implements ChangeFloorListener {
    /**
     * The SwingMapView instance used to display the map.
     */
    private final SwingMapView view;
    private final OverworldModel model;
    
    public MapController(final SwingMapView mapView, final OverworldModel model) {
        this.view = Objects.requireNonNull(mapView);
        this.model = Objects.requireNonNull(model);
    }

    /**
     * Initializes the view by binding the floor-change action,
     * generates the first floor, and displays the UI.
     */
    public void show() {
        view.onNextFloorRequested(this::next);
        model.setChangeFloorListener(this);
        model.setFloorInitializer(this::initFloor);
        next();
        SwingUtilities.invokeLater(view::showView);
    }

    /**
     * Advances to the next floor in the dungeon and updates the view
     * with the new floor's structure data.
     */
    public void next() {
        model.nextFloor();
        model.bindCurrentFloor(model.getCurrentFloor());
    }
    
    private void initFloor(Floor floor) {
        // tolgo ogetti dal piano precedente
        model.setSpawnObjects(List.of(), List.of());
        // genero ogg nuovo piano
        new OverworldEntitiesGenerator(
            floor,
            model.getPlayer(),
            model,
            model.getGridNotifier()
        );
    }

    @Override
    public void onFloorChange(StructureData grid) {
        SwingUtilities.invokeLater(() -> view.render(grid));
    }
}
