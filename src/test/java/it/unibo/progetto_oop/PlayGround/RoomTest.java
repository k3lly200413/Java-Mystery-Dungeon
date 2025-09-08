package it.unibo.progetto_oop.PlayGround;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.overworld.playGround.DungeonLogic.Room;
import it.unibo.progetto_oop.overworld.playGround.data.Position;

public class RoomTest {

    @Test
    void CellsOfRoom() {
        Room r = new Room(2, 5, 3, 2);
        // y=5: (2,5) (3,5) (4,5)
        // y=6: (2,6) (3,6) (4,6)

        List<Position> cells = new ArrayList<>();
        for (Position p : r) {
            cells.add(p);
        }

        assertEquals(6, cells.size());

        assertEquals(new Position(2,5), cells.get(0));
        assertEquals(new Position(3,5), cells.get(1));
        assertEquals(new Position(4,5), cells.get(2));
        assertEquals(new Position(2,6), cells.get(3));
        assertEquals(new Position(3,6), cells.get(4));
        assertEquals(new Position(4,6), cells.get(5));
    }

    @Test
    void checkIteratorHasNext() {
        Room r = new Room(0, 0, 1, 1); // room da una cella sola
        Iterator<Position> it = r.iterator();

        assertTrue(it.hasNext());
        assertEquals(new Position(0, 0), it.next());
        assertFalse(it.hasNext());
        assertThrows(NoSuchElementException.class, () -> {
            it.next();
        });
    }
    
    @Test
    void containsWorksCorrectly() {
        Room r = new Room(2, 5, 3, 2);
        // dentro
        assertTrue(r.contains(new Position(2,5)));
        assertTrue(r.contains(new Position(4,6)));
        // fuori
        assertFalse(r.contains(new Position(5,5)));
        assertFalse(r.contains(new Position(1,5)));
        assertFalse(r.contains(new Position(2,7)));
    }

}
