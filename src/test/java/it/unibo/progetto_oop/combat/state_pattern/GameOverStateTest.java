package it.unibo.progetto_oop.combat.state_pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import it.unibo.progetto_oop.combat.combat_builder.RedrawContext;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatModel;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatView;
import it.unibo.progetto_oop.overworld.combat_collision.CombatCollision;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * Tests for {@link GameOverState}.
 */
class GameOverStateTest {

    /** Timeout used by verify(..., timeout()). */
    private static final int VERIFY_TIMEOUT_MS = 200;

    /** Sleep used to wait timer (state uses ~700ms delay). */
    private static final int SLEEP_AFTER_TIMER_MS = 1000;

    /** Enemy start X coordinate used in stubs. */
    private static final int ENEMY_X = 7;

    /** Enemy/Player Y coordinate used in stubs. */
    private static final int ROW_Y = 4;

    /** Player start X coordinate used in stubs. */
    private static final int PLAYER_X = 3;

    /** Attack position X coordinate used in stubs. */
    private static final int ATTACK_X = 4;

    /** Test enemy HP used in stubs. */
    private static final int ENEMY_HP_SAMPLE = 42;

    /** Test player positive HP. */
    private static final int PLAYER_HP_POSITIVE = 10;

    /** Test enemy positive HP. */
    private static final int ENEMY_HP_POSITIVE = 10;

    /** Test enemy remaining HP used in sync test. */
    private static final int ENEMY_HP_SYNC = 25;

    /** Enemy position used in tests. */
    private static final Position ENEMY_POS = new Position(ENEMY_X, ROW_Y);

    /** Player position used in tests. */
    private static final Position PLAYER_POS = new Position(PLAYER_X, ROW_Y);

    /** Attack position used in tests. */
    private static final Position ATTACK_POS = new Position(ATTACK_X, ROW_Y);

    /** The combat collision manager. */
    private CombatCollision combatCollision;

    /** The grid notifier for updating the game grid. */
    private GridNotifier gridNotifier;

    /** The enemy involved in the combat. */
    private Enemy enemy;

    /** The player involved in the combat. */
    private Player player;

    /** The combat controller managing the combat flow. */
    private CombatController controller;

    /** The combat model managing the combat state. */
    private CombatModel model;

    /** The combat view for rendering the combat UI. */
    private CombatView view;

    @BeforeEach
    void setUp() {
        this.combatCollision = mock(CombatCollision.class);
        this.gridNotifier = mock(GridNotifier.class);
        this.enemy = mock(Enemy.class);
        this.player = mock(Player.class);

        this.controller = mock(CombatController.class);
        this.model = mock(CombatModel.class);
        this.view = mock(CombatView.class);

        when(this.controller.getModel()).thenReturn(this.model);
        when(this.controller.getView()).thenReturn(this.view);

        // minimal stubs for redraw
        when(this.model.getPlayerPosition()).thenReturn(PLAYER_POS);
        when(this.model.getEnemyPosition()).thenReturn(ENEMY_POS);
        when(this.model.getAttackPosition()).thenReturn(ATTACK_POS);
        when(this.model.isGameOver()).thenReturn(false);
        when(this.model.getWhoDied()).thenReturn(null);

        when(this.enemy.getCurrentPosition()).thenReturn(ENEMY_POS);
        when(this.enemy.getCurrentHp()).thenReturn(ENEMY_HP_SAMPLE);
    }

    @Test
    void enterStateRedrawsImmediately() {
        when(this.model.getPlayerHealth()).thenReturn(PLAYER_HP_POSITIVE);
        when(this.model.getEnemyHealth()).thenReturn(ENEMY_HP_POSITIVE);

        final GameOverState state = new GameOverState(
            this.combatCollision, this.gridNotifier, this.enemy, this.player
        );
        state.enterState(this.controller);

        verify(this.view, timeout(VERIFY_TIMEOUT_MS))
        .redrawGrid(any(RedrawContext.class));
    }

    @Test
    void whenPlayerIsDeadShowGameOverIsCalled() throws InterruptedException {
        when(this.model.getPlayerHealth()).thenReturn(0);
        when(this.model.getEnemyHealth()).thenReturn(ENEMY_HP_POSITIVE);

        final GameOverState state = new GameOverState(
            this.combatCollision, this.gridNotifier, this.enemy, this.player
        );
        state.enterState(this.controller);

        Thread.sleep(SLEEP_AFTER_TIMER_MS);

        verify(this.view).showGameOver(any(Runnable.class));
        verify(this.combatCollision, never()).setInCombat(anyBoolean());
        verify(this.gridNotifier, never()).notifyEnemyRemoved(any());
    }

    @Test
    void whenEnemyIsDeadRewardsAndOverworld() throws InterruptedException {
        when(this.model.getPlayerHealth()).thenReturn(PLAYER_HP_POSITIVE);
        when(this.model.getEnemyHealth()).thenReturn(0);

        final GameOverState state = new GameOverState(
            this.combatCollision, this.gridNotifier, this.enemy, this.player
        );
        state.enterState(this.controller);

        Thread.sleep(SLEEP_AFTER_TIMER_MS);

        verify(this.gridNotifier).notifyEnemyRemoved(ENEMY_POS);
        verify(this.gridNotifier).notifyListEnemyRemoved(ENEMY_POS);
        verify(this.combatCollision).setInCombat(false);
        verify(this.combatCollision).showOverworld();
        verify(this.view).showInfo("You Win! Returning to Overworld...");
        verify(this.enemy, never()).setHp(anyInt());
    }

    @Test
    void whenNoOneIsDeadEnemyHealthSynced() throws InterruptedException {
        when(this.model.getPlayerHealth()).thenReturn(PLAYER_HP_POSITIVE);
        when(this.model.getEnemyHealth()).thenReturn(ENEMY_HP_SYNC);

        final GameOverState state = new GameOverState(
            this.combatCollision, this.gridNotifier, this.enemy, this.player
        );
        state.enterState(this.controller);

        Thread.sleep(SLEEP_AFTER_TIMER_MS);

        verify(this.combatCollision).setInCombat(false);
        verify(this.combatCollision).showOverworld();
        verify(this.enemy).setHp(ENEMY_HP_SYNC);
        verify(this.view, never()).showGameOver(any());
        verify(this.view, never()).showInfo("You Win! Returning to Overworld!");
    }
}
