
package it.unibo.progetto_oop.combat.helper;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;

public class NeighboursTest {

    private Neighbours neighbours;

    @BeforeAll
    void setUp() {
        this.neighbours = new Neighbours();
    }

    @Test
    void neighboursTest(){
        assertTrue(neighbours.neighbours(new Position(0, 1), new Position(0, 0), 1));
        assertTrue(neighbours.neighbours(new Position(0, 0), new Position(0, 2), 2));
        assertTrue(neighbours.neighbours(new Position(0, 0), new Position(0, 0), 10));
        assertTrue(neighbours.neighbours(new Position(0, 0), new Position(0, 0), 10));
        assertTrue(neighbours.neighbours(new Position(0, 0), new Position(1, 0), 1));
        assertFalse(neighbours.neighbours(new Position(1, 0), new Position(3, 0), 1));
    }

    void deathNeighboursTest() {
        assertTrue(neighbours.deathNeighbours(new Position(0, 0), new Position(1, 1), 1));
        assertTrue(neighbours.deathNeighbours(new Position(0, 0), new Position(1, 0), 1));
        assertTrue(neighbours.deathNeighbours(new Position(0, 0), new Position(0, 1), 1));
        assertTrue(neighbours.deathNeighbours(new Position(0, 0), new Position(0, 0), 10));
        assertFalse(neighbours.deathNeighbours(new Position(0, 0), new Position(2, 2), 10));
    }
}
