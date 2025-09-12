package it.unibo.progetto_oop.overworld.collisions;

import it.unibo.progetto_oop.overworld.combat_collision.CombatCollisionImpl;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.view_manager_observer.ViewManagerObserver;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CombatCollisionImplTest {

    /**
     * coordinate constant.
     */
    private static final int COORDINATE = 5;

    private static final int DELAY = 80;

    /**
     * combat collision implementation under test.
     */
    private CombatCollisionImpl collision;

    /**
     * mocked position.
     */
    private Position playerPos;

    /**
     * mocked position.
     */
    private Position enemyPos;

    /**
     * mocked entity.
     */
    private Enemy enemy;

    /**
     * mocked entity.
     */
    private Player player;

    /**
     * mocked observer.
     */
    private ViewManagerObserver observer;

    @BeforeEach
    void setUpCombatCollision() {
        collision = new CombatCollisionImpl();
        playerPos = mock(Position.class);
        enemyPos = mock(Position.class);
        enemy = mock(Enemy.class);
        player = mock(Player.class);
        observer = mock(ViewManagerObserver.class);
        collision.setViewManagerListener(observer);
    }

    @Test
    void testCheckCombatCollisionTrue() {
        when(playerPos.x()).thenReturn(1);
        when(playerPos.y()).thenReturn(1);
        when(enemyPos.x()).thenReturn(1);
        when(enemyPos.y()).thenReturn(2);
        assertTrue(collision.checkCombatCollision(playerPos, enemyPos));
    }

    @Test
    void testCheckCombatCollisionFalse() {
        when(playerPos.x()).thenReturn(1);
        when(playerPos.y()).thenReturn(1);
        when(enemyPos.x()).thenReturn(COORDINATE);
        when(enemyPos.y()).thenReturn(COORDINATE);
        assertFalse(collision.checkCombatCollision(playerPos, enemyPos));
    }

    @Test
    void testShowCombatCallsObserver() {
        collision.setInCombat(false);
        collision.showCombat(enemy, player);
        // waiting for invokeLater to execute
        new javax.swing.Timer(DELAY, e -> {
            verify(observer).onPlayerEnemyContact(enemy);
        }).start();
    }

    @Test
    void testShowCombatDoesNotCallObserverIfAlreadyInCombat() {
        collision.setInCombat(true);
        collision.showCombat(enemy, player);
        // waiting for invokeLater to execute
        new javax.swing.Timer(DELAY, e -> {
            verify(observer, never()).onPlayerEnemyContact(enemy);
        }).start();
    }

    @Test
    void testShowOverworldCallsObserver() {
        collision.showOverworld();
        verify(observer).onEnemyDefeat();
    }
}
