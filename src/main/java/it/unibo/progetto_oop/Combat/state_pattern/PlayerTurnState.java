package it.unibo.progetto_oop.combat.state_pattern;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.potion_strategy.CurePoison;
import it.unibo.progetto_oop.Overworld.Player.Player;

/**
 * Player's turn state in combat.
 */
public class PlayerTurnState implements CombatState {
    /**
     * The amount of stamina to be removed after a special attack.
     */
    private static final int STAMINA_TO_REMOVE = 10;
    /**
     * Strategy for curing poison.
     */
    private final CurePoison curePoison;

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
        context.setState(new AnimatingState());
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
        context.getView().showInfo("Bag Selected");
    }

    @Override
    public final void handleRunInput(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'handleRunInput'");
    }

    @Override
    public final void enterState(final CombatController context) {
        context.getModel().setPlayerTurn(true);
        context.getView().setAllButtonsEnabled();
        context.getView().showOriginalButtons();
        context.getView().showInfo("Your Turn!");
    }

    @Override
    public final void exitState(final CombatController context) {
        context.getView().clearInfo();
        context.getModel().setPlayerTurn(false);
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

}
