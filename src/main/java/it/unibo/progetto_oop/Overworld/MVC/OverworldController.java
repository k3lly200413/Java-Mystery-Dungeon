package it.unibo.progetto_oop.Overworld.MVC;

import java.util.Objects;

import it.unibo.progetto_oop.Overworld.MVC.*;

public class OverworldController {
    private final OverworldModel model;
    private final OverworldView view; 
    private final OverworldApplication game;

    public OverworldController(OverworldModel model, OverworldView view, OverworldApplication game){
        this.model = Objects.requireNonNull(model, "Model cannot be null");
        this.view = Objects.requireNonNull(view, "View cannot be null");
        this.game = Objects.requireNonNull(game, "Game Application cannot be null");
    }

    public void initializeImputBindings() {
        // TODO
    }
        
}
