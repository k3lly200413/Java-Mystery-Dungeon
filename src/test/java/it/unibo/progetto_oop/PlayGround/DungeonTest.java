package it.unibo.progetto_oop.PlayGround;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.FloorConfig;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.Dungeon;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.Floor;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.FloorGenerator;
import it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy.ImplRandomPlacement;
import it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy.ImplRoomPlacement;
import it.unibo.progetto_oop.Overworld.PlayGround.PlacementStrategy.ImplTunnelPlacement;
import it.unibo.progetto_oop.Overworld.Player.Player;

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
        Player p = new Player(100, new Inventory());
        return new Dungeon(gen, conf, p, new OverworldModel(p, null, null, null));
    }

    @Test
    void FirstFloorCreated() {
        Dungeon d = newDungeon(3);
        assertNotNull(d.getCurrentFloor(), "Il piano corrente non deve essere null");
        assertEquals(0, d.getCurrentFloorIndex());
    }

    @Test
    void nextFloorAdvancesUntilMax() {
        Dungeon d = newDungeon(3);

        assertTrue(d.nextFloor(), "Dovrebbe passare al piano 1");
        assertNotNull(d.getCurrentFloor());
        assertEquals(1, d.getCurrentFloorIndex());

        assertTrue(d.nextFloor(), "Dovrebbe passare al piano 2");
        assertEquals(2, d.getCurrentFloorIndex());

        // ultimo piano -> nextFloor() dovrebbe fallire
        assertFalse(d.nextFloor(), "Max piani raggiunto");
        assertEquals(2, d.getCurrentFloorIndex());
    }

    @Test
void sameInstanceWithoutNextFloorAndDifferentInstancesWhenAdvancing() {
    Dungeon d = newDungeon(3);

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
