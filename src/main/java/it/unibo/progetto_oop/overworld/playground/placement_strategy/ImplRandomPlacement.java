package it.unibo.progetto_oop.overworld.playground.placement_strategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.StructureData;
import it.unibo.progetto_oop.overworld.playground.data.TileType;

public final class ImplRandomPlacement implements RandomPlacementStrategy {

    @Override
    public void placeOnBase(StructureData base, TileType type, int n, Random rand) {
        if (base == null || type == null || n <= 0 || rand == null) return;

        for (Position p : pickRandomCandidates(base, null, 0, n, rand)) {
            base.set(p.x(), p.y(), type);
        }
    }

    @Override
    public void placeObject(StructureData base, StructureData entity,
                            TileType type, int n, Random rand,
                            Position player, int dist) {
        if (base == null || entity == null || type == null || n <= 0 || rand == null) return;

        for (Position p : pickRandomCandidates(base, player, dist, n, rand)) {
            entity.set(p.x(), p.y(), type);
        }
    }

    @Override
    public Position placePlayer(StructureData base, StructureData entity, Random rand) {
        if (base == null || entity == null || rand == null) return null;

        List<Position> one = pickRandomCandidates(base, null, 0, 1, rand);
        if (one.isEmpty()) return null;

        Position p = one.get(0);
        entity.set(p.x(), p.y(), TileType.PLAYER);
        return p;
    }

    private static List<Position> pickRandomCandidates(
            StructureData base, Position player, int minDist, int n, Random rand) {

        List<Position> candidates = collectCandidates(base, player, minDist);
        if (candidates.isEmpty() || n <= 0 || rand == null) {
            return List.of();
        }
        Collections.shuffle(candidates, rand);
        int limit = Math.min(n, candidates.size());
        return candidates.subList(0, limit);
    }

    // Celle candidate: ROOM non adiacenti a TUNNEL e lontane dal player
    private static List<Position> collectCandidates(StructureData g, Position player, int dist) {
        List<Position> out = new ArrayList<>();
        for (int y = 0; y < g.height(); y++) {
            for (int x = 0; x < g.width(); x++) {
                TileType t = g.get(x, y);
                if (t == TileType.ROOM
                        && !adjacentToTunnel(g, x, y)
                        && isFarFromPlayer(x, y, player, dist)) { // <= basta questa
                    out.add(new Position(x, y));
                }
            }
        }
        return out;
    }

    // true se cella vicino di 1 è TUNNEL
    public static boolean adjacentToTunnel(StructureData g, int x, int y) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                int nx = x + dx;
                int ny = y + dy;

                // salta il centro e controlla solo se è in-bounds
                if ((dx != 0 || dy != 0)
                        && g.inBounds(nx, ny)
                        && g.get(nx, ny) == TileType.TUNNEL) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isFarFromPlayer(int x, int y, Position playerPos, int minDist) {
        if (playerPos == null || minDist <= 0)
            return true;
        int dx = Math.abs(x - playerPos.x());
        int dy = Math.abs(y - playerPos.y());
        int dist = Math.max(dx, dy);
        return dist >= minDist;
    }
}
