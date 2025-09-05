/**
 * @author Laura Bertozzi
 */
package it.unibo.progetto_oop.combat.state_pattern;

import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.potion_strategy.PotionStrategy;



public class ItemSelectionState implements CombatState {

    @Override
    public void enterState(final CombatController context) {
        context.getView().showInfo("\nEntered selection section");
    }

    @Override
    public void exitState(final CombatController context) {
        context.getView().showInfo("\nEXITING\n");

    }

    @Override
    public void handlePhysicalAttackInput(final CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handlePhysicalAttackInput'");
    }

    @Override
    public void handleInfoInput(final CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleInfoInput'");
    }

    @Override
    public void handleBackInput(final CombatController context) {
        // TODO --> turn back the state to player state (need a function to set state in CombatController)
    }

    @Override
    public void handleBagInput(final CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleBagInput'");
    }

    @Override
    public void handleRunInput(final CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleRunInput'");
    }

    @Override
    public void handleAnimationComplete(final CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleAnimationComplete'");
    }


    @Override
    public void handlePotionUsed(
        final CombatController context,
        final PotionStrategy selectedPotion, final Player player) {
        selectedPotion.applyEffect(context.getModel());
    }

    @Override
    public void handleLongRangeAttackInput(final CombatController context, boolean isPoison, boolean isFlame) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleLongRangeAttackInput'");
    }

    @Override
    public void handleCurePoisonInput(final CombatController context) {
        // this.handlePotionUsed(context, null, null); is this class needed???
    }

    @Override
    public void handleAttackBuffInput(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleAttackBuffInput'");
    }

    @Override
    public void handleHealInput(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleHealInput'");
    }
}
