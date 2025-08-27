package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.Objects;

import javax.swing.SwingUtilities;

public final class MapController {
    private final SwingMapView view;
    private final Dungeon dungeon;

    public MapController(SwingMapView view, Dungeon dungeon) {
        this.view = Objects.requireNonNull(view);
        this.dungeon = Objects.requireNonNull(dungeon);
    }

    public void show() {
        view.onNextFloorRequested(this::next);
        next();
        SwingUtilities.invokeLater(view::showView);
    }

    public void next() {
        dungeon.nextFloor();
        StructureData grid = dungeon.getCurrentFloor().grid();
        SwingUtilities.invokeLater(() -> view.render(grid));
    }
}
