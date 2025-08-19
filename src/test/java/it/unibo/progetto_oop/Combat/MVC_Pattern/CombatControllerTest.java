package it.unibo.progetto_oop.Combat.MVC_Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import javax.swing.JButton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import it.unibo.progetto_oop.Combat.StatePattern.AnimatingState;
import it.unibo.progetto_oop.Combat.StatePattern.BossTurnState;
import it.unibo.progetto_oop.Combat.StatePattern.EnemyTurnState;
import it.unibo.progetto_oop.Combat.StatePattern.FuryBossState;
import it.unibo.progetto_oop.Combat.StatePattern.ItemSelectionState;
import it.unibo.progetto_oop.Combat.StatePattern.PlayerTurnState;

public class CombatControllerTest {
    
    private CombatModel model;
    private CombatView view;
    private CombatController controller;

    // Need before each because tests modify model and if I were to use BeforeAll it will modify the model so all tests will be affected
    @BeforeEach
    void setUp(){
        this.model = new CombatModel(12,     // size
            100,    // playerMaxStamina
            7,      // playerPower
            2,      // playerPoisonPower
            5,      // playerLongRangePower
            3,      // enemyPower
            3,      // enemySpeed
            "Dragon");

        this.view = new CombatView(model.getSize(), (20 * model.getSize()) / 3, (50 * model.getSize()) / 3, 70, 75, 100, 100);

        this.controller = new CombatController(model, view);
    }

    @Test
    void attachListenersTest() {
        var listeners = controller.getView().getLongRangeAttackButton();
        assertTrue(listeners.getActionListeners().length > 0, "Long Range Attack Button should have listeners attached");
    }

    @Test
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
        controller.setState(new FuryBossState());
        assertTrue(this.controller.getCurrentState() instanceof FuryBossState);
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
    void playerLongRangeAttackAnimationStartedTest() {
        controller.performLongRangeAttack(model.getPlayerPosition(), 1, true, false);
        assertTrue(controller.isAnimationRunning(), "Animation timer should be running after performing a player long range attack");
    }

    
}
