package it.unibo.progetto_oop.WallCollision;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision.WallCollisionImpl;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;

class WallCollisionImplTest {

    private StructureData gridMock;
    private WallCollisionImpl wallCollision;

    @BeforeEach
    void setup() {
        gridMock = mock(StructureData.class);
        wallCollision = new WallCollisionImpl(gridMock);

        // mock dimensioni base
        when(gridMock.width()).thenReturn(5);
        when(gridMock.height()).thenReturn(5);
    }

    @Test
    void testInBounds() {
        Position inside = new Position(2, 3);
        Position outside = new Position(6, 0);

        assertTrue(wallCollision.inBounds(inside));
        assertFalse(wallCollision.inBounds(outside));
    }

    @Test
    void testCanEnter_NotWall() {
        Position p = new Position(1, 1);
        when(gridMock.get(1, 1)).thenReturn(TileType.ROOM);

        assertTrue(wallCollision.canEnter(p));
    }

    @Test
    void testCanEnter_Wall() {
        Position p = new Position(2, 2);
        when(gridMock.get(2, 2)).thenReturn(TileType.WALL);

        assertFalse(wallCollision.canEnter(p));
    }

    @Test
    void testCanEnemyEnter_Room() {
        Position p = new Position(3, 3);
        when(gridMock.get(3, 3)).thenReturn(TileType.ROOM);

        assertTrue(wallCollision.canEnemyEnter(p));
    }

    @Test
    void testCanEnemyEnter_NotRoom() {
        Position p = new Position(3, 4);
        when(gridMock.get(3, 4)).thenReturn(TileType.WALL);

        assertFalse(wallCollision.canEnemyEnter(p));
    }

    @Test
    void testClosestWall_FindsWall() {
        Position from = new Position(0, 0);

        // simuliamo muro a (3,0)
        when(gridMock.get(1, 0)).thenReturn(TileType.ROOM);
        when(gridMock.get(2, 0)).thenReturn(TileType.ROOM);
        when(gridMock.get(3, 0)).thenReturn(TileType.WALL);

        Optional<Position> wall = wallCollision.closestWall(from, 1, 0);

        assertTrue(wall.isPresent());
        assertEquals(new Position(2, 0), wall.get());
    }

    @Test
    void testClosestWall_NoWallFound() {
        Position from = new Position(0, 0);

        // tutto ROOM
        when(gridMock.get(anyInt(), anyInt())).thenReturn(TileType.ROOM);

        Optional<Position> wall = wallCollision.closestWall(from, 1, 0);

        assertTrue(wall.isEmpty());
    }

}