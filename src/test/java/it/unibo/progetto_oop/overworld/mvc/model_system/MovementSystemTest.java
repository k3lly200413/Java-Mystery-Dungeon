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
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovementSystemTest {
    private Player player;
    private OverworldModel model;
    private PickupSystem pickupSystem;
    private EnemySystem enemySystem;
    private MovementSystem movementSystem;
    private Position position;
    private Position tempPosition;

    private WallCollision wallCollision;
    private StructureData structureData;
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

        when(model.getWallCollision().canEnter(any(Position.class))).thenReturn(false);
        movementSystem.move(1, 0, pickupSystem, enemySystem);
        var result = model.getWallCollision().canEnter(any(Position.class));
        assertEquals(false, result);
    }

    @Test
    void testMoveStairs() {
        when(player.getPosition()).thenReturn(position);
        when(model.getWallCollision()).thenReturn(wallCollision);
        when(model.getWallCollision().canEnter(any(Position.class))).thenReturn(true);
        when(model.getBaseGridView()).thenReturn(structureData);

        when(model.getBaseGridView().get(anyInt(), anyInt())).thenReturn(TileType.STAIRS);
        when(model.getGridNotifier()).thenReturn(gridNotifier);

        movementSystem.move(1, 0, pickupSystem, enemySystem);
        verify(model).nextFloor();
    }

    @Test
    void testMoveEnemyEncounter() {
        when(player.getPosition()).thenReturn(position);
        when(model.getWallCollision()).thenReturn(wallCollision);
        when(model.getWallCollision().canEnter(any(Position.class))).thenReturn(true);
        when(model.getBaseGridView()).thenReturn(structureData);
        when(model.getBaseGridView().get(anyInt(), anyInt())).thenReturn(TileType.ROOM);
        when(model.getGridNotifier()).thenReturn(gridNotifier);

        Enemy enemy = mock(Enemy.class);
        when(enemySystem.checkEnemyHit(any(Position.class))).thenReturn(Optional.of(enemy));

        movementSystem.move(1, 0, pickupSystem, enemySystem);
        verify(enemySystem).setEncounteredEnemy(enemy);
        assertTrue(movementSystem.isCombatTransitionPending());
    } 

    @Test
    void testMoveNoEnemy() {
        when(player.getPosition()).thenReturn(position);
        when(model.getWallCollision()).thenReturn(wallCollision);
        when(model.getWallCollision().canEnter(any(Position.class))).thenReturn(true);
        when(model.getBaseGridView()).thenReturn(structureData);
        when(model.getBaseGridView().get(anyInt(), anyInt())).thenReturn(TileType.ROOM);
        when(model.getGridNotifier()).thenReturn(gridNotifier);
        when(enemySystem.checkEnemyHit(any(Position.class))).thenReturn(Optional.empty());
        movementSystem.move(1, 0, pickupSystem, enemySystem);
        
        verify(enemySystem).triggerEnemyTurns();
        verify(player).setPosition(tempPosition);
    }
}
