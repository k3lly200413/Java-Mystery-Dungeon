package it.unibo.progetto_oop.combat.mvc_pattern;

import javax.swing.JButton;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.combat.combat_builder.CombatBuilder;
import it.unibo.progetto_oop.combat.state_pattern.AnimatingState;
import it.unibo.progetto_oop.combat.state_pattern.PlayerTurnState;

public class CombatViewTest {
    CombatView view;
    CombatModel model;
    CombatController controller;

    @BeforeEach
    void setUp() {
        this.model = new CombatBuilder()
            .setSize(12)
            .setStaminaMax(100)
            .setPlayerPower(10)
            .setPlayerPoisonPower(2)
            .setPlayerLongRangePower(10)
            .setEnemyPower(10)
            .setEnemySpeed(3)
            .setEnemyName("Dragon")
            .build();

        this.view = new CombatView(model.getSize(), (20 * model.getSize()) / 3, (50 * model.getSize()) / 3, 70, 75, 100, 100);
        // this.view.init();
        this.controller = new CombatController(model, view, null,null, null);
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
    void performingPhysicalAttackButtonsAreNotClickable() {
        this.controller.setState(new PlayerTurnState());
        this.controller.getCurrentState().handlePhysicalAttackInput(controller);
        // this.view.showAttackOptions();
        assertTrue(this.view.getAttackButtonPanel().isVisible());
        assertFalse(this.view.getBagButtonPanel().isVisible());
        assertFalse(this.view.getOriginalButtonPanel().isVisible());
        assertFalse(this.view.getPoisonAttackButton().isEnabled());
    }
    @Test
    void buttonsAreNotClickableDuringAnimationTest() {
        this.controller.setState(new AnimatingState());
        // this.view.showBagButtons();
        assertFalse(this.view.getAttackButtonPanel().getComponent(0).isEnabled());
        // this.view.showAttackOptions();
        assertFalse(this.view.getAttackButtonPanel().getComponent(0).isEnabled());
        // this.view.showOriginalButtons();
        assertFalse(this.view.getAttackButtonPanel().getComponent(0).isEnabled());
    }
    @Test
    void testCardLayoutShowing() {
        // this.view.showAttackOptions();
        assertTrue(this.view.getAttackButtonPanel().isVisible());
        assertFalse(this.view.getBagButtonPanel().isVisible());
        assertFalse(this.view.getOriginalButtonPanel().isVisible());
        // this.view.showBagButtons();
        assertFalse(this.view.getAttackButtonPanel().isVisible());
        assertTrue(this.view.getBagButtonPanel().isVisible());
        assertFalse(this.view.getOriginalButtonPanel().isVisible());
        // this.view.showOriginalButtons();
        assertTrue(this.view.getOriginalButtonPanel().isVisible());
        assertFalse(this.view.getAttackButtonPanel().isVisible());
        assertFalse(this.view.getBagButtonPanel().isVisible());
    }
    
}
