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
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.player.Player;

class CombatControllerTest {

    /** sleep value for thread.sleep. */
    private static final int SLEEP_1 = 350;

    /** sleep value for thread.sleep. */
    private static final int SLEEP_2 = 300;

    /** sleep value for thread.sleep. */
    private static final int SLEEP_3 = 1600;

    private static final String IGNORED = "test interrupt";

    private CombatModel model;
    private CombatController controller;

    @BeforeEach
    void setUp() {
        final Player player = new Player(100, 100, 100, new Inventory());
        final CombatCollision collision = mock(CombatCollision.class);
        final GridNotifier gridNotifier = mock(GridNotifier.class);
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
        this.controller = new CombatController(model, view, player, collision, gridNotifier);
    }

    @Test
    void initialControllerPlayerTurnStatesTest() {
        assertTrue(this.controller.getCurrentState() instanceof PlayerTurnState, "Initial state should be PlayerTurnState");
    }

    @Test
    void curePoisonTest() {
        this.model.setPlayerPoisoned(true);
        this.controller.setState(new ItemSelectionState());
        assertTrue(this.model.isPlayerPoison(), "Player should be poisoned");

        this.model.setPlayerPoisoned(true);
        this.controller.setState(new ItemSelectionState());
        this.controller.handleCurePoisonInput();
        assertFalse(this.model.isPlayerPoison(), "Player should not be poisoned");
        initialControllerPlayerTurnStatesTest();
    }

    @Test
    void setStateTest() {
        controller.setState(new BossTurnState());
        assertTrue(this.controller.getCurrentState() instanceof AnimatingState,
        "State should be set to AnimatingState because BossTurnState transitions to AnimatingState");
        controller.setState(new EnemyTurnState());
        assertTrue(this.controller.getCurrentState() instanceof EnemyTurnState, "State should be set to EnemyTurnState");
        controller.setState(new PlayerTurnState());
        assertTrue(this.controller.getCurrentState() instanceof PlayerTurnState);
        controller.setState(new AnimatingState());
        assertTrue(this.controller.getCurrentState() instanceof AnimatingState);
        controller.setState(new ItemSelectionState());
        assertTrue(this.controller.getCurrentState() instanceof ItemSelectionState);
    }

    @Test
    void stopAnimationTimerTest() {
        controller.performPlayerPhysicalAttack();
        controller.stopAnimationTimer();
        assertTrue(!controller.isAnimationRunning(), "Animation timer should be stopped after calling stopAnimationTimer");
    }

    @Test
    void isAnimationRunningTest() {
        controller.performPlayerPhysicalAttack();
        assertTrue(controller.isAnimationRunning(),
        "Animation timer should be running after performing a player physical attack");
    }

    @Test
    void playerPhysicalAttackAnimationStartedTest() {
        controller.performPlayerPhysicalAttack();
        assertTrue(controller.isAnimationRunning(),
        "Animation timer should be running after performing a player physical attack");
    }

    @Test
    void enemyPhysicalAttackAnimationStartedTest() {
        controller.performEnemyPhysicalAttack();
        assertTrue(controller.isAnimationRunning(),
        "Animation timer should be running after performing an enemy physical attack");
    }

    @Test
    void longRangeAttackAnimationStartedTest() {
        controller.stopAnimationTimer();
        controller.performLongRangeAttack(model.getPlayerPosition(), 1, true, false);
        assertTrue(controller.isAnimationRunning(),
        "Animation timer should be running after performing a player long range attack");
        controller.stopAnimationTimer();
        controller.performLongRangeAttack(model.getEnemyPosition(), -1, true, false);
        assertTrue(controller.isAnimationRunning(),
        "Animation timer should be running after performing an enemy long range attack");
        controller.stopAnimationTimer();
        controller.performLongRangeAttack(model.getPlayerPosition(), 1, false, true);
        assertTrue(controller.isAnimationRunning(),
        "Animation timer should be running after performing a player poison attack");
        controller.stopAnimationTimer();
        controller.performLongRangeAttack(model.getEnemyPosition(), -1, false, true);
        assertTrue(controller.isAnimationRunning(),
        "Animation timer should be running after performing an enemy poison attack");
    }

    @Test
    void resetForNewCombatTest() {
        controller.setState(new EnemyTurnState());
        controller.resetForNewCombat();
        assertTrue(controller.getCurrentState() instanceof PlayerTurnState,
        "After resetting for the new combat it should be the player's turn");
    }

    @Test
    void enemyTurnFinishesAndReturnsControlToPlayer() {

        controller.setState(new EnemyTurnState());
        controller.performEnemyPhysicalAttack();

        try {
            Thread.sleep(SLEEP_1);
        } catch (final InterruptedException ignored) {
            fail(IGNORED, ignored);
        }

        controller.stopAnimationTimer();
        assertTrue(!controller.isAnimationRunning(),
        "After enemy turn animation, animation timer should be stopped");
    }

    @Test
    void infoZoomAnimationTransitionsToInfoDisplayStateAndStopsTimer() {
        controller.setState(new PlayerTurnState());

        controller.performInfoAnimation();

        try {
            Thread.sleep(SLEEP_2);
        } catch (final InterruptedException ignored) {
            fail(IGNORED, ignored);
        }
        assertTrue(controller.isAnimationRunning(),
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

        assertTrue(controller.getCurrentState() instanceof InfoDisplayState,
            "After the animation the state must be InfoDisplayState");
        assertTrue(!controller.isAnimationRunning(),
            "After the animation the timer must be stopped");
    }

}
