package it.unibo.progetto_oop.Overworld.MVC.InputBindings;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;

import it.unibo.progetto_oop.Overworld.MVC.OverworldView;

public class InputBindings {
    OverworldView view;
    
    public InputBindings(OverworldView view) {
        this.view = view;
    }


    public void initializeImputBindings() {
        // Bind pressed puttons to keys
        InputMap inputMap = view.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        // keys
        final String MOVE_UP = "moveUp";
        final String MOVE_DOWN = "moveDown";
        final String MOVE_LEFT = "moveLeft";
        final String MOVE_RIGHT = "moveRight";
        final String ESCAPE = "escape";

        // the following ones are not being used for now
        final String ENTER = "Enter"; // to save
        final String SPACE = "Space"; // to load

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
    }
}
