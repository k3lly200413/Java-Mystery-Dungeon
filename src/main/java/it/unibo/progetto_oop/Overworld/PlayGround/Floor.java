package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.List;
import java.util.Objects;

public final class Floor {
    private final StructureData grid;
    private final List<Room> rooms;

    public Floor(int w, int h, FloorGenerator gen) {
        Objects.requireNonNull(gen);
        this.grid = new ImplArrayListStructureData(w, h); // oggi ArrayGrid; domani cambi qui.
        this.rooms = List.copyOf(gen.generate(grid)); // Immutable list of rooms
        System.out.println("[FLOOR] grid " + grid.width() + "x" + grid.height());

    }

    public StructureData grid() {
        return grid;
    }

    public List<Room> rooms() {
        return rooms;
    }
}
