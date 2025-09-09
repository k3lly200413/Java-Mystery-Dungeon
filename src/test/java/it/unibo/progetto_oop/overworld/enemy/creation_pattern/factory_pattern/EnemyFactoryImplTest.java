package it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_pattern;

import it.unibo.progetto_oop.overworld.combat_collision.CombatCollision;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.GenericEnemy;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.BossEnemy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision.WallCollision;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.playground.data.Position;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnemyFactoryImplTest {
    /**
     * mocked grid notifier.
     */
    private GridNotifier gridNotifier;

    /**
     * mocked position.
     */
    private Position position;

    /**
     * Class under test.
     */
    private EnemyFactoryImpl factory;

    /**
     * health costant for enemy creation tests.
     */
    private static final int HEALTH = 10;

    /**
     * power costant for enemy creation tests.
     */
    private static final int POWER = 5;

    @BeforeEach
    void setUp() {
        WallCollision wallCollision = mock(WallCollision.class);
        CombatCollision combatCollision = mock(CombatCollision.class);
        gridNotifier = mock(GridNotifier.class);
        position = mock(Position.class);
        factory = new EnemyFactoryImpl(wallCollision, combatCollision);
    }

    @Test
    void testCreatePatrollerEnemy() {
        final Enemy enemy = factory.
            createPatrollerEnemy(HEALTH, POWER, position, true, gridNotifier);
        assertTrue(enemy instanceof GenericEnemy);
        assertEquals(enemy.getState().getDescription(), "Patroller State");
    }

    @Test
    void testCreateFollowerEnemy() {
        final Enemy enemy = factory.
            createFollowerEnemy(HEALTH, POWER, position, false, gridNotifier);
        assertTrue(enemy instanceof GenericEnemy);
        assertEquals(enemy.getState().getDescription(), "Follower State");
    }

    @Test
    void testCreateSleeperEnemy() {
        final Enemy enemy = factory.
            createSleeperEnemy(HEALTH, POWER, position, true, gridNotifier);
        assertTrue(enemy instanceof GenericEnemy);
        assertEquals(enemy.getState().getDescription(), "Sleeper State");
    }

    @Test
    void testCreateBossEnemy() {
        final Enemy enemy = factory.
        createBossEnemy(HEALTH, POWER, position, false, gridNotifier);
        assertTrue(enemy instanceof BossEnemy);
        assertEquals(enemy.getState().getDescription(), "Sleeper State");
    }
}
