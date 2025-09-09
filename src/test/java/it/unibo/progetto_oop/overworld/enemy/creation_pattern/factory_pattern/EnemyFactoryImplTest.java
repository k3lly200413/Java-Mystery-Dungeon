package it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_pattern;

import it.unibo.progetto_oop.overworld.combat_collision.CombatCollision;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.GenericEnemy;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.BossEnemy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision.WallCollision;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EnemyFactoryImplTest {
    private WallCollision wallCollision;
    private CombatCollision combatCollision;
    private GridNotifier gridNotifier;
    private Position position;
    private EnemyFactoryImpl factory;

    @BeforeEach
    void setUp() {
        wallCollision = mock(WallCollision.class);
        combatCollision = mock(CombatCollision.class);
        gridNotifier = mock(GridNotifier.class);
        position = mock(Position.class);
        factory = new EnemyFactoryImpl(wallCollision, combatCollision);
    }

    @Test
    void testCreatePatrollerEnemy() {
        Enemy enemy = factory.createPatrollerEnemy(10, 5, position, true, gridNotifier);
        assertTrue(enemy instanceof GenericEnemy);
        assertEquals(enemy.getState().getDescription(), "Patroller State");
    }

    @Test
    void testCreateFollowerEnemy() {
        Enemy enemy = factory.createFollowerEnemy(10, 5, position, false, gridNotifier);
        assertTrue(enemy instanceof GenericEnemy);
        assertEquals(enemy.getState().getDescription(), "Follower State");
    }

    @Test
    void testCreateSleeperEnemy() {
        Enemy enemy = factory.createSleeperEnemy(10, 5, position, true, gridNotifier);
        assertTrue(enemy instanceof GenericEnemy);
        assertEquals(enemy.getState().getDescription(), "Sleeper State");
    }

    @Test
    void testCreateBossEnemy() {
        Enemy enemy = factory.createBossEnemy(100, 20, position, false, gridNotifier);
        assertTrue(enemy instanceof BossEnemy);
        assertEquals(enemy.getState().getDescription(), "Sleeper State");
    }
}
