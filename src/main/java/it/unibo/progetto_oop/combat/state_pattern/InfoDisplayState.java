package it.unibo.progetto_oop.combat.state_pattern;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.ActionType;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatModel;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatView;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatViewInterface;
import it.unibo.progetto_oop.overworld.player.Player;

/**
 * Class representing the Info Display State in the combat state pattern.
 * In this state, the player is viewing detailed information about the enemy.
 * The view is zoomed in on the enemy, and only the Back button is enabled
 * to return to the previous state.
 */
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
     * @param context Instance of the controller
     *
     *                This method is called when the back button is pressed.
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
        final CombatModel model = context.getModel();
        final CombatViewInterface view = context.getView();

        view.setAllMenusDisabled();

        final String infoText = String.format(
            "<html>Enemy Info:<br>Name: %s<br>Power: %d<br>Speed: %d</html>",
            model.getEnemyName(),
            model.getEnemyPower(),
            model.getEnemySpeed() // Add other stats
        );
        view.showInfo(infoText);

        view.showAttackMenu();
        view.setActionEnabled(ActionType.BACK, true);

    }

    /**
     * Handles the transition logic when exiting the InfoDisplayState.
     * Resets all positions and prepares the view for the next state.
     *
     * @param context the CombatController providing access to the model
     *                and view for updating the UI state
     */
    @Override
    public void exitState(final CombatController context) {
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
