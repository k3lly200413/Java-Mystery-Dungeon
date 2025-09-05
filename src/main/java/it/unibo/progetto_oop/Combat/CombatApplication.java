package it.unibo.progetto_oop.combat;

import javax.swing.SwingUtilities;

import it.unibo.progetto_oop.combat.combat_builder.CombatBuilder;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatModel;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatView;

/**
 * Main entry point for the combat application.
 */
public final class CombatApplication {

    // Private constructor to prevent instantiation
    private CombatApplication() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Main method to launch the combat application.
     *
     * @param args Command line arguments
     */
    public static void main(final String[] args) {
        // --- Game Configuration ---
        final int size = 12;
        final int playerPower = 40;
        final int playerPoisonPower = 2;
        final int enemyPower = 5;
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

        // --- Application Startup ---
        SwingUtilities.invokeLater(() -> {
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
            .build();


            // 2. Create the View
            final CombatView view = new CombatView(model.getSize(),
            viewWidthFactor * model.getSize() / sizeDivisor,
            viewHeightFactor * model.getSize() / sizeDivisor,
            buttonWidth, buttonHeight, windowWidth, windowHeight);
            view.init();

            // 3. Create the Controller, linking the Model and View
            final CombatController controller =
                new CombatController(model, view);

            // 4. Start the combat UI, making the window visible
            controller.startCombat();
        });
    }
}