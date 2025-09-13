package it.unibo.progetto_oop.combat.state_pattern;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatPresenter;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.player.adapter_pattern.PossibleUser;

/**
 * Player's turn state in combat.
 */
public class PlayerTurnState implements CombatState {
    /**
     * The amount of stamina to be removed after a special attack.
     */
    private static final int STAMINA_TO_REMOVE = 10;

    /**
     * Constructor for PlayerTurnState.
     * Initializes the CurePoison strategy.
     */
    public PlayerTurnState() {
        // this.curePoison = new CurePoison();
    }

    @Override
    public final void handlePhysicalAttackInput(
        final CombatPresenter context) {
        context.getViewApi().setAllMenusDisabled();
        context.getViewApi().clearInfo();
        context.getViewApi().showInfo("Player Has used physical Attack");
        context.setState(new AnimatingState());
        context.performPlayerPhysicalAttack();
    }

    @Override
    public final void handleLongRangeAttackInput(
        final CombatPresenter context,
        final boolean isPoison,
        final boolean isFalme) {

        context.setState(new AnimatingState());
        context.getReadOnlyModel().decreasePlayerStamina(STAMINA_TO_REMOVE);
        context.getViewApi().updatePlayerStamina(
            context.getReadOnlyModel().getPlayerStamina());
        context.getViewApi().showInfo("Player Has used Long Range Attack");
        context.performLongRangeAttack(
            context.getReadOnlyModel().getPlayerPosition(), 1, isFalme, isPoison);
    }

    @Override
    public final void handleInfoInput(final CombatPresenter context) {
        context.performInfoAnimation();
    }

    @Override
    public final void handleBackInput(final CombatPresenter context) {
        context.performBackToMainMenu();
    }

    @Override
    public final void handleBagInput(final CombatPresenter context) {
        context.getViewApi().showInfo("Bag Selected");
    }

    @Override
    public final void handleRunInput(final CombatPresenter context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'handleRunInput'");
    }

    @Override
    public final void enterState(final CombatPresenter context) {
        context.getReadOnlyModel().setPlayerTurn(true);
        context.getViewApi().setAllMenusEnabled();
        context.getViewApi().showMainMenu();
        context.getViewApi().showInfo("Your Turn!");
    }

    @Override
    public final void exitState(final CombatPresenter context) {
        context.getViewApi().clearInfo();
    }

    @Override
    public final void handleAnimationComplete(final CombatPresenter context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'handleAnimationComplete'");
    }

    @Override
    public final void handleCurePoisonInput(final CombatPresenter context) {
        // this.curePoison.applyEffect(context.getModel());
    }

    @Override
    public final void handleAttackBuffInput(final CombatPresenter context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'handleAttackBuffInput'");
    }

    @Override
    public final void handleHealInput(final CombatPresenter context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'handleHealInput'");
    }

    @Override
    public final void handlePotionUsed(
        final PossibleUser user,
        final Item selectedPotion,
        final Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'handlePotionUsed'");
    }

}
