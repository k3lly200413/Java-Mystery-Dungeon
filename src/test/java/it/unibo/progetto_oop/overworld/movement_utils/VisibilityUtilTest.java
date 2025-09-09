package it.unibo.progetto_oop.overworld.movement_utils;

import it.unibo.progetto_oop.overworld.enemy.movement_strategy.VisibilityUtil;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision.WallCollision;
import it.unibo.progetto_oop.overworld.playground.data.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class VisibilityUtilTest {
    /**
     * mock wall collision.
     */
    private WallCollision wallCollision;

    /**
     * system under test.
     */
    private VisibilityUtil visibilityUtil;

    /**
     * constant coordinate for tests.
     */
    private static final int COORDINATE = 5;

    @BeforeEach
    void setUp() {
        wallCollision = mock(WallCollision.class);
        visibilityUtil = new VisibilityUtil(wallCollision);
    }

    @Test
    void testInLosPlayerIsNeighbourAndVisible() {
        Position enemy = mock(Position.class);
        Position player = mock(Position.class);
        int neighbourDistance = 1;

        var visibility = new VisibilityUtil(wallCollision);
        // Mock WallCollision.canEnemyEnter to return true
        when(wallCollision.canEnemyEnter(any(Position.class))).thenReturn(true);
        // Should return true
        assertTrue(visibility.inLos(enemy, player, neighbourDistance));
    }

    @Test
    void testInLosPlayerNotNeighbour() {
        Position enemy = new Position(0, 0);
        Position player = new Position(COORDINATE, COORDINATE);
        int neighbourDistance = 1;

        var visibility = new VisibilityUtil(wallCollision);

        // Mock WallCollision.canEnemyEnter to return false
        when(wallCollision.
            canEnemyEnter(any(Position.class))).thenReturn(false);

        assertFalse(visibility.inLos(enemy, player, neighbourDistance));
    }

    @Test
    void testFirstMove() {
        Position enemy = new Position(1, 1);
        Position player = new Position(2, 1);
        Position result = visibilityUtil.firstMove(enemy, player);
        assertEquals(2, result.x());
        assertEquals(1, result.y());
    }
}
