package it.unibo.progetto_oop.overworld.mvc.model_system;

import it.unibo.progetto_oop.overworld.combat_collision.CombatCollision;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.GenericEnemy;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.mvc.OverworldModel;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class EnemySystemTest {
    /**
     * mock enemy 1.
     */
    private Enemy enemy1;

    /**
     * mock enemy 2.
     */
    private Enemy enemy2;

    /**
     * mock player.
     */
    private Player player;

    /**
     * mock model.
     */
    private OverworldModel model;

    /**
     * mock position.
     */
    private Position position;

    /**
     * actual position.
     */
    private Position position1;

    /**
     * system under test.
     */
    private EnemySystem enemySystem;

    /**
     * mock combat collision.
     */
    private CombatCollision combatCollision;

    /**
     * enemy health for tests.
     */
    private static final int ENEMY_HEALTH = 10;

    @BeforeEach
    void setUp() {
        enemy1 = mock(Enemy.class);
        enemy2 = mock(Enemy.class);
        player = mock(Player.class);
        model = mock(OverworldModel.class);
        position = mock(Position.class);
        enemySystem = new EnemySystem(
            new ArrayList<>(Arrays.asList(enemy1, enemy2)), player, model);
        combatCollision = mock(CombatCollision.class);
    }

    @Test
    void testGetEnemies() {
        List<Enemy> enemies = enemySystem.getEnemies();
        assertEquals(2, enemies.size());
        assertTrue(enemies.contains(enemy1));
        assertTrue(enemies.contains(enemy2));
    }

    @Test
    void testSetEncounteredEnemyWithCombatTransition() {
        when(model.isCombatTransitionPending()).thenReturn(true);
        when(model.getCombatCollision())
            .thenReturn(combatCollision);
        enemySystem.setEncounteredEnemy(enemy1);
        assertEquals(enemy1, enemySystem.getEncounteredEnemy());
    }

    @Test
    void testSetEnemies() {
        Enemy enemy3 = mock(Enemy.class);
        enemySystem.setEnemies(Arrays.asList(enemy3));
        assertEquals(1, enemySystem.getEnemies().size());
        assertTrue(enemySystem.getEnemies().contains(enemy3));
    }

    @Test
    void testCheckEnemyHitFound() {
        when(enemy1.getCurrentPosition()).thenReturn(position);
        when(model.getCombatCollision()).thenReturn(combatCollision);
        when(model.getCombatCollision()
                .checkCombatCollision(position, position)).thenReturn(true);
        Optional<Enemy> result = enemySystem.checkEnemyHit(position);
        assertTrue(result.isPresent());
        assertEquals(enemy1, result.get());
    }

    @Test
    void testCheckEnemyHitNotFound() {
        when(enemy1.getCurrentPosition()).thenReturn(position);
        when(model.getCombatCollision()).thenReturn(combatCollision);
        when(model.getCombatCollision()
            .checkCombatCollision(position, position)).thenReturn(false);
        Optional<Enemy> result = enemySystem.checkEnemyHit(position);
        assertFalse(result.isPresent());
    }

    @Test
    void testRemoveEnemyAt() {
        final Position position3 = new Position(1, 1);
        enemy1 = new GenericEnemy(
            ENEMY_HEALTH,
            ENEMY_HEALTH,
            ENEMY_HEALTH,
            position3,
            mock(GridNotifier.class));
        enemySystem =
            new EnemySystem(
                new ArrayList<>(Arrays.asList(enemy1)), player, model);

        boolean removed = enemySystem.removeEnemyAt(position3);
        assertTrue(removed);
        assertEquals(0, enemySystem.getEnemies().size());
    }

    @Test
    void testTriggerEnemyTurns() {
        enemySystem.triggerEnemyTurns();
        verify(enemy1).takeTurn(player);
        verify(enemy2).takeTurn(player);
    }
}
