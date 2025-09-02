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
    public static void main(String[] args) {
        // --- Game Configuration ---
        int size = 12;
        int playerPower = 7;
        int playerPoisonPower = 2;
        int enemyPower = 3;
        int enemySpeed = 3;
        String enemyName = "Dragon";
        int playerMaxStamina = 100;
        int playerLongRangePower = 5;

        // --- Application Startup ---
        // Ensure UI creation happens on the Event Dispatch Thread (EDT) for safety.
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