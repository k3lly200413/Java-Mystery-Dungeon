package it.unibo.progetto_oop.Combat.StatePattern;

import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.PotionStrategy.CurePoison;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class PlayerTurnState implements CombatState{

    /**
     * Strategy for curing poison.
     */
    private CurePoison curePoison;

    /**
     * Constructor for PlayerTurnState.
     * Initializes the CurePoison strategy.
     */
    public PlayerTurnState() {
        this.curePoison = new CurePoison();
    }

    @Override
    public final void handlePhysicalAttackInput(
        final CombatController context) {
        // TODO: Call controller and have it change state to animating state so this can
        // be all done during the animation

        // Disable buttons during animation
        context.getView().setAllButtonsDisabled();
        context.getView().clearInfo();
        // TODO: call model to remove 10 (placeholder) points of stamina
        context.getView().showInfo("Player Has used physical Attack");
        System.out.println(
            "Debug Log: Requested Physicaln\nCurrent State: Player Turn State");
        context.performPlayerPhysicalAttack();
    }

    @Override
    public final void handleLongRangeAttackInput(
        final CombatController context,
        final boolean isPoison,
        final boolean isFalme) {
        // TODO: Call controller and have it change state to animating state so this can
        // be all done during the animation

        // Disable buttons during animation
        context.getView().setAllButtonsDisabled();
        context.getView().clearInfo();
        // TODO: call model to remove 20 (placeholder) points of stamina
        context.getView().showInfo("Player Has used Long Range Attack");
        System.out.println(
            "Debug Log: Requested Long Range\n"
            + "Current State: Player Turn State");
        context.performPlayerLongRangeAttack(isPoison, isFalme);
    }

    @Override
    public final void handleInfoInput(final CombatController context) {
        context.performInfo();
    }

    @Override
    public final void handleBackInput(final CombatController context) {
        context.performBackToMainMenu();
    }

    @Override
    public final void handleBagInput(final CombatController context) {
        System.out.println("PlayerTurnState: Bag action requested");
    }

    @Override
    public final void handleRunInput(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'handleRunInput'");
    }

    @Override
    public final void enterState(final CombatController context) {
        context.getView().setAllButtonsEnabled();
        context.getView().showOriginalButtons();
        context.getView().showInfo("Your Turn!");
        System.out.println("------ Entering Player Turn State ------");
    }

    @Override
    public final void exitState(final CombatController context) {
        System.out.println("------ Exiting Player Turn State ------");
        context.getView().clearInfo();
    }

    @Override
    public final void handleAnimationComplete(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'handleAnimationComplete'");
    }

    @Override
    public final void handleCurePoisonInput(final CombatController context) {
        this.curePoison.applyEffect(context.getModel());
    }

    @Override
    public final void handleAttackBuffInput(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'handleAttackBuffInput'");
    }

    @Override
    public final void handleHealInput(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'handleHealInput'");
    }

    @Override
    public final void handlePotionUsed(
        final CombatController context,
        final Item selectedPotion,
        final Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'handlePotionUsed'");
    }

    @Override
    public final void enter(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
"Unimplemented method 'enter'");
    }
}
