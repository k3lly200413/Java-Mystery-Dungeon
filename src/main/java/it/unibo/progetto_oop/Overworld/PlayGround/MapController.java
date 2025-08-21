package it.unibo.progetto_oop.Overworld.PlayGround;

public class MapController {
    private final Floor model;
    private final MapView view;

    public MapController(Floor model, MapView view) {
        this.model = model;
        this.view = view;
    }

    public void show() {
        view.render(model.grid());
    }
}

