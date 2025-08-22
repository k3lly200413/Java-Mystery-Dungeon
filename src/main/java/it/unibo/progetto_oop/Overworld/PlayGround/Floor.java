package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Floor {
    private final StructureData grid;
    private final List<Room> rooms;
    private final List<FloorObserver> observers = new ArrayList<>();

    public Floor(FloorConfig conf, FloorGenerator gen) {
        Objects.requireNonNull(gen);
        this.grid = new ImplArrayListStructureData(conf.width(),conf.height()); // oggi ArrayGrid; domani cambi qui.
        this.rooms = List.copyOf(gen.generate(grid, conf)); // Immutable list of rooms
        notifyObservers();
    }

    public StructureData grid() {
        return grid;
    }

    public List<Room> rooms() {
        return rooms;
    }

    public void addObserver(FloorObserver o) {
        observers.add(Objects.requireNonNull(o));
    }

    public void removeObserver(FloorObserver o) {
        observers.remove(o);
    }

    private void notifyObservers() {
        for (var o : observers)
            o.floorChanged(grid);
    }

     // API di modifica che notificano automaticamente (usale per future mosse/oggetti)
    public void setTile(int x,int y, TileType t){
        if (grid.inBounds(x,y)) {
            grid.set(x,y,t);
            notifyObservers();
        }
    }

    public void refresh() {
        notifyObservers();
    } // utile per forzare un repaint
}
