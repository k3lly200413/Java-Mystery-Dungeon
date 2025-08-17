package it.unibo.progetto_oop.Overworld.MVC;

import javax.swing.*;
import java.util.Objects;

public class OverworldView extends JPanel {
    private final OverworldModel model;


     /**
     * Constructor 
     */
    public OverworldView(OverworldModel model) {
        this.model = Objects.requireNonNull(model, "Model cannot be null");
    }
    
}
