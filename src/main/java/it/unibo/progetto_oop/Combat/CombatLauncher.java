package it.unibo.progetto_oop.combat;

import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatModel;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatView;

public class CombatLauncher {

    private CombatLauncher() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Main method used to run the Application
     * <p>
     * <ul>
     *   <li><b>invokeLater</b> Explenation
     *   <li><ul>
     *     <li> Used to make sure that all GUI elements are executed on EDT thread 
     *          (thread used for graphics) so that there are no problems when it 
     *          comes to sync with Timer and other actions 
     *   </ul>
     * </ul>
     *
     * @param args Command line arguments
     * @return combatController instance
     */
    public static CombatController buildCombat() {
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
            // 1. Create the Model with our configuration
            CombatModel model = new CombatModel(size, playerMaxStamina, playerPower, playerPoisonPower, playerLongRangePower, enemyPower, enemySpeed, enemyName);

            // 2. Create the View
            CombatView view = new CombatView(model.getSize(), (20 * model.getSize()) / 3, (50 * model.getSize()) / 3, 70, 75, 100, 100);

            // 3. Create the Controller, linking the Model and View
            CombatController controller = new CombatController(model, view);

            return controller;
    }
}
