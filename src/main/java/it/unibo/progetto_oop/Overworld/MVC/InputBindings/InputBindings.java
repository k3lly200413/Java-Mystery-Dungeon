package it.unibo.progetto_oop.Overworld.MVC.InputBindings;

import java.awt.event.KeyEvent;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class InputBindings {
    private final JComponent panel;

    public InputBindings(JComponent component) {
        this.panel = component;
    }

    public void setBindings() {
        InputMap inputMap = panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

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
    }
        
}
