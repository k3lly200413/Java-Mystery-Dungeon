package it.unibo.progetto_oop.Combat.StatePattern;

import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class GameOverState implements  CombatState {

    @Override
    public void enterState(final CombatController context) {
        System.out.println("\n\nEntered Game Over State\n\n");
        context.redrawView(true, true, false, false, 0, 1, 2,
        true, context.getModel().getWhoDied(),
        context.getModel().getEnemyPosition(),
        false, 0, false, context.getModel().getDeathRayPath());
    }

    /**
     *
     * @param context Istance of the controller
     *
     * This method is called when exiting a combat state.
     */
    @Override
    public void exitState(final CombatController context) {
        System.out.println("\n\nExited Game Over State\n\n");
    }

    /**
     *
     * @param context Istance of the controller
     *
     * This method is called when an animation is complete during combat.
     */
    @Override
    public void handleAnimationComplete(final CombatController context) {
        System.out.println("Animation Complete in Game Over State");
    }

    @Override
    public void handleAttackBuffInput(final CombatController context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleBackInput(final CombatController context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleBagInput(final CombatController context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleCurePoisonInput(final CombatController context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleHealInput(final CombatController context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleInfoInput(final CombatController context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleLongRangeAttackInput(final CombatController context,
    final boolean isPoison, final boolean isFlame) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handlePhysicalAttackInput(final CombatController context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handlePotionUsed(final CombatController context,
    final Item selectedPotion, final Player player) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleRunInput(final CombatController context) {
        // TODO Auto-generated method stub

    }

}
