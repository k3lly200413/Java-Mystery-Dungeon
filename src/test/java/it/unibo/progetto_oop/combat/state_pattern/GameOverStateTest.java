package it.unibo.progetto_oop.combat.state_pattern;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.anyInt;

import it.unibo.progetto_oop.combat.combat_builder.RedrawContext;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatModel;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatView;
import it.unibo.progetto_oop.overworld.combat_collision.CombatCollision;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.player.Player;

class GameOverStateTest {

    /** Combat collision instance. */
    private CombatCollision combatCollision;

    /** Grid notifier instance. */
    private GridNotifier gridNotifier;

    /** Enemy instance. */
    private Enemy enemy;

    /** User player instance. */
    private Player player;

    /** Combat controller instance. */
    private CombatController controller;

    /** Combat model instance. */
    private CombatModel model;

    /** Combat view instance. */
    private CombatView view;

    /** Enemy position. */
    private final Position enemyPos = new Position(7, 4);

    /** Player position. */
    private final Position playerPos = new Position(3, 4);

    /** Attack position. */
    private final Position attackPos = new Position(4, 4);

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

    @BeforeEach
    void setUp() {
        combatCollision = mock(CombatCollision.class);
        gridNotifier = mock(GridNotifier.class);
        enemy = mock(Enemy.class);
        player = mock(Player.class);

        controller = mock(CombatController.class);
        model = mock(CombatModel.class);
        view = mock(CombatView.class);

        when(controller.getModel()).thenReturn(model);
        when(controller.getView()).thenReturn(view);

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
        .redrawGrid(any(RedrawContext.class));
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

        // Altri effetti non dovrebbero accadere
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
}
