package it.unibo.progetto_oop.overworld.playground.placement_strategy;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.overworld.playground.data.StructureData;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Room;

public class ImplTunnelPlacement implements TunnelPlacementStrategy {

    @Override
    public void connect(StructureData grid, List<Room> rooms, Random rand) {
        for (int i = 0; i < rooms.size() - 1; i++) {
            Room r1 = rooms.get(i);
            Room r2 = rooms.get(i + 1);
            int x1 = r1.getX() + r1.getWidth() / 2;
            int y1 = r1.getY() + r1.getHeight() / 2;
            int x2 = r2.getX() + r2.getWidth() / 2;
            int y2 = r2.getY() + r2.getHeight() / 2;

            if (rand.nextBoolean()) {
                connectHorizontal(grid, x1, x2, y1);
                connectVertical(grid, y1, y2, x2);
            } else {
                connectVertical(grid, y1, y2, x1);
                connectHorizontal(grid, x1, x2, y2);
            }
        }
    }

    private void connectHorizontal(StructureData grid, int x1, int x2, int y) {
        int startX = Math.min(x1, x2);
        int endX = Math.max(x1, x2);
        for (int x = startX; x <= endX; x++) {
            setTunnelIfWall(grid, x, y);
        }
    }

    private void connectVertical(StructureData grid, int y1, int y2, int x) {
        int startY = Math.min(y1, y2);
        int endY = Math.max(y1, y2);
        for (int y = startY; y <= endY; y++) {
            setTunnelIfWall(grid, x, y);
        }
    }

    /**
     * Scava TUNNEL solo se la cella è WALL; non modifica FLOOR (stanze) né cambia
     * TUNNEL già esistenti.
     */
    private void setTunnelIfWall(StructureData grid, int x, int y) {
        if (!grid.inBounds(x, y))
            return;
        TileType t = grid.get(x, y);
        if (t == TileType.WALL) {
            grid.set(x, y, TileType.TUNNEL);
        }
        // se è FLOOR (stanza) o già TUNNEL, non fare nulla
    }
}
