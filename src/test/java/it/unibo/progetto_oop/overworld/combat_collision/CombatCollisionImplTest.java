package it.unibo.progetto_oop.overworld.combat_collision;

import it.unibo.progetto_oop.overworld.ViewManagerObserver;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CombatCollisionImplTest {
    private CombatCollisionImpl collision;
    private Position playerPos;
    private Position enemyPos;
    private Enemy enemy;
    private Player player;
    private ViewManagerObserver observer;

    @BeforeEach
    void setUp() {
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
        when(enemyPos.x()).thenReturn(5);
        when(enemyPos.y()).thenReturn(5);
        assertFalse(collision.checkCombatCollision(playerPos, enemyPos));
    }

    @Test
    void testShowCombatCallsObserver() {
        collision.setInCombat(false);
        collision.showCombat(enemy, player);
        verify(observer).onPlayerEnemyContact(enemy);
    }

    @Test
    void testShowCombatDoesNotCallObserverIfAlreadyInCombat() {
        collision.setInCombat(true);
        collision.showCombat(enemy, player);
        verify(observer, never()).onPlayerEnemyContact(enemy);
    }

    @Test
    void testShowOverworldCallsObserver() {
        collision.showOverworld();
        verify(observer).onEnemyDefeat();
    }
}
