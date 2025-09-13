package it.unibo.progetto_oop.overworld.playground.placement_strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ImplArrayListStructureData;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ReadOnlyGrid;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ReadOnlyGridAdapter;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.StructureData;

// CHECKSTYLE: MagicNumber OFF
class RandomPlacementTest {

    @Test
    void testPlaceOnBase() {
        final StructureData base = new ImplArrayListStructureData(10, 8);
        base.fill(TileType.ROOM);

        final ImplRandomPlacement placer = new ImplRandomPlacement();
        final Random rand = new Random(123);

        placer.placeOnBase(base, TileType.STAIRS, 12, rand);

        assertEquals(12, count(base, TileType.STAIRS));
    }

    @Test
    void testPlaceObjectConstraints() {
        final StructureData base = new ImplArrayListStructureData(12, 10);
        final StructureData entity = new ImplArrayListStructureData(12, 10);
        base.fill(TileType.ROOM);
        entity.fill(TileType.NONE);

        // tunnel to test adjacency
        base.set(5, 5, TileType.TUNNEL);
        base.set(6, 5, TileType.TUNNEL);

        final ReadOnlyGrid baseRO = ReadOnlyGridAdapter.of(base);

        final Position player = new Position(2, 2);
        entity.set(player.x(), player.y(), TileType.PLAYER);

        final int n = 8;
        final int minDist = 3;
        final ImplRandomPlacement placer = new ImplRandomPlacement();
        placer.placeObject(
                baseRO, entity, TileType.ENEMY, n,
                new Random(123), player, minDist
        );

        int placed = 0;
        for (int y = 0; y < entity.height(); y++) {
            for (int x = 0; x < entity.width(); x++) {
                if (entity.get(x, y) == TileType.ENEMY) {
                    placed++;
                    assertTrue(
                            ImplRandomPlacement.isFarFromPlayer(
                                x, y, player, minDist
                            ),
                            "Troppo vicino al player in (" + x + "," + y + ")");
                    assertFalse(
                            ImplRandomPlacement.adjacentToTunnel(baseRO, x, y),
                            "Adiacente a TUNNEL in (" + x + "," + y + ")");
                }
            }
        }
        assertTrue(placed <= n, "Non deve piazzare più di n nemici");
    }

    @Test
    void testPlacePlayer() {
        final StructureData base = new ImplArrayListStructureData(7, 6);
        final StructureData entity = new ImplArrayListStructureData(7, 6);
        base.fill(TileType.ROOM);
        entity.fill(TileType.NONE);

        // tunnel to test
        base.set(3, 2, TileType.TUNNEL);
        base.set(4, 2, TileType.TUNNEL);

        final ReadOnlyGrid baseRO = ReadOnlyGridAdapter.of(base);

        final ImplRandomPlacement placer = new ImplRandomPlacement();
        final Random rand = new Random(123);

        final Position p = placer.placePlayer(baseRO, entity, rand);
        assertNotNull(p, "placePlayer mustn't return null");
        assertEquals(TileType.PLAYER, entity.get(p.x(), p.y()));
        assertEquals(TileType.ROOM, base.get(p.x(), p.y()));
    }

    @Test
    void testPlaceObjectCap() {
        final StructureData base = new ImplArrayListStructureData(5, 5);
        final StructureData entity = new ImplArrayListStructureData(5, 5);
        base.fill(TileType.ROOM);
        entity.fill(TileType.NONE);

        final ReadOnlyGrid baseRO = ReadOnlyGridAdapter.of(base);

        final Position player = new Position(0, 0);
        entity.set(player.x(), player.y(), TileType.PLAYER);

        final ImplRandomPlacement placer = new ImplRandomPlacement();
        final Random rand = new Random(123);

        final int requested = 100; // more than available
        final int minDist = 1;

        placer.placeObject(
            baseRO, entity, TileType.ENEMY, requested, rand, player, minDist
        );

        final int placed = count(entity, TileType.ENEMY);
        final int maxPossible = base.width() * base.height() - 1; // not the player

        assertTrue(placed <= maxPossible);
    }

    private static int count(final StructureData g, final TileType t) {
        int c = 0;
        for (int y = 0; y < g.height(); y++) {
            for (int x = 0; x < g.width(); x++) {
                if (g.get(x, y) == t) {
                    c++;
                }
            }
        }
        return c;
    }

}
