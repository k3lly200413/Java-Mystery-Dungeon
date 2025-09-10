package it.unibo.progetto_oop.overworld.grid_notifier;

import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.listner.grid_updater.GridUpdater;

public class GridNotifier {
    GridUpdater grid;
    ListEnemyUpdater listEnemyUpdater;
    ListItemUpdater listItemUpdater;

    public GridNotifier(GridUpdater grid) {
        this.grid = grid;
    }

    public void setGridUpdater(GridUpdater grid) {
        this.grid = grid;
    }

    public void setListEnemyUpdater(ListEnemyUpdater listEnemyUpdater) {
        this.listEnemyUpdater = listEnemyUpdater;
    }
    public void setListItemUpdater(ListItemUpdater listItemUpdater) {
        this.listItemUpdater = listItemUpdater;
    }

    // gridUpdater methods
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
        if (grid != null)
            grid.onEnemyRemoved(at);
    }
    
    //listEnemyUpdater methods
    public void notifyListEnemyRemoved(Position at) {
        if (listEnemyUpdater != null)
            listEnemyUpdater.onEnemyRemoved(at);
    }

    //listItemUpdater methods
    public void notifyListItemRemoved(Position at) {
        if (listItemUpdater != null)
            listItemUpdater.onItemRemoved(at);
    }
}
