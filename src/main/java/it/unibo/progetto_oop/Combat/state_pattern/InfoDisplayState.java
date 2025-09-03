package it.unibo.progetto_oop.combat.state_pattern;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatModel;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatView;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class InfoDisplayState implements CombatState {

    @Override
    public void handlePhysicalAttackInput(final CombatController context) {
        /*
         * Handle physical attack should not be called while in this state
         */
    }

    @Override
    public void handleLongRangeAttackInput(final CombatController context,
    final boolean isFlame, final boolean isPoison) {
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
    public void enterState(final CombatController context) {
        final CombatModel model = context.getModel();
        final CombatView view = context.getView();

        view.setAllButtonsDisabled();

        final String infoText = String.format(
            "<html>Enemy Info:<br>Name: %s<br>Power: %d<br>Speed: %d</html>",
            model.getEnemyName(),
            model.getEnemyPower(),
            model.getEnemySpeed() // Add other stats
        );
        view.showInfo(infoText);

        view.showAttackOptions();
        view.setCustomButtonEnabled(view.getAttackBackButton());

    }

// --- Make sure exitState or PlayerTurnState.enterState handles the reset ---


    @Override
    public void exitState(final CombatController context) {
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
