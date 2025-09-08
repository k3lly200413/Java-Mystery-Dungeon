package it.unibo.progetto_oop.Overworld.MVC;

import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.JPanel;

import it.unibo.progetto_oop.Overworld.MVC.InputBindings.InputBindings;
import it.unibo.progetto_oop.Overworld.PlayGround.view.SwingMapView;
import it.unibo.progetto_oop.Overworld.ViewManagerObserver.ViewManagerObserver;
import it.unibo.progetto_oop.Overworld.enemy.creation_pattern.FactoryImpl.Enemy;

import java.awt.event.ActionEvent;

public class OverworldController implements ViewManagerObserver {
    /**
     * the model instance.
     */
    private final OverworldModel model;

    /**
     * the view instance.
     */
    private final SwingMapView view;

    /**
     * the view manager instance.
     */
    private final ViewManager viewManager;

    /**
     * Constructor for the OverworldController.
     * @param newModel the model instance
     * @param newView the view instance
     * @param newViewManager the view manager instance
     */
    public OverworldController(final OverworldModel newModel,
    final SwingMapView newView, final ViewManager newViewManager) {
        this.model = Objects.requireNonNull(newModel, "Model cannot be null");
        this.view = Objects.requireNonNull(newView, "View cannot be null");
        this.viewManager = Objects.requireNonNull(newViewManager,
            "View Manager cannot be null");

        this.initializeInputBindings();
    }

    /**
     * Initialize input bindings for the controller.
     */
    public void initializeInputBindings() {
        JPanel panel = this.view;

        // Bind pressed buttons to keys
        InputBindings bindings = new InputBindings(panel);
        bindings.setBindings();

        // Bind keys to actions
        ActionMap actionMap = panel.getActionMap();

        // action map
        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                model.moveUp();
                view.setCameraTarget(model.getPlayer().getPosition());
                view.repaint();
            }
        });

        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                model.moveDown();
                view.setCameraTarget(model.getPlayer().getPosition());
                view.repaint();
            }
        });

        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                model.moveLeft();
                view.setCameraTarget(model.getPlayer().getPosition());
                view.repaint();
            }
        });

        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                model.moveRight();
                view.setCameraTarget(model.getPlayer().getPosition());
                view.repaint();
            }
        });

        actionMap.put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                viewManager.showInventory(model.getInventoryInstance());
                System.out.println("Menu aperto!");
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
}
