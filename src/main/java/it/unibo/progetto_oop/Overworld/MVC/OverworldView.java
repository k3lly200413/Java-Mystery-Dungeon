package it.unibo.progetto_oop.Overworld.MVC;

import java.util.Objects;

import javax.swing.JPanel;

public class OverworldView extends JPanel {
    private final OverworldModel model;


     /**
     * Constructor 
     */
    public OverworldView(OverworldModel model) {
        this.model = Objects.requireNonNull(model, "Model cannot be null");
    }
    
} //NON SERVE AL MOMENTO NON MOSTRA NULLA ( PROBABILMENTE NON SERVIRÀ MAI QUI)
