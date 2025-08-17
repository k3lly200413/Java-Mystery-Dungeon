package it.unibo.progetto_oop.Combat.StatePattern;

import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatView;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class InfoDisplayState implements CombatState {

     @Override
    public void handlePhysicalAttackInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePhysicalAttackInput'");
    }

    @Override
    public void handleLongRangeAttackInput(CombatController context, boolean isFlame, boolean isPoison) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleLongRangeAttackInput'");
    }

    @Override
    public void handleInfoInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleInfoInput'");
    }

    @Override
    public void handleBackInput(CombatController context) {
        context.getModel().resetPositions();
        context.redrawView();
        context.setState(new PlayerTurnState());
    }

    @Override
    public void handleBagInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleBagInput'");
    }

    @Override
    public void handleRunInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleRunInput'");
    }

    @Override
    public void handleAnimationComplete(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleAnimationComplete'");
    }

    @Override
    public void enterState(CombatController context) {
        System.out.println("Entering InfoDisplayState"); // Debug log

        CombatModel model = context.getModel();
        CombatView view = context.getView();

        // --- Step 1: Explicitly Disable ALL standard combat buttons/panels ---
        // This is crucial because the previous state (AnimatingState) likely
        // disabled them, but we need to be certain only 'Back' will be active.
        view.setButtonsEnabled(false); // Assumes this disables Attack/Bag/Run etc. AND Physical/LongRange etc. panels

        // --- Step 2: Display the Information ---
        String infoText = String.format(
            "<html>Enemy Info:<br>Name: %s<br>Power: %d<br>Speed: %d</html>",
            model.getEnemyName(),
            model.getEnemyPower(),
            model.getEnemySpeed() // Add other stats
        );
        view.showInfo(infoText);

        // --- Step 3: Enable ONLY the Back button ---
        // Since setCustomButtonEnable only enables, we are sure only 'Back' is now active.
        
        view.showAttackOptions();
        System.out.println("\nOriginal\n");
        view.setCustomButtonEnable(view.getAttackBackButton());

        // --- Step 4: Ensure zoomed view persists ---
        // Do not reset positions here.
        // Ensure the view is redrawn showing the enemy in the zoomed position.
        // The exact parameters depend on your redrawView method signature.
        // context.redrawView();

    }

// --- Make sure exitState or PlayerTurnState.enterState handles the reset ---


    @Override
    public void exitState(CombatController context) {
        System.out.println("Exiting InfoDisplayState");
        // Reset positions when leaving the info view
        context.getModel().resetPositions();
        // Clear the specific info text
        context.getView().clearInfo();
        // The next state's enterState will handle redrawing and enabling buttons.
    }

    @Override
    public void handleAttackBuffInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleAttackBuffInput'");
    }

    @Override
    public void handleHealInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleHealInput'");
    }

    @Override
    public void handleCurePoisonInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleCurePoisonInput'");
    }

    @Override
    public void handlePotionUsed(CombatController context, Item selectedPotion, Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePotionUsed'");
    }
    
}
