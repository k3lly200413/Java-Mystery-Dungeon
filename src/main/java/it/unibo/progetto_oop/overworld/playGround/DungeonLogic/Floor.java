package it.unibo.progetto_oop.overworld.playGround.dungeonLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unibo.progetto_oop.overworld.playGround.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playGround.data.ImplArrayListStructureData;
import it.unibo.progetto_oop.overworld.playGround.data.Position;
import it.unibo.progetto_oop.overworld.playGround.data.StructureData;
import it.unibo.progetto_oop.overworld.playGround.data.TileType;

public final class Floor{
    private final StructureData grid;
    private final List<Room> rooms;

    public Floor(FloorConfig conf, FloorGenerator gen, final boolean finalFloor) {
        Objects.requireNonNull(gen);
        this.grid = new ImplArrayListStructureData(conf.width(), conf.height()); // oggi ArrayGrid; domani cambio qui
        this.rooms = List.copyOf(gen.generate(grid, conf, finalFloor)); // Immutable list of rooms
    }

    public StructureData grid() {
        return grid;
    }

    public List<Room> rooms() {
        return rooms;
    }

    @Deprecated
    public List<Position> getObjectsPositions(TileType tile) {
        final List<Position> positions = new ArrayList<>();
        for (int x = 0; x < grid.width(); x++) {
            for (int y = 0; y < grid.height(); y++) {
                if (grid.get(x, y) == tile) {
                    positions.add(new Position(x, y));
                }
            }
        }
        return positions;
    }

}
