package it.unibo.progetto_oop.combat;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision.CombatCollision;
import it.unibo.progetto_oop.Overworld.GridNotifier.GridNotifier;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.combat.combat_builder.CombatBuilder;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatModel;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatView;

public final class CombatLauncher {

    private CombatLauncher() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Main method to launch the combat application.
     *
     * @param player the player instance
     * @param combatCollision the combat collision instance
     * @param gridNotifier the grid notifier instance
     * @param enemy the enemy instance
     * @return combatController instance
     */
    public static CombatController buildCombat(final Player player,
    final CombatCollision combatCollision,
    final GridNotifier gridNotifier, final Enemy enemy) {
        // --- Game Configuration ---
        final int size = 12;
        final int playerPower = 50;
        final int playerPoisonPower = 2;
        final int enemyPower = 30;
        final int enemySpeed = 3;
        final String enemyName = "Dragon";
        final int playerMaxStamina = 100;
        final int playerLongRangePower = 5;

        final int viewWidthFactor = 20;

        final int viewHeightFactor = 50;

        final int buttonWidth = 70;

        final int buttonHeight = 75;

        final int windowWidth = 100;

        final int windowHeight = 100;

        final int sizeDivisor = 3;

        final int maxHealth = 100;

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
            .setEnemyPower(enemyPower)
            .setEnemySpeed(enemySpeed)
            .setEnemyName(enemyName)
            .setMaxHealth(maxHealth)
            .build();


            // 2. Create the View
            final CombatView view = new CombatView(model.getSize(),
            viewWidthFactor * model.getSize() / sizeDivisor,
            viewHeightFactor * model.getSize() / sizeDivisor,
            buttonWidth, buttonHeight, windowWidth, windowHeight);
            view.init();

            // 3. Create the Controller, linking the Model and View
            final CombatController controller =
                new CombatController(model, view, player,
                combatCollision, gridNotifier, enemy);
            return controller;
    }
}
