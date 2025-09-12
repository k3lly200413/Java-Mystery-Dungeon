/**
 * @author Laura Bertozzi
 */

package it.unibo.progetto_oop.combat.state_pattern;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.overworld.player.Player;

/**
 * Class representing the Item Selection State in the combat state pattern.
 */
public class ItemSelectionState implements CombatState {

    /**
     * Handles the entry into the item selection state.
     *
     * @param context Instance of the controller
     */
    @Override
    public void enterState(final CombatController context) {
        context.getViewApi().showInfo("\nEntered selection section");
    }

    /**
     * Handles the exit from the item selection state.
     *
     * @param context Instance of the controller
     */
    @Override
    public void exitState(final CombatController context) {
        context.getViewApi().showInfo("\nEXITING\n");

    }

    /**
     * Handles the physical attack input action.
     */
    @Override
    public void handlePhysicalAttackInput(final CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handlePhysicalAttackInput'");
    }

    /**
     * Handles the info input action.
     */
    @Override
    public void handleInfoInput(final CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleInfoInput'");
    }

    /**
     * Handles the back input action.
     */
    @Override
    public void handleBackInput(final CombatController context) {
        context.performBackToMainMenu();
    }

    /**
     * Handles the bag input action.
     */
    @Override
    public void handleBagInput(final CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleBagInput'");
    }

    /**
     * Handles the run input action.
     */
    @Override
    public void handleRunInput(final CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleRunInput'");
    }

    /**
     * Handles the completion of an animation.
     */
    @Override
    public void handleAnimationComplete(final CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleAnimationComplete'");
    }

    /**
     * Handles the use of a potion item.
     */
    @Override
    public void handlePotionUsed(
        final CombatController context,
        final Item selectedPotion, final Player player) {
        selectedPotion.use(context.getModel());
    }

    /**
     * Handles the input for long range attacks.
     */
    @Override
    public void handleLongRangeAttackInput(final CombatController context, final boolean isPoison, final boolean isFlame) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleLongRangeAttackInput'");
    }

    @Override
    public void handleCurePoisonInput(final CombatController context) {
        // this.handlePotionUsed(context, null, null); is this class needed???
    }

    /**
     * Handles the input for attack buff items.
     */
    @Override
    public void handleAttackBuffInput(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleAttackBuffInput'");
    }

    /**
     * Handles the input for healing items.
     */
    @Override
    public void handleHealInput(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleHealInput'");
    }

}
