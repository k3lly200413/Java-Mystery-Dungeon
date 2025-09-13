package it.unibo.progetto_oop.combat.mvc_pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.combat.combat_builder.CombatBuilder;
import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.combat.state_pattern.AnimatingState;
import it.unibo.progetto_oop.combat.state_pattern.InfoDisplayState;
import it.unibo.progetto_oop.overworld.combat_collision.CombatCollision;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.player.Player;

class CombatViewTest {
    private CombatView view;
    private CombatModel model;
    private CombatPresenter controller;

    @BeforeEach
    void setUpCombatView() {

        final Player player = new Player(100, 100, 100, new Inventory());
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

        final int sizeDivisor = 3;

        final int maxHealth = player.getMaxHp();
        final CombatCollision collision = mock(CombatCollision.class);
        final GridNotifier gridNotifier = mock(GridNotifier.class);
        this.model = new CombatBuilder()
            .setSize(size)
            .setStaminaMax(playerMaxStamina)
            .setPlayerPower(playerPower)
            .setPlayerPoisonPower(playerPoisonPower)
            .setPlayerLongRangePower(playerLongRangePower)
            .setEnemySpeed(enemySpeed)
            .setEnemyName(enemyName)
            .setPlayerCurrentHealth(100)
            .setEnemyCurrentHealth(100)
            .setEnemyMaxHealth(maxHealth)
            .build();

        this.view = new CombatView(model.getSize(), viewWidthFactor * model.getSize() / sizeDivisor,
        viewHeightFactor * model.getSize() / sizeDivisor, buttonWidth,
        buttonHeight, 100, 100);
        this.view.init();
        this.controller = new CombatPresenter(model, view, player, collision, gridNotifier);
        this.view.setPlayerMaxStaminaBar(100);
        this.view.setPlayerHealthBarMax(100);
    }

    public CombatView getView() {
        return this.view;
    }

    public CombatModel getModel() {
        return this.model;
    }

    public CombatPresenter getController() {
        return this.controller;
    }

    @Test
    void initialisedButtonTest() {
        assertNotNull(this.view.getPoisonAttackButton());
        assertEquals("Poison", this.view.getPoisonAttackButton().getText());
    }

    @Test
    void playerHealthBarUpdateTest() {
        this.model.decreasePlayerHealth(10);
        this.view.updatePlayerHealth(this.model.getPlayerHealth());
        assertEquals(90, this.view.getPlayerHealthBar().getValue());
    }

    @Test
    void enemyHealthBarUpdateTest() {
        final int remaining = model.applyAttackHealth(
            true,
            10
        );
        this.view.updateEnemyHealth(remaining);
        assertEquals(90, this.view.getEnemyHealthBar().getValue());
    }

    @Test
    void buttonsAreNotClickableDuringAnimationTest() {
        this.controller.setState(new AnimatingState());
        this.view.showBagMenu();
        assertFalse(this.view.getAttackButtonPanel().getComponent(0).isEnabled());
        this.view.showAttackMenu();
        assertFalse(this.view.getAttackButtonPanel().getComponent(0).isEnabled());
        this.view.showMainMenu();
        assertFalse(this.view.getAttackButtonPanel().getComponent(0).isEnabled());
    }

    @Test
    void setTextOnInfoLabel() {
        this.controller.setState(new InfoDisplayState());
        this.controller.handleInfo();
        this.view.getInfoLabel().getText();
        final String stringResult = "<html><html>Enemy Info:<br>Name: "
        + this.model.getEnemyName()
        + "<br>Physical Power: "
        + this.model.getEnemyPower()
        + "<br>Long Range Power: "
        + this.model.getEnemyLongRangePower()
        + "</html></html>";
        assertEquals(this.view.getInfoLabel().getText(), stringResult);
    }

}
