package it.unibo.progetto_oop.MovementUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementUtil;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision.WallCollision;
import it.unibo.progetto_oop.overworld.playGround.data.Position;

class MovementUtilTest {

    private WallCollision wallCollisionMock;
    private MovementUtil movementUtil;

    @BeforeEach
    void setup() {
        wallCollisionMock = mock(WallCollision.class);
        movementUtil = new MovementUtil(wallCollisionMock);
    }

    @Test
    void testVerticalMovement_EnemyAboveWall() {
        Position enemy = new Position(5, 5);
        Position wall = new Position(5, 3); // muro sopra (y più piccolo)

        when(wallCollisionMock.closestWall(enemy, 0, 1))
                .thenReturn(Optional.of(wall));

        MovementUtil.MoveDirection dir = 
            movementUtil.getInitialGeneralMoveDirection(enemy, true);

        assertEquals(MovementUtil.MoveDirection.DOWN, dir);
    }

    @Test
    void testVerticalMovement_EnemyBelowWall() {
        Position enemy = new Position(5, 5);
        Position wall = new Position(5, 8); // muro sotto (y più grande)

        when(wallCollisionMock.closestWall(enemy, 0, 1))
                .thenReturn(Optional.of(wall));

        MovementUtil.MoveDirection dir = 
            movementUtil.getInitialGeneralMoveDirection(enemy, true);

        assertEquals(MovementUtil.MoveDirection.UP, dir);
    }

    @Test
    void testHorizontalMovement_EnemyLeftOfWall() {
        Position enemy = new Position(2, 4);
        Position wall = new Position(5, 4); // muro a destra (x più grande)

        when(wallCollisionMock.closestWall(enemy, 1, 0))
                .thenReturn(Optional.of(wall));

        MovementUtil.MoveDirection dir = 
            movementUtil.getInitialGeneralMoveDirection(enemy, false);

        assertEquals(MovementUtil.MoveDirection.LEFT, dir);
    }

    @Test
    void testHorizontalMovement_EnemyRightOfWall() {
        Position enemy = new Position(7, 4);
        Position wall = new Position(3, 4); // muro a sinistra (x più piccolo)

        when(wallCollisionMock.closestWall(enemy, 1, 0))
                .thenReturn(Optional.of(wall));

        MovementUtil.MoveDirection dir = 
            movementUtil.getInitialGeneralMoveDirection(enemy, false);

        assertEquals(MovementUtil.MoveDirection.RIGHT, dir);
    }

    @Test
    void testEnemyAndWallOnSameCoordinate() {
        Position enemy = new Position(5, 5);
        Position wall = new Position(5, 5); // stessa posizione

        when(wallCollisionMock.closestWall(enemy, 0, 1))
                .thenReturn(Optional.of(wall));

        MovementUtil.MoveDirection dir = 
            movementUtil.getInitialGeneralMoveDirection(enemy, true);

        assertEquals(MovementUtil.MoveDirection.NONE, dir);
    }

    @Test
    void testNoWallFound() {
        Position enemy = new Position(5, 5);

        when(wallCollisionMock.closestWall(enemy, 0, 1))
                .thenReturn(Optional.empty());

        MovementUtil.MoveDirection dir = 
            movementUtil.getInitialGeneralMoveDirection(enemy, true);

        assertEquals(MovementUtil.MoveDirection.NONE, dir);
    }
}
