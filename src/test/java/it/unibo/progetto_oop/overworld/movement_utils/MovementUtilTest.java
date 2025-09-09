package it.unibo.progetto_oop.overworld.movement_utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementUtil;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision.WallCollision;
import it.unibo.progetto_oop.overworld.playground.data.Position;

class MovementUtilTest {

    /**
     * mock wall collision.
     */
    private WallCollision wallCollisionMock;

    /**
     * system under test.
     */
    private MovementUtil movementUtil;

    @BeforeEach
    void setup() {
        wallCollisionMock = mock(WallCollision.class);
        movementUtil = new MovementUtil(wallCollisionMock);
    }

    @Test
    void testVerticalMovementEnemyAboveWall() {
        final Position enemy = new Position(0, 1);
        // muro sopra (y più piccolo)
        final Position wall = new Position(0, 0);

        when(wallCollisionMock.closestWall(enemy, 0, 1))
                .thenReturn(Optional.of(wall));

        MovementUtil.MoveDirection dir =
            movementUtil.getInitialGeneralMoveDirection(enemy, true);

        assertEquals(MovementUtil.MoveDirection.DOWN, dir);
    }

    @Test
    void testVerticalMovementEnemyBelowWall() {
        final Position enemy = new Position(0, 0);
        // muro sotto (y più grande)
        final Position wall = new Position(0, 2);

        when(wallCollisionMock.closestWall(enemy, 0, 1))
                .thenReturn(Optional.of(wall));

        MovementUtil.MoveDirection dir =
            movementUtil.getInitialGeneralMoveDirection(enemy, true);

        assertEquals(MovementUtil.MoveDirection.UP, dir);
    }

    @Test
    void testHorizontalMovementEnemyLeftOfWall() {
        final Position enemy = new Position(0, 0);
        // muro a destra (x più grande)
        final Position wall = new Position(2, 0);

        when(wallCollisionMock.closestWall(enemy, 1, 0))
                .thenReturn(Optional.of(wall));

        MovementUtil.MoveDirection dir =
            movementUtil.getInitialGeneralMoveDirection(enemy, false);

        assertEquals(MovementUtil.MoveDirection.LEFT, dir);
    }

    @Test
    void testHorizontalMovementEnemyRightOfWall() {
        final Position enemy = new Position(2, 0);
        final Position wall = new Position(0, 0);
        // muro a sinistra (x più piccolo)

        when(wallCollisionMock.closestWall(enemy, 1, 0))
                .thenReturn(Optional.of(wall));

        MovementUtil.MoveDirection dir =
            movementUtil.getInitialGeneralMoveDirection(enemy, false);

        assertEquals(MovementUtil.MoveDirection.RIGHT, dir);
    }

    @Test
    void testEnemyAndWallOnSameCoordinate() {
        final Position enemy = new Position(1, 1);
        final Position wall = new Position(1, 1); // stessa posizione

        when(wallCollisionMock.closestWall(enemy, 0, 1))
                .thenReturn(Optional.of(wall));

        MovementUtil.MoveDirection dir =
            movementUtil.getInitialGeneralMoveDirection(enemy, true);

        assertEquals(MovementUtil.MoveDirection.NONE, dir);
    }

    @Test
    void testNoWallFound() {
        final Position enemy = new Position(1, 1);

        when(wallCollisionMock.closestWall(enemy, 0, 1))
                .thenReturn(Optional.empty());

        MovementUtil.MoveDirection dir =
            movementUtil.getInitialGeneralMoveDirection(enemy, true);

        assertEquals(MovementUtil.MoveDirection.NONE, dir);
    }
}
