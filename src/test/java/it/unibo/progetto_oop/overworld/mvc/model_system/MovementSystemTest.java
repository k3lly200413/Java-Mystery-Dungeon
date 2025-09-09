package it.unibo.progetto_oop.overworld.mvc.model_system;

import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision.WallCollision;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.mvc.OverworldModel;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.StructureData;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;


class MovementSystemTest {
    /**
     * mock player.
     */
    private Player player;

    /**
     * mock model.
     */
    private OverworldModel model;

    /**
     * mock pickup system.
     */
    private PickupSystem pickupSystem;

    /**
     * mock enemy system.
     */
    private EnemySystem enemySystem;

    /**
     * system under test.
     */
    private MovementSystem movementSystem;

    /**
     * mock position.
     */
    private Position position;

    /**
     * temporary position.
     */
    private Position tempPosition;

    /**
     * mock wall collision.
     */
    private WallCollision wallCollision;

    /**
     * mock structure data.
     */
    private StructureData structureData;

    /**
     * mock grid notifier.
     */
    private GridNotifier gridNotifier;

    @BeforeEach
    void setUp() {
        player = mock(Player.class);
        model = mock(OverworldModel.class);
        pickupSystem = mock(PickupSystem.class);
        enemySystem = mock(EnemySystem.class);
        movementSystem = new MovementSystem(player, model);
        tempPosition = new Position(2, 1);
        position = new Position(1, 1);

        wallCollision = mock(WallCollision.class);
        structureData = mock(StructureData.class);
        gridNotifier = mock(GridNotifier.class);
    }

    @Test
    void testMoveWallHit() {
        when(player.getPosition()).thenReturn(position);
        when(model.getWallCollision()).thenReturn(wallCollision);

        when(model.getWallCollision()
            .canEnter(any(Position.class))).thenReturn(false);
        movementSystem.move(1, 0, pickupSystem, enemySystem);
        final var result =
            model.getWallCollision().canEnter(any(Position.class));
        assertFalse(result);
    }

    @Test
    void testMoveStairs() {
        when(player.getPosition()).thenReturn(position);
        when(model.getWallCollision()).thenReturn(wallCollision);
        when(model.getWallCollision()
            .canEnter(any(Position.class))).thenReturn(true);
        when(model.getBaseGridView()).thenReturn(structureData);

        when(model.getBaseGridView()
            .get(anyInt(), anyInt())).thenReturn(TileType.STAIRS);
        when(model.getGridNotifier()).thenReturn(gridNotifier);

        movementSystem.move(1, 0, pickupSystem, enemySystem);
        verify(model).nextFloor();
    }

    @Test
    void testMoveEnemyEncounter() {
        when(player.getPosition()).thenReturn(position);
        when(model.getWallCollision()).thenReturn(wallCollision);
        when(model.getWallCollision()
            .canEnter(any(Position.class))).thenReturn(true);
        when(model.getBaseGridView()).thenReturn(structureData);
        when(model.getBaseGridView()
            .get(anyInt(), anyInt())).thenReturn(TileType.ROOM);
        when(model.getGridNotifier()).thenReturn(gridNotifier);

        final Enemy enemy = mock(Enemy.class);
        when(enemySystem.checkEnemyHit(
            any(Position.class))).thenReturn(Optional.of(enemy));

        movementSystem.move(1, 0, pickupSystem, enemySystem);
        verify(enemySystem).setEncounteredEnemy(enemy);
        assertTrue(movementSystem.isCombatTransitionPending());
    }

    @Test
    void testMoveNoEnemy() {
        when(player.getPosition()).thenReturn(position);
        when(model.getWallCollision()).thenReturn(wallCollision);
        when(model.getWallCollision()
            .canEnter(any(Position.class))).thenReturn(true);
        when(model.getBaseGridView()).thenReturn(structureData);
        when(model.getBaseGridView()
            .get(anyInt(), anyInt())).thenReturn(TileType.ROOM);
        when(model.getGridNotifier()).thenReturn(gridNotifier);
        when(enemySystem.checkEnemyHit(
            any(Position.class))).thenReturn(Optional.empty());
        movementSystem.move(1, 0, pickupSystem, enemySystem);

        verify(enemySystem).triggerEnemyTurns();
        verify(player).setPosition(tempPosition);
    }
}
