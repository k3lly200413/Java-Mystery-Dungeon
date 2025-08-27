package it.unibo.progetto_oop.Combat;

import javax.swing.SwingUtilities;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatView;

public class CombatApplication {

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
     * @param args
     */
    public static void main(String[] args) {
        // --- Game Configuration ---
        int size = 12;
        int playerPower = 99;
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
            CombatModel model = new CombatModel(size, playerMaxStamina, playerPower, playerPoisonPower, playerLongRangePower, enemyPower, enemySpeed, enemyName);

            // 2. Create the View
            CombatView view = new CombatView(model.getSize(), (20 * model.getSize()) / 3, (50 * model.getSize()) / 3, 70, 75, 100, 100);

            // 3. Create the Controller, linking the Model and View
            CombatController controller = new CombatController(model, view);

            // 4. Start the combat UI, making the window visible
            controller.startCombat();
        });
    }
}