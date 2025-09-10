package it.unibo.progetto_oop.combat;

import it.unibo.progetto_oop.combat.combat_builder.CombatBuilder;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatModel;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatView;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatViewInterface;
import it.unibo.progetto_oop.overworld.combat_collision.CombatCollision;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.player.Player;

/**
 * Launcher class for the combat application.
 * Initializes the MVC components and starts the combat
 */
public final class CombatLauncher {

    /** Combat controller instance. */
    private CombatController combatController;

    /**
     * Main method to launch the combat application.
     *
     * @param player the player instance
     * @param combatCollision the combat collision instance
     * @param gridNotifier the grid notifier instance
     * @return combatController instance
     */
    public CombatController buildCombat(final Player player,
    final CombatCollision combatCollision,
    final GridNotifier gridNotifier) {
        // --- Game Configuration ---
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

        // --- Application Startup ---
        // Ensure UI creation happens on the Event
        // Dispatch Thread (EDT) for safety.
        // 1. Create the Model with our configuration
        final CombatModel model = new CombatBuilder()
        .setSize(size)
        .setStaminaMax(playerMaxStamina)
        .setPlayerPower(playerPower)
        .setPlayerPoisonPower(playerPoisonPower)
        .setPlayerLongRangePower(playerLongRangePower)
        .setPlayerCurrentHealth(player.getCurrentHp())
        .setEnemyPower(enemyPower)
        .setEnemySpeed(enemySpeed)
        .setEnemyName(enemyName)
        .setPlayerMaxHealth(maxHealth)
        .build();
        // 2. Create the View
        final CombatViewInterface view = new CombatView(model.getSize(),
        viewWidthFactor * model.getSize() / sizeDivisor,
        viewHeightFactor * model.getSize() / sizeDivisor,
        buttonWidth, buttonHeight, windowWidth, windowHeight);
        view.init();
        // 3. Create the Controller, linking the Model and View
        this.combatController =
            new CombatController(model, view, player,
            combatCollision, gridNotifier);
        return this.combatController;
    }

    /**
     * Sets the encountered enemy in the combat controller.
     *
     * @param encounteredEnemy the enemy encountered by the player
     */
    public void setEncounteredEnemy(final Enemy encounteredEnemy) {
        this.combatController.setEncounteredEnemy(encounteredEnemy);
    }

}
