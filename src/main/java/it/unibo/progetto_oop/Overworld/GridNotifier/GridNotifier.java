package it.unibo.progetto_oop.Overworld.GridNotifier;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.GridUpdater;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;

public class GridNotifier {
    GridUpdater grid;

    public GridNotifier(GridUpdater grid) {
        this.grid = grid;
    }

    public void setGridUpdater(GridUpdater grid) {
        this.grid = grid;
    }

    public void notifyEnemyMoved(Position from, Position to) {
        if (grid != null) grid.onEnemyMove(from, to);
    }

    public void notifyPlayerMoved(Position from, Position to) {
        if (grid != null) grid.onPlayerMove(from, to);
    }

    public void notifyItemRemoved(Position at) {
        if (grid != null) grid.onItemRemoved(at);
    }
    public void notifyEnemyRemoved(Position at) {
        if (grid != null) grid.onEnemyRemoved(at);
    }
}
