package it.unibo.progetto_oop.playground;

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

    private Dungeon newDungeon(int maxFloors) {
        FloorConfig conf = new FloorConfig.Builder()
                .size(20, 15)
                .rooms(3)
                .roomSize(3, 8, 3, 8)
                .floors(maxFloors)
                .tileSize(14)
                .build();

        FloorGenerator gen = new FloorGenerator(new ImplRoomPlacement(), new ImplTunnelPlacement(), new ImplRandomPlacement(), new Random());
        return new Dungeon(gen, conf);
    }

    @Test
    void FirstFloorCreated() {
        Dungeon d = newDungeon(3);
        assertTrue(d.nextFloor(), "creo piano 0 partendo da -1");
        assertEquals(0, d.getCurrentFloorIndex());
        assertNotNull(d.getCurrentFloor(), "Il piano 0 deve esistere dopo il primo nextFloor()");
    }

    @Test
    void nextFloorAdvancesUntilMax() {
        Dungeon d = newDungeon(3);
        
        assertTrue(d.nextFloor());               // piano 0
        assertEquals(0, d.getCurrentFloorIndex());
        
        assertTrue(d.nextFloor());               // piano 1
        assertEquals(1, d.getCurrentFloorIndex());
        
        assertTrue(d.nextFloor());               // piano 2
        assertEquals(2, d.getCurrentFloorIndex());
        // max raggiunto
        assertFalse(d.nextFloor());
        assertEquals(2, d.getCurrentFloorIndex());
    }

    @Test
    void sameInstanceWithoutNextFloor_And_DifferentInstancesWhenAdvancing() {
        Dungeon d = newDungeon(3);
        assertTrue(d.nextFloor());              // piano 0

        // due chiamate consecutive -> stessa istanza
        Floor f1a = d.getCurrentFloor();
        Floor f1b = d.getCurrentFloor();
        assertSame(f1a, f1b, "Senza avanzare il piano deve restare la stessa istanza");

        // avanza -> nuova istanza
        assertTrue(d.nextFloor(), "Dovrebbe avanzare al piano 1");
        Floor f2 = d.getCurrentFloor();
        assertNotSame(f1a, f2, "Ogni avanzamento deve produrre un'istanza diversa");

        //due chiamate consecutive -> stessa istanza
        Floor f2a = d.getCurrentFloor();
        Floor f2b = d.getCurrentFloor();
        assertSame(f2a, f2b, "Senza avanzare il piano deve restare la stessa istanza");

        assertTrue(d.nextFloor(), "Dovrebbe avanzare al piano 2");
        Floor f3 = d.getCurrentFloor();
        assertNotSame(f2, f3, "Ogni avanzamento deve produrre un'istanza diversa");
    }

}
