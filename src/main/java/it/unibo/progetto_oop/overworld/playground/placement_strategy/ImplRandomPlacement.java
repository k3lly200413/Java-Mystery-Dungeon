package it.unibo.progetto_oop.overworld.playground.placement_strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ReadOnlyGrid;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ReadOnlyGridAdapter;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.StructureData;

/**
 * Implementation of a random placement strategy for placing objects
 * and the player on a grid-based map.
 * This class provides methods to place objects randomly on valid tiles,
 * ensuring they are not adjacent to tunnels and optionally maintaining
 * a minimum distance from the player's position.
 */
public final class ImplRandomPlacement implements RandomPlacementStrategy {

    @Override
    public void placeOnBase(final StructureData base, final TileType type,
                            final int n, final Random rand) {
        if (base == null || type == null || n <= 0 || rand == null) {
            return;
        }

        for (final Position p : pickRandomCandidates(
            ReadOnlyGridAdapter.of(base), null, 0, n, rand)) {
            base.set(p.x(), p.y(), type);
        }
    }

    @Override
    public void placeObject(
            final ReadOnlyGrid base, final StructureData entity,
            final TileType type, final int n, final Random rand,
            final Position player, final int dist) {
        if (base == null || entity == null || type == null
                || n <= 0 || rand == null) {
            return;
        }

        for (final Position p : pickRandomCandidates(
            base, player, dist, n, rand)) {
            entity.set(p.x(), p.y(), type);
        }
    }

    @Override
    public Position placePlayer(
            final ReadOnlyGrid base,
            final StructureData entity,
            final Random rand) {
        if (base == null || entity == null || rand == null) {
            return null;
        }

        final List<Position> one = pickRandomCandidates(base, null, 0, 1, rand);
        if (one.isEmpty()) {
            return null;
        }

        final Position p = one.get(0);
        entity.set(p.x(), p.y(), TileType.PLAYER);
        return p;
    }

    private static List<Position> pickRandomCandidates(
            final ReadOnlyGrid base,
            final Position player,
            final int minDist,
            final int n,
            final Random rand) {

        final List<Position> candidates = collectCandidates(
            base, player, minDist);
        if (candidates.isEmpty() || n <= 0 || rand == null) {
            return List.of();
        }
        Collections.shuffle(candidates, rand);
        final int limit = Math.min(n, candidates.size());
        return candidates.subList(0, limit);
    }

    private static List<Position> collectCandidates(
            final ReadOnlyGrid g, final Position player, final int dist) {
        final List<Position> out = new ArrayList<>();
        for (int y = 0; y < g.height(); y++) {
            for (int x = 0; x < g.width(); x++) {
                final TileType t = g.get(x, y);
                if (t == TileType.ROOM
                        && !adjacentToTunnel(g, x, y)
                        && isFarFromPlayer(x, y, player, dist)) {
                    out.add(new Position(x, y));
                }
            }
        }
        return out;
    }

    /**
     * Checks if a given cell is adjacent to a tunnel.
     *
     * @param g The structure data representing the grid.
     * @param x The x-coordinate of the cell to check.
     * @param y The y-coordinate of the cell to check.
     * @return true if the cell is adjacent to a tunnel, false otherwise.
     */
    public static boolean adjacentToTunnel(
            final ReadOnlyGrid g,
            final int x,
            final int y) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                final int nx = x + dx;
                final int ny = y + dy;

                // jump the center and check only if in-bounds
                if ((dx != 0 || dy != 0)
                        && g.inBounds(nx, ny)
                        && g.get(nx, ny) == TileType.TUNNEL) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if a given position is far enough from the player's position.
     *
     * @param x        The x-coordinate of the position to check.
     * @param y        The y-coordinate of the position to check.
     * @param playerPos The player's position.
     * @param minDist  The minimum distance required.
     * @return true if the position is far enough, false otherwise.
     */
    public static boolean isFarFromPlayer(
            final int x, final int y,
            final Position playerPos, final int minDist) {
        if (playerPos == null || minDist <= 0) {
            return true;
        }
        final int dx = Math.abs(x - playerPos.x());
        final int dy = Math.abs(y - playerPos.y());
        final int dist = Math.max(dx, dy);
        return dist >= minDist;
    }
}
