package it.unibo.progetto_oop.Overworld.PlayGround;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public final class ImplRandomPlacement implements RandomPlacementStrategy {

    @Override
    public void placeObject(StructureData g, TileType type, int n, Random rand) {
        if (g == null || rand == null || n <= 0) return;

        List<Point> candidates = collectCandidates(g);
        if (candidates.isEmpty()) return;

        Collections.shuffle(candidates, rand);
        int limit = Math.min(n, candidates.size());
        for (int i = 0; i < limit; i++) {
            Point p = candidates.get(i);
            g.set(p.x, p.y, type);
        }
    }

    /** Celle candidate: tutte le ROOM non adiacenti (8-neigh) a TUNNEL. */
    private static List<Point> collectCandidates(StructureData g) {
        List<Point> out = new ArrayList<>();
        for (int y = 0; y < g.height(); y++) {
            for (int x = 0; x < g.width(); x++) {
                TileType t = g.get(x, y);
                if (t != TileType.ROOM) continue;
                if (adjacentToTunnel8(g, x, y)) continue;
                out.add(new Point(x, y));
            }
        }
        return out;
    }

    /** true se un vicino (8-neighborhood) è TUNNEL */
    private static boolean adjacentToTunnel8(StructureData g, int x, int y) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dx == 0 && dy == 0) continue;
                int nx = x + dx, ny = y + dy;
                if (!g.inBounds(nx, ny)) continue;
                if (g.get(nx, ny) == TileType.TUNNEL) return true;
            }
        }
        return false;
    }

    /** piccolo record/POJO per coordinate */
    private static final class Point {
        final int x, y;
        Point(int x, int y) { this.x = x; this.y = y; }
    }
}
