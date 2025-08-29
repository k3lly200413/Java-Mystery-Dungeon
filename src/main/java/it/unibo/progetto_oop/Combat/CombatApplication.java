package it.unibo.progetto_oop.Combat;

import javax.swing.SwingUtilities;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatView;

public class CombatApplication {

    /**
     *
     * @param args
     */
    public static void main(final String[] args) {
        // --- Game Configuration ---
        final int size = 12;
        final int playerPower = 10;
        final int playerPoisonPower = 2;
        final int enemyPower = 3;
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
            CombatModel model = new CombatModel(size,
            playerMaxStamina, playerPower,
            playerPoisonPower, playerLongRangePower, enemyPower,
            enemySpeed, enemyName);

            // 2. Create the View
            CombatView view = new CombatView(model.getSize(),
            (viewWidthFactor * model.getSize()) / sizeDivisor,
            (viewHeightFactor * model.getSize()) / sizeDivisor,
            buttonWidth, buttonHeight, windowWidth, windowHeight);

            // 3. Create the Controller, linking the Model and View
            CombatController controller = new CombatController(model, view);

            // 4. Start the combat UI, making the window visible
            controller.startCombat();
        });
    }
}
