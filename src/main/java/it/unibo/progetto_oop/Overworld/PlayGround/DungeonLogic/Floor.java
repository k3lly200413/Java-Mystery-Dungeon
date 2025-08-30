package it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.FloorConfig;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.GridUpdater;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.ImplArrayListStructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;

public final class Floor implements GridUpdater{
    private final StructureData grid;
    private final List<Room> rooms;

    public Floor(FloorConfig conf, FloorGenerator gen) {
        Objects.requireNonNull(gen);
        this.grid = new ImplArrayListStructureData(conf.width(),conf.height()); // oggi ArrayGrid; domani cambi qui.
        this.rooms = List.copyOf(gen.generate(grid, conf)); // Immutable list of rooms
    }

    public StructureData grid() {
        return grid;
    }

    public List<Room> rooms() {
        return rooms;
    }

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

     @Override
    public void onPlayerMove(Position from, Position to) {
        grid.set(from.x(), from.y(), TileType.ROOM);
        grid.set(to.x(),   to.y(),   TileType.PLAYER);
    }

    @Override
    public void onEnemyMove(Position from, Position to) {
        grid.set(from.x(), from.y(), TileType.ROOM);
        grid.set(to.x(),   to.y(),   TileType.ENEMY);
    }

    @Override
    public void onItemRemoved(Position at) {
        // PLAYER sopra item
        grid.set(at.x(), at.y(), TileType.PLAYER);
    }

    @Override
    public void onEnemyRemoved(Position at) {
        if (grid.get(at.x(), at.y()) != TileType.PLAYER) {
            grid.set(at.x(), at.y(), TileType.ROOM);
        }
    }

}
