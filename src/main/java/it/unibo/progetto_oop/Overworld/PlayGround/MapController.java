package JMD;

public class MapController {
    private final Floor model;
    private final MapView view;

    public MapController(Floor model, MapView view) {
        this.model = model;
        this.view = view;
    }

    public void show() {
        this.view.render(this.model.getPlayGround());
    }
}
