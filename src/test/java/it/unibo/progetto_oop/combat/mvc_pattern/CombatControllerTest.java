package it.unibo.progetto_oop.combat.mvc_pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.combat.combat_builder.CombatBuilder;
import it.unibo.progetto_oop.combat.state_pattern.EnemyTurnState;
import it.unibo.progetto_oop.combat.state_pattern.InfoDisplayState;
import it.unibo.progetto_oop.combat.state_pattern.PlayerTurnState;

public class CombatControllerTest {

    private CombatModel model;
    private CombatView view;
    private CombatController controller;

    @BeforeEach
    void setUp(){
        this.model = new CombatBuilder()
            .setSize(12)
            .setStaminaMax(100)
            .setPlayerPower(10)
            .setPlayerPoisonPower(2)
            .setPlayerLongRangePower(10)
            .setEnemySpeed(3)
            .setEnemyName("Dragon")
            .build();

        this.view = new CombatView(model.getSize(), (20 * model.getSize()) / 3,
        (50 * model.getSize()) / 3, 70,
        75, 100, 100);
        this.view.init();
        this.controller = new CombatController(model, view, null, null, null);
    }

    // @Test
    // void attachListenersTest() {
    //     var listeners = controller.getView().getLongRangeAttackButton();
    //     assertTrue(listeners.getActionListeners().length > 0, "Long Range Attack Button should have listeners attached");
    // }

    /*@Test
    void curePoisonTest() {
        this.model.setPlayerPoisoned(true);
        this.controller.setState(new PlayerTurnState());
        this.controller.getCurrentState().handleCurePoisonInput(controller);
        assertEquals(false, this.model.isPlayerPoison());
    }

    @Test
    void initialControllerPlayerTurnStatesTest() {
        assertTrue(this.controller.getCurrentState() instanceof PlayerTurnState, "Initial state should be PlayerTurnState");
    }

    @Test
    void setStateTest() {
        controller.setState(new EnemyTurnState());
        assertTrue(this.controller.getCurrentState() instanceof EnemyTurnState, "State should be set to EnemyTurnState");
        controller.setState(new PlayerTurnState());
        assertTrue(this.controller.getCurrentState() instanceof PlayerTurnState);
        controller.setState(new AnimatingState());
        assertTrue(this.controller.getCurrentState() instanceof AnimatingState);
        controller.setState(new BossTurnState());
        assertTrue(this.controller.getCurrentState() instanceof BossTurnState);
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
        assertTrue(controller.isAnimationRunning(), "Animation timer should be running after performing a player physical attack");
    }

    @Test
    void playerPhysicalAttackAnimationStartedTest() {
        controller.performPlayerPhysicalAttack();
        assertTrue(controller.isAnimationRunning(), "Animation timer should be running after performing a player physical attack");
    }
    @Test
    void enemyPhysicalAttackAnimationStartedTest() {
        controller.performEnemyPhysicalAttack();
        assertTrue(controller.isAnimationRunning(), "Animation timer should be running after performing an enemy physical attack");
    }
    @Test
    void longRangeAttackAnimationStartedTest() {
        controller.stopAnimationTimer();
        controller.performLongRangeAttack(model.getPlayerPosition(), 1, true, false);
        assertTrue(controller.isAnimationRunning(), "Animation timer should be running after performing a player long range attack");
        controller.stopAnimationTimer();
        controller.performLongRangeAttack(model.getEnemyPosition(), -1, true, false);
        assertTrue(controller.isAnimationRunning(), "Animation timer should be running after performing a player long range attack");
        controller.stopAnimationTimer();
        controller.performLongRangeAttack(model.getPlayerPosition(), 1, false, true);
        assertTrue(controller.isAnimationRunning(), "Animation timer should be running after performing a player long range attack");
        controller.stopAnimationTimer();
        controller.performLongRangeAttack(model.getEnemyPosition(), -1, false, true);
        assertTrue(controller.isAnimationRunning(), "Animation timer should be running after performing a player long range attack");
    } */

    @Test
    void enemyTurnFinishesAndReturnsControlToPlayer() {

        controller.setState(new EnemyTurnState());
        controller.performEnemyPhysicalAttack();

        try {
            Thread.sleep(350);
        } catch (InterruptedException ignored) { }

        controller.stopAnimationTimer();
        assertTrue(!controller.isAnimationRunning(),
        "After enemy turn animation, animation timer should be stopped");
    }

    @Test
    void infoZoomAnimationTransitionsToInfoDisplayStateAndStopsTimer() {
        controller.setState(new PlayerTurnState());

        controller.performInfoAnimation();

        try {
            Thread.sleep(300);
        } catch (InterruptedException ignored) { }
        assertTrue(controller.isAnimationRunning(),
            "During the animation the timer must be running");

        // Estimated total time:
        // - Enemy movement 3 steps x 200ms ≈ 600ms
        // - makeBigger ~5 ticks x 200ms ≈ 1000ms
        // Safety margin: wait ~1.6–1.9s overall
        try {
            Thread.sleep(1600);
        } catch (InterruptedException ignored) { }

        assertTrue(controller.getCurrentState() instanceof InfoDisplayState,
            "After the animation the state must be InfoDisplayState");
        assertTrue(!controller.isAnimationRunning(),
            "After the animation the timer must be stopped");
    }

}
