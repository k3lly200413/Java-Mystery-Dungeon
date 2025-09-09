package it.unibo.progetto_oop.overworld.mvc.input_bindings;

import java.awt.event.KeyEvent;

import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

public class InputBindings {
    /**
     * The panel to which the input bindings will be added.
     */
    private final JComponent panel;

    /**
     * Constructor of the InputBindings class.
     * @param component the panel to which the input bindings will be added
     */
    public InputBindings(final JComponent component) {
        this.panel = component;
    }

    /**
     * Set the input bindings for the panel.
     */
    public void setBindings() {
        final InputMap inputMap = panel.
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        // ----keys---- //

        final String moveUp = "moveUp";
        final String moveDown = "moveDown";
        final String moveLeft = "moveLeft";
        final String moveRight = "moveRight";
        final String escape = "escape";
        final String enter = "Enter";
        final String space = "Space";

        // Fill the input map with key bindings
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), moveUp);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0), moveUp);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), moveDown);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0), moveDown);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), moveLeft);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0), moveLeft);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), moveRight);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0), moveRight);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), escape);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), enter);

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), space);
    }
}
