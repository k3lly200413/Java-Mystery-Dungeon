package it.unibo.progetto_oop.Overworld.MVC;

import java.util.Objects;

import javax.swing.*;

import it.unibo.progetto_oop.Overworld.MVC.InputBindings.InputBindings;
import it.unibo.progetto_oop.Overworld.PlayGround.view.SwingMapView;

import java.awt.event.ActionEvent;

public class OverworldController {
    private final OverworldModel model;
    private final SwingMapView view; 
    private final ViewManager viewManager;

    public OverworldController(OverworldModel model, SwingMapView view, ViewManager game){
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.view = Objects.requireNonNull(view, "View cannot be null");
        this.viewManager = Objects.requireNonNull(game, "Game Application cannot be null");

        this.initializeImputBindings();
    }

    public void initializeImputBindings() {
        JPanel panel = this.view.getPanel();

        // Bind pressed puttons to keys
        InputBindings bindings = new InputBindings(panel);
        bindings.setBindings();

        // Bind keys to actions
        ActionMap actionMap = panel.getActionMap();
    
        // action map
        actionMap.put("moveUp", new AbstractAction() { // TODO: magari implementare move right, left....
            int dy = -1;
            int dx = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                model.movePlayer(dx, dy);
                view.repaint();
            }
        });

        actionMap.put("moveDown", new AbstractAction() {
            int dy = 1;
            int dx = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                model.movePlayer(dx, dy);  
                view.repaint();
            }
        });

        actionMap.put("moveLeft", new AbstractAction() {
            int dy = 0;
            int dx = -1;

            @Override
            public void actionPerformed(ActionEvent e) {
                model.movePlayer(dx, dy);
                view.repaint();
            }
        });

        actionMap.put("moveRight", new AbstractAction() {
            int dy = 0;
            int dx = 1;

            @Override
            public void actionPerformed(ActionEvent e) {
                model.movePlayer(dx, dy);
                view.repaint();
            }
        });

        actionMap.put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManager.showInventory(model.getInventoryInstance());
                System.out.println("Menu aperto!"); 
            }
        });

        actionMap.put("Enter", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });

        actionMap.put("Space", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
    }
}
        
