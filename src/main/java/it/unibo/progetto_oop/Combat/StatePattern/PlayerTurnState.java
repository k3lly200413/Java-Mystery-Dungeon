package it.unibo.progetto_oop.Combat.StatePattern;

import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.PotionStrategy.CurePoison;

public class PlayerTurnState implements CombatState {

    /**
     * Strategy for curing poison.
     */
    private final CurePoison curePoison;

    /**
     * The amount of stamina to be removed after a special attack.
     */
    private static final int STAMINA_TO_REMOVE = 10;

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
        context.getView().setAllButtonsDisabled();
        context.getView().clearInfo();
        context.getView().showInfo("Player Has used physical Attack");
        System.out.println(
            "Debug Log: Requested Physical\nCurrent State: Player Turn State");
        context.setState(new AnimatingState());
        context.performPlayerPhysicalAttack();
    }

    @Override
    public final void handleLongRangeAttackInput(
        final CombatController context,
        final boolean isPoison,
        final boolean isFalme) {

        context.setState(new AnimatingState());
        context.getModel().decreasePlayerStamina(STAMINA_TO_REMOVE);
        context.getView().updatePlayerStamina(
            context.getModel().getPlayerStamina());
        context.getView().showInfo("Player Has used Long Range Attack");
        context.performLongRangeAttack(
            context.getModel().getPlayerPosition(), 1, isFalme, isPoison);
    }

    @Override
    public final void handleInfoInput(final CombatController context) {
        context.performInfoAnimation();
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
        context.getModel().setPlayerTurn(true);
        context.getView().setAllButtonsEnabled();
        context.getView().showOriginalButtons();
        context.getView().showInfo("Your Turn!");
        System.out.println("------ Entering Player Turn State ------");
    }

    @Override
    public final void exitState(final CombatController context) {
        System.out.println("------ Exiting Player Turn State ------");
        context.getView().clearInfo();
        // context.getModel().setPlayerTurn(false);
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
        // throw new UnsupportedOperationException(
                // "Unimplemented method 'handleAttackBuffInput'");
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
        final Item selectedPotion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'handlePotionUsed'");
    }

}
