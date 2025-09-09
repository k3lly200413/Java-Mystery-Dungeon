package it.unibo.progetto_oop.overworld.wall_collision;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision.WallCollisionImpl;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.StructureData;
import it.unibo.progetto_oop.overworld.playground.data.TileType;

class WallCollisionTest {
    /**
     * mocked structure data for the grid.
     */
    private StructureData gridMock;

    /**
     * mocked structure data for the entity grid.
     */
    private StructureData entityGridMock;

    /**
     * system under test.
     */
    private WallCollisionImpl wallCollision;

    /**
     * max x dimension.
     */
    private static final int MAX_X = 5;

    /**
     * max y dimension.
     */
    private static final int MAX_Y = 5;

    @BeforeEach
    void setup() {
        gridMock = mock(StructureData.class);
        entityGridMock = mock(StructureData.class);
        wallCollision = new WallCollisionImpl(gridMock, entityGridMock);

        // mock dimensioni base
        when(gridMock.width()).thenReturn(MAX_X);
        when(gridMock.height()).thenReturn(MAX_Y);
        when(entityGridMock.width()).thenReturn(MAX_X);
        when(entityGridMock.height()).thenReturn(MAX_Y);

    }

    @Test
    void testInBounds() {
        Position inside = new Position(2, 2);
        Position outside = new Position(MAX_X + 1, 1);

        assertTrue(wallCollision.inBounds(inside));
        assertFalse(wallCollision.inBounds(outside));
    }

    @Test
    void testCanEnterNotWall() {
        Position p = new Position(1, 1);
        when(gridMock.get(1, 1)).thenReturn(TileType.ROOM);

        assertTrue(wallCollision.canEnter(p));
    }

    @Test
    void testCanEnterWall() {
        Position p = new Position(2, 2);
        when(gridMock.get(2, 2)).thenReturn(TileType.WALL);

        assertFalse(wallCollision.canEnter(p));
    }

    @Test
    void testCanEnemyEnterRoom() {
        Position p = new Position(1, 1);
        when(gridMock.get(1, 1)).thenReturn(TileType.ROOM);
        when(entityGridMock.get(1, 1)).thenReturn(TileType.NONE);

        assertTrue(wallCollision.canEnemyEnter(p));
    }

    @Test
    void testCanEnterEntity() {
        Position p = new Position(1, 1);
        when(gridMock.get(1, 1)).thenReturn(TileType.ROOM);
        when(entityGridMock.get(1, 1)).thenReturn(TileType.ENEMY);

        assertFalse(wallCollision.canEnter(p));

        when(entityGridMock.get(1, 1)).thenReturn(TileType.ITEM);

        // can pickup items
        assertTrue(wallCollision.canEnter(p));
    }

    @Test
    void testCanEnemyEnterEntity() {
        Position p = new Position(1, 1);
        when(gridMock.get(1, 1)).thenReturn(TileType.ROOM);
        when(entityGridMock.get(1, 1)).thenReturn(TileType.PLAYER);

        assertFalse(wallCollision.canEnemyEnter(p));

        when(entityGridMock.get(1, 1)).thenReturn(TileType.ITEM);

        // enemies can't pick up items
        assertFalse(wallCollision.canEnemyEnter(p));

        when(entityGridMock.get(1, 1)).thenReturn(TileType.ENEMY);

        assertFalse(wallCollision.canEnemyEnter(p));
    }

    @Test
    void testCanEnemyEnterNotRoom() {
        Position p = new Position(1, 1);
        when(gridMock.get(1, 1)).thenReturn(TileType.WALL);

        assertFalse(wallCollision.canEnemyEnter(p));

        when(gridMock.get(1, 1)).thenReturn(TileType.STAIRS);

        assertFalse(wallCollision.canEnemyEnter(p));
    }

    @Test
    void testClosestWallFindsWallX() {
        Position from = new Position(1, 0);

        when(gridMock.get(2, 0)).thenReturn(TileType.ROOM);
        when(gridMock.get(0, 1)).thenReturn(TileType.ROOM);
        when(gridMock.get(0, 0)).thenReturn(TileType.WALL);

        Optional<Position> wall = wallCollision.closestWall(from, 1, 0);

        assertTrue(wall.isPresent());
        assertEquals(new Position(0, 0), wall.get());
    }

    @Test
    void testClosestWallFindsWallY() {
        Position from = new Position(0, 2);

        when(gridMock.get(0, 1)).thenReturn(TileType.ROOM);
        when(gridMock.get(1, 2)).thenReturn(TileType.ROOM);
        when(gridMock.get(0, 0)).thenReturn(TileType.WALL);
        when(gridMock.get(0, 1)).thenReturn(TileType.ITEM);

        Optional<Position> wall = wallCollision.closestWall(from, 0, 1);

        assertTrue(wall.isPresent());
        assertEquals(new Position(0, 1), wall.get());
    }

    @Test
    void testClosestWallNoWallFound() {
        Position from = new Position(0, 0);

        // tutto ROOM
        when(gridMock.get(anyInt(), anyInt())).thenReturn(TileType.ROOM);

        Optional<Position> wall = wallCollision.closestWall(from, 1, 0);

        assertTrue(wall.isEmpty());
    }
}
