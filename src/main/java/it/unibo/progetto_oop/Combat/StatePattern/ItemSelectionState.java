/**
 * @author Laura Bertozzi
 */
package it.unibo.progetto_oop.Combat.StatePattern;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Overworld.Inventory.Item;
import it.unibo.progetto_oop.Overworld.Player.Player;



public class ItemSelectionState implements CombatState {

    @Override
    public void enterState(CombatController context) {
        System.out.println("\nEntered selection section");
    }

    @Override
    public void exitState(CombatController context) {
        System.out.println("\nEXITING\n");

    }

    @Override
    public void handlePhysicalAttackInput(CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handlePhysicalAttackInput'");
    }

    @Override
    public void handleInfoInput(CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleInfoInput'");
    }

    @Override
    public void handleBackInput(CombatController context) {
        // TODO --> turn back the state to player state (need a function to set state in CombatController)
    }

    @Override
    public void handleBagInput(CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleBagInput'");
    }

    @Override
    public void handleRunInput(CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleRunInput'");
    }

    @Override
    public void handleAnimationComplete(CombatController context) {
        throw new UnsupportedOperationException("Unimplemented method 'handleAnimationComplete'");
    }


    @Override
    public void handlePotionUsed(CombatController context, Item selectedPotion, Player player) {
        // TODO --> use item on player and update the state accordingly
        // --> new player turn state
    }

    @Override
    public void handleLongRangeAttackInput(CombatController context, boolean isPoison, boolean isFlame) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleLongRangeAttackInput'");
    }

    @Override
    public void handleCurePoisonInput(CombatController context) {
        // this.handlePotionUsed(context, null, null); is this class needed???
    }
}
