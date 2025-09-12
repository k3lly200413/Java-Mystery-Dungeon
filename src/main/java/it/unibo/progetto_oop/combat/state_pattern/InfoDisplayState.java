package it.unibo.progetto_oop.combat.state_pattern;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.ActionType;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatViewApi;
import it.unibo.progetto_oop.combat.mvc_pattern.ReadOnlyCombatModel;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.player.adapter_pattern.PossibleUser;

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
        context.getReadOnlyModel().resetPositions();
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
        final ReadOnlyCombatModel model = context.getReadOnlyModel();
        final CombatViewApi view = context.getViewApi();

        view.setAllMenusDisabled();

        final String infoText = String.format(
            "<html>Enemy Info:<br>Name: %s<br>Power: %d<br>Speed: %d</html>",
            model.getEnemyName(),
            model.getEnemyPower(),
            model.getEnemySpeed()
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
        context.getReadOnlyModel().resetPositions();
        context.getViewApi().clearInfo();
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
    public void handlePotionUsed(final PossibleUser user,
    final Item selectedPotion, final Player player) {
        // TODO Auto-generated method stub
    }

}
