package it.unibo.progetto_oop.overworld.mvc;

import java.awt.event.ActionEvent;
import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.JPanel;

import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.overworld.mvc.input_bindings.InputBindings;
import it.unibo.progetto_oop.overworld.playground.view.playground_view.ImplMapView;
import it.unibo.progetto_oop.overworld.view_manager_observer.ViewManagerObserver;

/**
 * Controller principale dell'overworld.
 */
public class OverworldController implements ViewManagerObserver {
    /**
     * the model instance.
     */
    private final OverworldModel model;

    /**
     * the view instance.
     */
    private final ImplMapView view;

    /**
     * the view manager instance.
     */
    private final ViewManager viewManager;

    /**
     * Constructor for the OverworldController.
     *
     * @param newModel the model instance
     * @param newView the view instance
     * @param newViewManager the view manager instance
     */
    public OverworldController(final OverworldModel newModel,
    final ImplMapView newView, final ViewManager newViewManager) {
        this.model = Objects.requireNonNull(newModel, "Model cannot be null");
        this.view = Objects.requireNonNull(newView, "View cannot be null");
        this.viewManager = Objects.requireNonNull(newViewManager,
            "View Manager cannot be null");

        this.initializeInputBindings();
    }

    /**
     * Initialize input bindings for the controller.
     */
    public final void initializeInputBindings() {
        final JPanel panel = this.view;

        // Bind pressed buttons to keys
        final InputBindings bindings = new InputBindings(panel);
        bindings.setBindings();

        // Bind keys to actions
        final ActionMap actionMap = panel.getActionMap();

        // action map
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                model.move(MoveDirection.UP);
                view.setCameraTarget(model.getPlayer().getPosition());
                view.repaint();
            }
        });

        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                model.move(MoveDirection.DOWN);
                view.setCameraTarget(model.getPlayer().getPosition());
                view.repaint();
            }
        });

        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                model.move(MoveDirection.LEFT);
                view.setCameraTarget(model.getPlayer().getPosition());
                view.repaint();
            }
        });

        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                model.move(MoveDirection.RIGHT);
                view.setCameraTarget(model.getPlayer().getPosition());
                view.repaint();
            }
        });

        actionMap.put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                viewManager.showInventory(model.getInventoryInstance());
            }
        });

        actionMap.put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
            }
        });

        actionMap.put("Space", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
            }
        });
    }

    @Override
    public final void onPlayerEnemyContact(final Enemy encounteredEnemy) {
        this.viewManager.showCombat(encounteredEnemy);
    }

    @Override
    public final void onEnemyDefeat() {
        this.viewManager.showOverworld();
    }

    @Override
    public final void onPlayerDefeat() {
        this.viewManager.showGameOver();
    }

    @Override
    public final void onPlayerWin() {
        this.viewManager.showWin();
    }
}
