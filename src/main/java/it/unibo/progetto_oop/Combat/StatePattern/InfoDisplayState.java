package it.unibo.progetto_oop.Combat.StatePattern;

import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatView;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class InfoDisplayState implements CombatState {

    @Override
    public void handlePhysicalAttackInput(final CombatController context) {

    }

    @Override
    public void handleLongRangeAttackInput(final CombatController context,
    final boolean isFlame, final boolean isPoison) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleInfoInput(final CombatController context) {
        // TODO Auto-generated method stub
    }

    /**
     *
     * @param context Instance of the controller
     *
     * This method is called when the back button is pressed.
     */
    @Override
    public void handleBackInput(final CombatController context) {
        context.getModel().resetPositions();
        context.redrawView();
        context.setState(new PlayerTurnState());
    }

    @Override
    public void handleBagInput(final CombatController context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleRunInput(final CombatController context) {
        // TODO Auto-generated method stub
    }

    @Override
    public void handleAnimationComplete(final CombatController context) {
        // TODO Auto-generated method stub
    }

    /**
     * Handles the transition logic when entering the InfoDisplayState.
     *
     * Disables all combat buttons, displays enemy information on the view,
     * and enables only the Back button
     * while keeping the zoomed view persistent.
     *
     *
     * @param context the CombatController providing access to the model
     *                and view for updating the UI state
     */
    @Override
    public void enterState(final CombatController context) {
        System.out.println("Entering InfoDisplayState"); // Debug log

        CombatModel model = context.getModel();
        CombatView view = context.getView();

        // --- Step 1: Explicitly Disable ALL standard combat buttons/panels ---
        // This is crucial because the previous state (AnimatingState) likely
        // disabled them, but we need to be certain only 'Back' will be active.
        view.setAllButtonsDisabled();

        // --- Step 2: Display the Information ---
        String infoText = String.format(
            "<html>Enemy Info:<br>Name: %s<br>Power: %d<br>Speed: %d</html>",
            model.getEnemyName(),
            model.getEnemyPower(),
            model.getEnemySpeed() // Add other stats
        );
        view.showInfo(infoText);

        // --- Step 3: Enable ONLY the Back button ---
        // Since setCustomButtonEnable only enables,
        //we are sure only 'Back' is now active.

        view.showAttackOptions();
        System.out.println("\nOriginal\n");
        view.setCustomButtonEnabled(view.getAttackBackButton());

        // --- Step 4: Ensure zoomed view persists ---
        // Do not reset positions here.
        // Ensure the view is redrawn showing the enemy in the zoomed position.
        // The exact parameters depend on the redrawView method signature.
        // context.redrawView();

    }

// --- Make sure exitState or PlayerTurnState.enterState handles the reset ---

    /**
     * Handles the transition logic when exiting the InfoDisplayState.
     *
     * Resets all positions and prepares the view for the next state.
     *
     * @param context the CombatController providing access to the model
     *                and view for updating the UI state
     */
    @Override
    public void exitState(final CombatController context) {
        System.out.println("Exiting InfoDisplayState");
        // Reset positions when leaving the info view
        context.getModel().resetPositions();
        // Clear the specific info text
        context.getView().clearInfo();
        // The next state's enterState will
        // handle redrawing and enabling buttons.
    }

    @Override
    public void handleAttackBuffInput(final CombatController context) {
        // TODO Auto-generated method stub
    }

    @Override
    public void handleHealInput(final CombatController context) {
        // TODO Auto-generated method stub
    }

    @Override
    public void handleCurePoisonInput(final CombatController context) {
        // TODO Auto-generated method stub
    }

    @Override
    public void handlePotionUsed(final CombatController context,
    final Item selectedPotion, final Player player) {
        // TODO Auto-generated method stub
    }

}
