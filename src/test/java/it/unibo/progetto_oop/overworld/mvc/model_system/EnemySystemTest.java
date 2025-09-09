package it.unibo.progetto_oop.overworld.mvc.model_system;

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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnemySystemTest {
    private Enemy enemy1;
    private Enemy enemy2;
    private Player player;
    private OverworldModel model;
    private Position position;
    private Position position1;
    private EnemySystem enemySystem;

    @BeforeEach
    void setUp() {
        enemy1 = mock(Enemy.class);
        enemy2 = mock(Enemy.class);
        player = mock(Player.class);
        model = mock(OverworldModel.class);
        position = mock(Position.class);
        enemySystem = new EnemySystem(new ArrayList<>(Arrays.asList(enemy1, enemy2)), player, model);
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
        when(model.getCombatCollision()).thenReturn(mock(it.unibo.progetto_oop.overworld.combat_collision.CombatCollision.class));
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
        when(model.getCombatCollision()).thenReturn(mock(it.unibo.progetto_oop.overworld.combat_collision.CombatCollision.class));
        when(model.getCombatCollision().checkCombatCollision(position, position)).thenReturn(true);
        Optional<Enemy> result = enemySystem.checkEnemyHit(position);
        assertTrue(result.isPresent());
        assertEquals(enemy1, result.get());
    }

    @Test
    void testCheckEnemyHitNotFound() {
        when(enemy1.getCurrentPosition()).thenReturn(position);
        when(model.getCombatCollision()).thenReturn(mock(it.unibo.progetto_oop.overworld.combat_collision.CombatCollision.class));
        when(model.getCombatCollision().checkCombatCollision(position, position)).thenReturn(false);
        Optional<Enemy> result = enemySystem.checkEnemyHit(position);
        assertFalse(result.isPresent());
    }

    @Test
    void testRemoveEnemyAt() {
        position1 = new Position(1, 1);
        enemy1 = new GenericEnemy(
            10,
            10,
            10,
            position1,
            mock(GridNotifier.class));
        enemySystem = new EnemySystem(new ArrayList<>(Arrays.asList(enemy1)), player, model);

        boolean removed = enemySystem.removeEnemyAt(position1);
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
