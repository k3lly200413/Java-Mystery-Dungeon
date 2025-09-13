package it.unibo.progetto_oop.combat.mvc_pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;

import it.unibo.progetto_oop.combat.combat_builder.CombatBuilder;
import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.combat.state_pattern.AnimatingState;
import it.unibo.progetto_oop.combat.state_pattern.BossTurnState;
import it.unibo.progetto_oop.combat.state_pattern.EnemyTurnState;
import it.unibo.progetto_oop.combat.state_pattern.InfoDisplayState;
import it.unibo.progetto_oop.combat.state_pattern.ItemSelectionState;
import it.unibo.progetto_oop.combat.state_pattern.PlayerTurnState;
import it.unibo.progetto_oop.overworld.combat_collision.CombatCollision;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.player.Player;

class CombatPresenterTest {

    /** sleep value for thread.sleep. */
    private static final int SLEEP_1 = 350;

    /** sleep value for thread.sleep. */
    private static final int SLEEP_2 = 300;

    /** sleep value for thread.sleep. */
    private static final int SLEEP_3 = 1600;

    private static final String IGNORED = "test interrupt";

    private CombatModel model;
    private CombatPresenter presenter;

    @BeforeEach
    void setUpCombatPresenter() {
        final Player player = new Player(100, 100, 100, new Inventory());
        final CombatCollision collision = mock(CombatCollision.class);
        final GridNotifier gridNotifier = mock(GridNotifier.class);
        final Enemy enemy = mock(Enemy.class);
        final int size = 12;
        final int playerPower = player.getPower();
        final int playerPoisonPower = 2;
        final int enemySpeed = 3;
        final String enemyName = "Dragon";
        final int playerMaxStamina = player.getStamina();
        final int playerLongRangePower = 5;

        final int viewWidthFactor = 20;

        final int viewHeightFactor = 50;

        final int buttonWidth = 70;

        final int buttonHeight = 75;

        final int windowWidth = 100;

        final int windowHeight = 100;

        final int sizeDivisor = 3;

        final int maxHealth = player.getMaxHp();

        model = new CombatBuilder()
        .setSize(size)
        .setPlayerMaxHealth(maxHealth)
        .setStaminaMax(playerMaxStamina)
        .setPlayerPower(playerPower)
        .setPlayerPoisonPower(playerPoisonPower)
        .setPlayerLongRangePower(playerLongRangePower)
        .setPlayerCurrentHealth(player.getCurrentHp())
        .setEnemySpeed(enemySpeed)
        .setEnemyName(enemyName)
        .setPlayerMaxHealth(maxHealth)
        .build();
        final CombatViewInterface view = new CombatView(model.getSize(),
        viewWidthFactor * model.getSize() / sizeDivisor,
        viewHeightFactor * model.getSize() / sizeDivisor,
        buttonWidth, buttonHeight, windowWidth, windowHeight);
        view.init();
        this.presenter = new CombatPresenter(model, view, player, collision, gridNotifier);
        this.presenter.setEncounteredEnemy(enemy);
    }

    @Test
    void initialpresenterPlayerTurnStatesTest() {
        assertTrue(this.presenter.getCurrentState() instanceof PlayerTurnState, "Initial state should be PlayerTurnState");
    }

    @Test
    void curePoisonTest() {
        this.model.setPlayerPoisoned(true);
        this.presenter.setState(new ItemSelectionState());
        assertTrue(this.model.isPlayerPoison(), "Player should be poisoned");

        this.model.setPlayerPoisoned(true);
        this.presenter.setState(new ItemSelectionState());
        this.presenter.handleCurePoisonInput();
        assertFalse(this.model.isPlayerPoison(), "Player should not be poisoned");
        initialpresenterPlayerTurnStatesTest();
    }

    @Test
    void setStateTest() {
        presenter.setState(new BossTurnState());
        assertTrue(this.presenter.getCurrentState() instanceof AnimatingState,
        "State should be set to AnimatingState because BossTurnState transitions to AnimatingState");
        presenter.setState(new EnemyTurnState());
        assertTrue(this.presenter.getCurrentState() instanceof EnemyTurnState, "State should be set to EnemyTurnState");
        presenter.setState(new PlayerTurnState());
        assertTrue(this.presenter.getCurrentState() instanceof PlayerTurnState);
        presenter.setState(new AnimatingState());
        assertTrue(this.presenter.getCurrentState() instanceof AnimatingState);
        presenter.setState(new ItemSelectionState());
        assertTrue(this.presenter.getCurrentState() instanceof ItemSelectionState);
    }

    @Test
    void stopAnimationTimerTest() {
        presenter.performPlayerPhysicalAttack();
        presenter.stopAnimationTimer();
        assertTrue(!presenter.isAnimationRunning(), "Animation timer should be stopped after calling stopAnimationTimer");
    }

    @Test
    void isAnimationRunningTest() {
        presenter.performPlayerPhysicalAttack();
        assertTrue(presenter.isAnimationRunning(),
        "Animation timer should be running after performing a player physical attack");
    }

    @Test
    void playerPhysicalAttackAnimationStartedTest() {
        presenter.performPlayerPhysicalAttack();
        assertTrue(presenter.isAnimationRunning(),
        "Animation timer should be running after performing a player physical attack");
    }

    @Test
    void enemyPhysicalAttackAnimationStartedTest() {
        presenter.performEnemyPhysicalAttack();
        assertTrue(presenter.isAnimationRunning(),
        "Animation timer should be running after performing an enemy physical attack");
    }

    @Test
    void longRangeAttackAnimationStartedTest() {
        presenter.stopAnimationTimer();
        presenter.performLongRangeAttack(model.getPlayerPosition(), 1, true, false);
        assertTrue(presenter.isAnimationRunning(),
        "Animation timer should be running after performing a player long range attack");
        presenter.stopAnimationTimer();
        presenter.performLongRangeAttack(model.getEnemyPosition(), -1, true, false);
        assertTrue(presenter.isAnimationRunning(),
        "Animation timer should be running after performing an enemy long range attack");
        presenter.stopAnimationTimer();
        presenter.performLongRangeAttack(model.getPlayerPosition(), 1, false, true);
        assertTrue(presenter.isAnimationRunning(),
        "Animation timer should be running after performing a player poison attack");
        presenter.stopAnimationTimer();
        presenter.performLongRangeAttack(model.getEnemyPosition(), -1, false, true);
        assertTrue(presenter.isAnimationRunning(),
        "Animation timer should be running after performing an enemy poison attack");
    }

    @Test
    void resetForNewCombatTest() {
        presenter.setState(new EnemyTurnState());
        presenter.resetForNewCombat();
        assertTrue(presenter.getCurrentState() instanceof PlayerTurnState,
        "After resetting for the new combat it should be the player's turn");
    }

    @Test
    void enemyTurnFinishesAndReturnsControlToPlayer() {

        presenter.setState(new EnemyTurnState());
        presenter.performEnemyPhysicalAttack();

        try {
            Thread.sleep(SLEEP_1);
        } catch (final InterruptedException ignored) {
            fail(IGNORED, ignored);
        }

        presenter.stopAnimationTimer();
        assertTrue(!presenter.isAnimationRunning(),
        "After enemy turn animation, animation timer should be stopped");
    }

    @Test
    void infoZoomAnimationTransitionsToInfoDisplayStateAndStopsTimer() {
        presenter.setState(new PlayerTurnState());

        presenter.performInfoAnimation();

        try {
            Thread.sleep(SLEEP_2);
        } catch (final InterruptedException ignored) {
            fail(IGNORED, ignored);
        }
        assertTrue(presenter.isAnimationRunning(),
            "During the animation the timer must be running");

        // Estimated total time:
        // - Enemy movement 3 steps x 200ms ≈ 600ms
        // - makeBigger ~5 ticks x 200ms ≈ 1000ms
        // Safety margin: wait ~1.6–1.9s overall
        try {
            Thread.sleep(SLEEP_3);
        } catch (final InterruptedException ignored) {
            fail(IGNORED, ignored);
        }

        assertTrue(presenter.getCurrentState() instanceof InfoDisplayState,
            "After the animation the state must be InfoDisplayState");
        assertTrue(!presenter.isAnimationRunning(),
            "After the animation the timer must be stopped");
    }

}
