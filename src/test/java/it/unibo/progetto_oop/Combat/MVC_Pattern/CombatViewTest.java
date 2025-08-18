package it.unibo.progetto_oop.Combat.MVC_Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import javax.swing.JButton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import it.unibo.progetto_oop.Combat.StatePattern.AnimatingState;
import it.unibo.progetto_oop.Combat.StatePattern.PlayerTurnState;

public class CombatViewTest {
    CombatView view;
    CombatModel model;
    CombatController controller;

    @BeforeEach
    void setUp() {
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
    void initialisedButtonTest() {
        assertNotNull(this.view.getPoisonAttackButton());
        assertEquals("Poison", this.view.getPoisonAttackButton().getText());
    }

    @Test
    void poisonAttackButtonPressedTest() {
        JButton poisonButton = this.view.getPoisonAttackButton();
        poisonButton.doClick();
        assertNotNull(poisonButton);
        assertFalse(poisonButton.isEnabled());
        assertTrue(controller.isAnimationRunning());
    }
    @Test
    void playerHealthBarUpdateTest() {
        this.model.decreasePlayerHealth(10);
        this.view.updatePlayerHealth(this.model.getPlayerHealth());
        assertEquals(90, this.view.getPlayerHealthBar().getValue());
    }

    @Test
    void EnemyHealthBarUpdateTest() {
        this.model.decreaseEnemyHealth(10);
        this.view.updateEnemyHealth(this.model.getEnemyHealth());
        assertEquals(90, this.view.getEnemyHealthBar().getValue());
    }
    @Test
    void performingPhysicalAttackSetsAnimatingStateTest() {
        this.controller.setState(new PlayerTurnState());
        this.controller.getCurrentState().handlePhysicalAttackInput(controller);
        this.view.getPoisonAttackButton().isEnabled();
    }
    @Test
    void buttonsAreClickableDuringAnimationTest() {
        this.controller.setState(new AnimatingState());
        assertFalse(this.view.getAttackButtonPanel().getComponent(0).isEnabled());
    }
}
