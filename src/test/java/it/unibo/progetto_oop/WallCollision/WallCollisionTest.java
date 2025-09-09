package it.unibo.progetto_oop.WallCollision;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision.WallCollisionImpl;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.StructureData;
import it.unibo.progetto_oop.overworld.playground.data.TileType;

public class WallCollisionTest {

    private StructureData gridMock;
    private StructureData entityGridMock;
    private WallCollisionImpl wallCollision;

    @BeforeEach
    void setup() {
        gridMock = mock(StructureData.class);
        entityGridMock = mock(StructureData.class);
        wallCollision = new WallCollisionImpl(gridMock, entityGridMock);

        // mock dimensioni base
        when(gridMock.width()).thenReturn(5);
        when(gridMock.height()).thenReturn(5);
        when(entityGridMock.width()).thenReturn(5);
        when(entityGridMock.height()).thenReturn(5);

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
        when(entityGridMock.get(3, 3)).thenReturn(TileType.NONE);

        assertTrue(wallCollision.canEnemyEnter(p));
    }

    @Test
    void testCanEnter_Entity() {
        Position p = new Position(3, 3);
        when(gridMock.get(3, 3)).thenReturn(TileType.ROOM);
        when(entityGridMock.get(3, 3)).thenReturn(TileType.ENEMY);

        assertFalse(wallCollision.canEnter(p));

        when(entityGridMock.get(3, 3)).thenReturn(TileType.ITEM);

        // can pickup items
        assertTrue(wallCollision.canEnter(p));
    }

    @Test
    void testCanEnemyEnter_Entity() {
        Position p = new Position(4, 4);
        when(gridMock.get(4, 4)).thenReturn(TileType.ROOM);
        when(entityGridMock.get(4, 4)).thenReturn(TileType.PLAYER);

        assertFalse(wallCollision.canEnemyEnter(p));

        when(entityGridMock.get(4, 4)).thenReturn(TileType.ITEM);

        // enemies can't pick up items
        assertFalse(wallCollision.canEnemyEnter(p));

        when(entityGridMock.get(4, 4)).thenReturn(TileType.ENEMY);

        assertFalse(wallCollision.canEnemyEnter(p));
    }

    @Test
    void testCanEnemyEnter_NotRoom() {
        Position p = new Position(3, 4);
        when(gridMock.get(3, 4)).thenReturn(TileType.WALL);

        assertFalse(wallCollision.canEnemyEnter(p));

        when(gridMock.get(3, 4)).thenReturn(TileType.STAIRS);

        assertFalse(wallCollision.canEnemyEnter(p));
    }

    @Test
    void testClosestWall_FindsWall_x() {
        Position from = new Position(1, 0);

        when(gridMock.width()).thenReturn(6);

        when(gridMock.get(2, 0)).thenReturn(TileType.ROOM);
        when(gridMock.get(4, 0)).thenReturn(TileType.ROOM);
        when(gridMock.get(0, 0)).thenReturn(TileType.WALL);
        when(gridMock.get(3, 0)).thenReturn(TileType.ITEM);

        Optional<Position> wall = wallCollision.closestWall(from, 1, 0);

        assertTrue(wall.isPresent());
        assertEquals(new Position(0, 0), wall.get());
    }

    @Test
    void testClosestWall_FindsWall_Y() {
        Position from = new Position(0, 3);

        when(gridMock.get(0, 1)).thenReturn(TileType.ROOM);
        when(gridMock.get(4, 2)).thenReturn(TileType.ROOM);
        when(gridMock.get(0, 0)).thenReturn(TileType.WALL);
        when(gridMock.get(0, 4)).thenReturn(TileType.ITEM);

        Optional<Position> wall = wallCollision.closestWall(from, 0, 1);

        assertTrue(wall.isPresent());
        assertEquals(new Position(0, 4), wall.get());
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