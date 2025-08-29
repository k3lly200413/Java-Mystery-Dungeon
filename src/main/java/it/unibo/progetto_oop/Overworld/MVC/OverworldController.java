package it.unibo.progetto_oop.Overworld.MVC;

import java.util.Objects;

import javax.swing.*;
import java.awt.event.KeyEvent;

public class OverworldController {
    private final OverworldModel model;
    private final OverworldView view; 
    private final viewManager game;

    public OverworldController(OverworldModel model, OverworldView view, viewManager game){
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.view = Objects.requireNonNull(view, "View cannot be null");
        this.game = Objects.requireNonNull(game, "Game Application cannot be null");
    }

    public void initializeImputBindings() {
        // Bind pressed puttons to keys
        InputMap inputMap = view.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        // Bind keys to actions
        ActionMap actionMap = view.getActionMap();

        // keys
        final String MOVE_UP = "moveUp";
        final String MOVE_DOWN = "moveDown";
        final String MOVE_LEFT = "moveLeft";
        final String MOVE_RIGHT = "moveRight";
        final String ESCAPE = "escape";
        final String ENTER = "Enter";
        final String SPACE = "Space";

        // Fill the input map with key bindings
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), MOVE_UP);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), MOVE_UP);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), MOVE_DOWN);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), MOVE_DOWN);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), MOVE_LEFT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), MOVE_LEFT);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), MOVE_RIGHT);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), MOVE_RIGHT);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), ESCAPE);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), ENTER);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), SPACE);
    
        // TODO: action map
    }
        
}
