package it.unibo.progetto_oop.overworld.playGround.PlacementStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.overworld.playGround.Data.Position;
import it.unibo.progetto_oop.overworld.playGround.Data.StructureData;
import it.unibo.progetto_oop.overworld.playGround.Data.TileType;

public final class ImplRandomPlacement implements RandomPlacementStrategy {

    @Override
    public void placeOnBase(StructureData base, TileType type, int n, Random rand) {
        if (base == null || rand == null || n <= 0) return;

        List<Position> candidates = collectCandidates(base, null, 0);
        if (candidates.isEmpty()) return;

        Collections.shuffle(candidates, rand);
        int limit = Math.min(n, candidates.size());
        for (int i = 0; i < limit; i++) {
            Position p = candidates.get(i);
            base.set(p.x(), p.y(), type);
        }
    }

    @Override
    // distanza minima dal player
    public void placeObject(StructureData base, StructureData entity,
                            TileType type, int n, Random rand,
                            Position player, int dist) {
        if (base == null || entity == null || rand == null || n <= 0) return;

        List<Position> candidates = collectCandidates(base, player, dist);
        if (candidates.isEmpty()) return;

        Collections.shuffle(candidates, rand);
        int limit = Math.min(n, candidates.size());
        for (int i = 0; i < limit; i++) {
            Position p = candidates.get(i);
            entity.set(p.x(), p.y(), type);
        }
    }

    @Override
    public Position placePlayer(StructureData base, StructureData entity, Random rand) {
        if (base == null || entity == null || rand == null) return null;

        List<Position> candidates = collectCandidates(base, null, 0);
        if (candidates.isEmpty()) return null;

        Collections.shuffle(candidates, rand);
        Position p = candidates.get(0);
        entity.set(p.x(), p.y(), TileType.PLAYER);
        return p;
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
    private static boolean adjacentToTunnel(StructureData g, int x, int y) {
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

    private static boolean isFarFromPlayer(int x, int y, Position playerPos, int minDist) {
        if (playerPos == null || minDist <= 0)
            return true;
        int dx = Math.abs(x - playerPos.x());
        int dy = Math.abs(y - playerPos.y());
        int dist = Math.max(dx, dy);
        return dist >= minDist;
    }
}
