package it.unibo.progetto_oop.overworld.playground.placement_strategy;

import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.data.StructureData_strategy.StructureData;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Room;

public class ImplTunnelPlacement implements TunnelPlacementStrategy {

    @Override
    public final void connect(final StructureData grid,
                              final List<Room> rooms,
                              final Random rand) {
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

    private void connectHorizontal(final StructureData grid, final int x1,
                                   final int x2, final int y) {
        int startX = Math.min(x1, x2);
        int endX = Math.max(x1, x2);
        for (int x = startX; x <= endX; x++) {
            setTunnelIfWall(grid, x, y);
        }
    }

    private void connectVertical(final StructureData grid,
                                 final int y1,
                                 final int y2,
                                 final int x) {
        int startY = Math.min(y1, y2);
        int endY = Math.max(y1, y2);
        for (int y = startY; y <= endY; y++) {
            setTunnelIfWall(grid, x, y);
        }
    }

    /**
     * Only carve a TUNNEL if the cell is a WALL; do not modify FLOOR (rooms)
     *  or existing TUNNEL tiles.
     * @param grid The structure data representing the grid of the dungeon.
     * @param x The x-coordinate of the cell to check and potentially modify.
     * @param y The y-coordinate of the cell to check and potentially modify.
     */
    private void setTunnelIfWall(final StructureData grid,
                                 final int x, final int y) {
        if (!grid.inBounds(x, y)) {
            return;
        }
        TileType t = grid.get(x, y);
        if (t == TileType.WALL) {
            grid.set(x, y, TileType.TUNNEL);
        }
        // se è FLOOR (stanza) o già TUNNEL, non fare nulla
    }
}
