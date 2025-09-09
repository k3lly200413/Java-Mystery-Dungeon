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

class GameOverStateTest {

    private CombatCollision combatCollision;
    private GridNotifier gridNotifier;
    private Enemy enemy;
    private Player player;
    private CombatController controller;
    private CombatModel model;
    private CombatView view;

    private final Position ENEMY_POS = new Position(7, 4);
    private final Position PLAYER_POS = new Position(3, 4);
    private final Position ATK_POS    = new Position(4, 4);

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

        // Stub minimi per redraw
        when(model.getPlayerPosition()).thenReturn(PLAYER_POS);
        when(model.getEnemyPosition()).thenReturn(ENEMY_POS);
        when(model.getAttackPosition()).thenReturn(ATK_POS);
        when(model.isGameOver()).thenReturn(false);
        when(model.getWhoDied()).thenReturn(null);

        when(enemy.getCurrentPosition()).thenReturn(ENEMY_POS);
        when(enemy.getCurrentHp()).thenReturn(42);
    }

    @Test
    void enterState_redrawsImmediately() {
        when(model.getPlayerHealth()).thenReturn(10);
        when(model.getEnemyHealth()).thenReturn(10);

        GameOverState state = new GameOverState(combatCollision, gridNotifier, enemy, player);
        state.enterState(controller);

        // Il redraw avviene subito
        verify(view, timeout(200)).redrawGrid(any(RedrawContext.class));
    }

    @Test
    void whenPlayerIsDead_showGameOverIsCalled() throws InterruptedException {
        when(model.getPlayerHealth()).thenReturn(0);
        when(model.getEnemyHealth()).thenReturn(10);

        GameOverState state = new GameOverState(combatCollision, gridNotifier, enemy, player);
        state.enterState(controller);

        Thread.sleep(1000); // aspetta che il Timer da 700 ms scatti

        verify(view).showGameOver(any(Runnable.class));
        verify(combatCollision, never()).setInCombat(anyBoolean());
        verify(gridNotifier, never()).notifyEnemyRemoved(any());
    }

    @Test
    void whenEnemyIsDead_rewardsAndOverworld() throws InterruptedException {
        when(model.getPlayerHealth()).thenReturn(10);
        when(model.getEnemyHealth()).thenReturn(0);

        GameOverState state = new GameOverState(combatCollision, gridNotifier, enemy, player);
        state.enterState(controller);

        Thread.sleep(1000);

        verify(gridNotifier).notifyEnemyRemoved(ENEMY_POS);
        verify(gridNotifier).notifyListEnemyRemoved(ENEMY_POS);
        verify(combatCollision).setInCombat(false);
        verify(combatCollision).showOverworld();
        verify(view).showInfo("You Win! Returning to Overworld...");
        verify(enemy, never()).setHp(anyInt());
    }

    @Test
    void whenNoOneIsDead_enemyHealthSynced() throws InterruptedException {
        when(model.getPlayerHealth()).thenReturn(10);
        when(model.getEnemyHealth()).thenReturn(25);

        GameOverState state = new GameOverState(combatCollision, gridNotifier, enemy, player);
        state.enterState(controller);

        Thread.sleep(1000);

        verify(combatCollision).setInCombat(false);
        verify(combatCollision).showOverworld();
        verify(enemy).setHp(25);
        verify(view, never()).showGameOver(any());
        verify(view, never()).showInfo("You Win! Returning to Overworld...");
    }
}
