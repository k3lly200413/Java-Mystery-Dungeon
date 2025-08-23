package it.unibo.progetto_oop.Overworld.PlayGround;

public class MapController {
    private final Dungeon dungeon;
    private final MapView view;

    public MapController(Dungeon dungeon, MapView view) {
        this.dungeon = dungeon;
        this.view = view;
        Floor f = dungeon.getCurrentFloor();
        f.addObserver(view);
        view.render(f.grid());
    }

    public void show() {
        view.render(dungeon.getCurrentFloor().grid());
    }

    public void nextFloor(Dungeon dungeon) {
    if (dungeon.nextFloor()) {
        Floor f = dungeon.getCurrentFloor();
        f.addObserver(view);
        view.render(f.grid());
    }
}
}

