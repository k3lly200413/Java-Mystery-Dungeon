package it.unibo.progetto_oop.Overworld.MVC;

import java.util.Objects;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;

import java.awt.event.ActionEvent;

import it.unibo.progetto_oop.Overworld.MVC.InputBindings.InputBindings;

public class OverworldController {
    private final OverworldModel model;
    private final OverworldView view; 

    public OverworldController(OverworldModel model, OverworldView view, viewManager game){
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.view = Objects.requireNonNull(view, "View cannot be null"); // TODO: unire con parte di Alice

        // Initializing bindings
        InputBindings bindings = new InputBindings(view);
        bindings.initializeImputBindings();

        // Link actions to commands
        addActions();
    }

    private void addActions() {
        ActionMap actionMap = view.getActionMap();

        actionMap.put("moveUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dx = 0;
                int dy = -1;

                model.movePlayer(dx, dy);
                view.repaint();
            }
        });

        actionMap.put("moveDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dx = 0;
                int dy = 1;

                model.movePlayer(dx, dy);
                view.repaint();
            }
        });

        actionMap.put("moveRight", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dx = 1;
                int dy = 0;

                model.movePlayer(dx, dy);
                view.repaint();
            }
        });

        actionMap.put("moveLeft", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dx = 1;
                int dy = 0;

                model.movePlayer(dx, dy);
                view.repaint();
            }
        });

        actionMap.put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!model.isInCombat() && !model.isCombatTransitionPending()){
                    // TODO: showInventory
                }
                else {
                    System.out.println("Use buttons, you are in combat right now");
                }
            }
        });

    }
}
