package it.unibo.progetto_oop.combat.state_pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import it.unibo.progetto_oop.combat.combat_builder.RedrawContext;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatPresenter;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatViewApi;
import it.unibo.progetto_oop.combat.mvc_pattern.ReadOnlyCombatModel;
import it.unibo.progetto_oop.overworld.combat_collision.CombatCollision;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;

class GameOverStateTest {

    /** HP value. */
    private static final int HP_VALUE = 42;

    /** Timeout time for async operations. */
    private static final long TIMEOUT_TIME = 200;

    /** HP value for enemy. */
    private static final int ENEMY_HP_VALUE = 25;

    /** Message shown when the player wins. */
    private static final String WIN_MESSAGE =
    "You Win! Returning to Overworld...";

    /** Return value for tests. */
    private static final int RETURN_VALUE = 10;

    /** Sleep time to wait for async operations. */
    private static final int SLEEP_TIME = 1000;

    /** Sleep time to wait for async operations. */
    private static final int SLEEP_BOSS_TIME = 800;

    /** Combat collision instance. */
    private CombatCollision combatCollision;

    /** Grid notifier instance. */
    private GridNotifier gridNotifier;

    /** Enemy instance. */
    private Enemy enemy;

    /** User player instance. */
    private Player player;

    /** Combat controller instance. */
    private CombatPresenter controller;

    /** Combat model instance. */
    private ReadOnlyCombatModel model;

    /** Combat view instance. */
    private CombatViewApi view;

    /** Enemy position. */
    private final Position enemyPos = new Position(7, 4);

    /** Player position. */
    private final Position playerPos = new Position(3, 4);

    /** Attack position. */
    private final Position attackPos = new Position(4, 4);

    @BeforeEach
    void setUpGameOverState() {
        combatCollision = mock(CombatCollision.class);
        gridNotifier = mock(GridNotifier.class);
        enemy = mock(Enemy.class);
        player = mock(Player.class);

        controller = mock(CombatPresenter.class);
        model = mock(ReadOnlyCombatModel.class);
        view = mock(CombatViewApi.class);
        controller.setEncounteredEnemy(enemy);

        when(controller.getReadOnlyModel()).thenReturn(model);
        when(controller.getViewApi()).thenReturn(view);

        when(model.getPlayerPosition()).thenReturn(playerPos);
        when(model.getEnemyPosition()).thenReturn(enemyPos);
        when(model.getAttackPosition()).thenReturn(attackPos);
        when(model.isGameOver()).thenReturn(false);
        when(model.getWhoDied()).thenReturn(null);

        when(enemy.getCurrentPosition()).thenReturn(enemyPos);
        when(enemy.getCurrentHp()).thenReturn(HP_VALUE);
    }

    @Test
    void enterStateRedrawsImmediately() {
        when(model.getPlayerHealth()).thenReturn(RETURN_VALUE);
        when(model.getEnemyHealth()).thenReturn(RETURN_VALUE);

        final GameOverState state = new GameOverState(
            combatCollision, gridNotifier, enemy, player);
        state.enterState(controller);

        verify(view, timeout(TIMEOUT_TIME))
        .updateDisplay(any(RedrawContext.class));
    }

    @Test
    void whenPlayerIsDeadShowGameOverOnCollision() throws InterruptedException {
        when(model.getPlayerHealth()).thenReturn(0);
        when(model.getEnemyHealth()).thenReturn(RETURN_VALUE);

        final GameOverState state = new GameOverState(
            combatCollision, gridNotifier, enemy, player);
        state.enterState(controller);

        Thread.sleep(SLEEP_TIME);

        verify(combatCollision, times(1)).showGameOver();

        verify(combatCollision, never()).setInCombat(false);
        verify(combatCollision, never()).showOverworld();
        verify(gridNotifier, never()).notifyEnemyRemoved(any());
        verify(gridNotifier, never()).notifyListEnemyRemoved(any());
        verify(enemy, never()).setHp(anyInt());
        verify(view, never()).showInfo(WIN_MESSAGE);
    }

    @Test
    void whenEnemyIsDeadRewardsNotifiedAndOverworldShown()
    throws InterruptedException {
        when(model.getPlayerHealth()).thenReturn(RETURN_VALUE);
        when(model.getEnemyHealth()).thenReturn(0);

        when(model.getEnemyState()).thenReturn(new EnemyTurnState());

        final GameOverState state = new GameOverState(
            combatCollision, gridNotifier, enemy, player);
        state.enterState(controller);

        Thread.sleep(SLEEP_TIME);

        verify(gridNotifier, times(1)).notifyEnemyRemoved(enemyPos);
        verify(gridNotifier, times(1)).notifyListEnemyRemoved(enemyPos);

        verify(combatCollision, times(1)).setInCombat(false);
        verify(combatCollision, times(1)).showOverworld();

        verify(view, times(1)).showInfo(WIN_MESSAGE);

        verify(enemy, never()).setHp(anyInt());

        verify(combatCollision, never()).showGameOver();
    }

    @Test
    void whenNoOneIsDeadSyncEnemyHpAndOverworld() throws InterruptedException {
        when(model.getPlayerHealth()).thenReturn(RETURN_VALUE);
        when(model.getEnemyHealth()).thenReturn(ENEMY_HP_VALUE);

        final GameOverState state = new GameOverState(
            combatCollision, gridNotifier, enemy, player);
        state.enterState(controller);

        Thread.sleep(SLEEP_TIME);

        verify(combatCollision, times(1)).setInCombat(false);
        verify(combatCollision, times(1)).showOverworld();

        verify(enemy, times(1)).setHp(ENEMY_HP_VALUE);

        verify(gridNotifier, never()).notifyEnemyRemoved(any());
        verify(gridNotifier, never()).notifyListEnemyRemoved(any());
        verify(view, never()).showInfo(WIN_MESSAGE);

        verify(combatCollision, never()).showGameOver();
    }

    @Test
void whenBossIsDeadShowWinScreenNotOverworld() {
    when(model.getPlayerHealth()).thenReturn(RETURN_VALUE);
    when(model.getEnemyHealth()).thenReturn(0);
    when(enemy.isBoss()).thenReturn(true);

    final GameOverState state = new GameOverState(
        combatCollision, gridNotifier, enemy, player);
    state.enterState(controller);

    verify(gridNotifier, timeout(SLEEP_BOSS_TIME)).notifyEnemyRemoved(enemyPos);
    verify(gridNotifier, timeout(SLEEP_BOSS_TIME))
    .notifyListEnemyRemoved(enemyPos);
    verify(combatCollision, timeout(SLEEP_BOSS_TIME)).setInCombat(false);

    verify(combatCollision, timeout(SLEEP_BOSS_TIME)).showWin();

    verify(combatCollision, never()).showOverworld();
    verify(view, never()).showInfo(WIN_MESSAGE);

    verify(combatCollision, never()).showGameOver();
    verify(enemy, never()).setHp(anyInt());
}
}
