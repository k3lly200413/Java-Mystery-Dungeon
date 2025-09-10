package it.unibo.progetto_oop.overworld.playground;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Random;

import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.overworld.playground.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Dungeon;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Floor;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.FloorGenerator;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.ImplRandomPlacement;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.ImplRoomPlacement;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.ImplTunnelPlacement;

/**
 * Test basilari su Dungeon: creazione, avanzamento piano, limiti.
 */
public class DungeonTest {

    private Dungeon newDungeon(final int maxFloors) {
        FloorConfig conf = new FloorConfig.Builder()
                .size(20, 15)
                .rooms(3)
                .roomSize(3, 8, 3, 8)
                .floors(maxFloors)
                .tileSize(14)
                .build();

        FloorGenerator gen = new FloorGenerator(
                new ImplRoomPlacement(),
                new ImplTunnelPlacement(),
                new ImplRandomPlacement(),
                new Random()
        );
        return new Dungeon(gen, conf);
    }

    @Test
    void firstFloorCreated() {
        Dungeon d = newDungeon(3);
        assertTrue(d.nextFloor(), "create floor 0 must succeed");
        assertEquals(0, d.getCurrentFloorIndex());
        assertNotNull(
            d.getCurrentFloor(),
            "floor 0 must be non-null after nextFloor()"
        );
    }

    @Test
    void nextFloorAdvancesUntilMax() {
        Dungeon d = newDungeon(3);

        assertTrue(d.nextFloor());               // floor 0
        assertEquals(0, d.getCurrentFloorIndex());

        assertTrue(d.nextFloor());               // floor 1
        assertEquals(1, d.getCurrentFloorIndex());

        assertTrue(d.nextFloor());               // floor 2
        assertEquals(2, d.getCurrentFloorIndex());
        // max reached
        assertFalse(d.nextFloor());
        assertEquals(2, d.getCurrentFloorIndex());
    }

    @Test
    void sameInstanceWithoutNextFloorAndDifferentInstancesWhenAdvancing() {
        Dungeon d = newDungeon(3);
        assertTrue(d.nextFloor());              // floor 0

        // two consecutive calls -> same instance
        Floor f1a = d.getCurrentFloor();
        Floor f1b = d.getCurrentFloor();
        assertSame(
            f1a, f1b, "without advancing the floor must be the same instance"
        );

        // advance -> new instance
        assertTrue(d.nextFloor(), "Should advance to floor 1");
        Floor f2 = d.getCurrentFloor();
        assertNotSame(
            f1a, f2, "Each advancement must produce a different instance"
        );

        // two consecutive calls -> same instance
        Floor f2a = d.getCurrentFloor();
        Floor f2b = d.getCurrentFloor();
        assertSame(
            f2a, f2b, "Without advancing the floor must be the same instance"
        );

        assertTrue(d.nextFloor(), "Should advance to floor 2");
        Floor f3 = d.getCurrentFloor();
        assertNotSame(
            f2, f3, "Each advancement must produce a different instance"
        );
    }

}
