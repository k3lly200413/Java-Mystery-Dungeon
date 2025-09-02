package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.Objects;

import javax.swing.SwingUtilities;

import it.unibo.progetto_oop.Overworld.MVC.OverworldEntitiesGenerator;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.Dungeon;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.Floor;
import it.unibo.progetto_oop.Overworld.PlayGround.view.SwingMapView;

public final class MapController {
    /**
     * The SwingMapView instance used to display the map.
     */
    private final SwingMapView view;
    /**
     * The Dungeon instance used to manage the dungeon floors.
     */
    private final Dungeon dungeon;
    private final OverworldModel model;
    
    public MapController(final SwingMapView mapView,
                         final Dungeon mapDungeon,
                         final OverworldModel model) {
        this.view = Objects.requireNonNull(mapView);
        this.dungeon = Objects.requireNonNull(mapDungeon);
        this.model = Objects.requireNonNull(model);
    }

    /**
     * Initializes the view by binding the floor-change action,
     * generates the first floor, and displays the UI.
     */
    public void show() {
        view.onNextFloorRequested(this::next);
        next();
        SwingUtilities.invokeLater(view::showView);
    }

    /**
     * Advances to the next floor in the dungeon and updates the view
     * with the new floor's structure data.
     */
    public void next() {
        dungeon.nextFloor();
        System.out.println(dungeon.getCurrentFloorIndex());
        final Floor currentFloor = dungeon.getCurrentFloor();
        model.bindCurrentFloor(currentFloor);

        new OverworldEntitiesGenerator(
                currentFloor,
                model.getPlayer(),
                model,
                model.gridNotifier   // è lo stesso che model ha bindato al floor
        );

        // Render UI
        final StructureData grid = currentFloor.grid();
        SwingUtilities.invokeLater(() -> view.render(grid));
    }
}
