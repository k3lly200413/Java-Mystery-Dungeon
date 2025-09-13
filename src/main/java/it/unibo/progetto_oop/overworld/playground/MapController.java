package it.unibo.progetto_oop.overworld.playground;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.Objects;
import javax.swing.SwingUtilities;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.progetto_oop.overworld.mvc.OverworldModelApi;
import it.unibo.progetto_oop.overworld.playground.data.listner.ChangeFloorListener;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ReadOnlyGrid;
import it.unibo.progetto_oop.overworld.playground.view.playground_view.MapView;

/**
 * Controller class for managing the map view and handling floor changes in the overworld.
 */
@SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification = "MVC: controller keeps references to injected collaborators; "
                      + "does not expose them and uses reduced interfaces."
    )
public final class MapController implements ChangeFloorListener {

    /**
     * Default zoom level for the map view.
     */
    private static final int DEFAULT_ZOOM_LEVEL = 9;

    /**
     * The view responsible for rendering the map.
     */
    private final MapView view;

    /**
     * The model representing the overworld state.
     */
    private final OverworldModelApi model;

    private final KeyEventDispatcher nextFloorOnN = e -> {
        if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_N) {
            next();
            return true;
        }
        return false;
    };

    /**
     * Constructs a MapController with the specified view and model.
     *
     * @param mapView        the view responsible for rendering the map
     * @param overworldModel the model representing the overworld state
     */
    public MapController(final MapView mapView,
            final OverworldModelApi overworldModel) {
        this.view = Objects.requireNonNull(mapView);
        this.model = Objects.requireNonNull(overworldModel);
    }

    /**
     * Starts the map controller by setting up the floor listener
     * and initializing the first floor.
     */
    public void start() {
        model.setChangeFloorListener(this);
        KeyboardFocusManager.getCurrentKeyboardFocusManager()
                .addKeyEventDispatcher(this.nextFloorOnN);
        model.nextFloor();
    }

    /**
     * Advances the game to the next floor.
     */
    public void next() {
        model.nextFloor();
    }


    @Override
    public void onFloorChange(final ReadOnlyGrid base) {
        view.setEntityGrid(model.getEntityGridView());
        view.setCameraTarget(model.getPlayerPosition());
        view.setZoom(DEFAULT_ZOOM_LEVEL);
        SwingUtilities.invokeLater(() -> view.render(base));
    }
}
