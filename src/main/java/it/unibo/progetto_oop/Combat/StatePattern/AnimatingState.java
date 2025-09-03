package it.unibo.progetto_oop.combat.StatePattern;

import javax.swing.Timer;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatModel;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class AnimatingState implements CombatState {
    /**
     * Indicates if it is the player's turn.
     */
    private final boolean playerTurn = true;
    /**
     * Delay for the animation in milliseconds.
     */
    private static final int ANIMATION_DELAY = 500;

    @Override
    public final void handlePhysicalAttackInput(
        final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(

            "Unimplemented method 'handlePhysicalAttackInput'");
    }

    @Override
    public final void handleLongRangeAttackInput(
        final CombatController context, final boolean isPoison,
            final boolean isFlame) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(

            "Unimplemented method 'handleLongRangeAttackInput'");
    }

    @Override
    public final void handleInfoInput(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(

            "Unimplemented method 'handleInfoInput'");
    }

    @Override
    public final void handleBackInput(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(

            "Unimplemented method 'handleBackInput'");
    }

    @Override
    public final void handleBagInput(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(

            "Unimplemented method 'handleBagInput'");
    }

    @Override
    public final void handleRunInput(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(

            "Unimplemented method 'handleRunInput'");
    }

    @Override
    public final void enterState(final CombatController context) {
        context.getView().showInfo(
            "Entered Animating State!\nNo issues for now");
        System.out.println("------ Entered Animating State ------");
        context.getView().setAllButtonsDisabled();
    }

    @Override
    public final void exitState(final CombatController context) {
        System.out.println("------ Exeting Animating State ------");
        if (context.getModel().isPlayerTurn()) {
            context.getModel().setPlayerTurn(!this.playerTurn);
        } else {
            context.getModel().setPlayerTurn(this.playerTurn);
            // TODO: implement setState in Controller
            // context.setState(new PlayerturnState())
        }
    }

    @Override
    public final void handleAnimationComplete(final CombatController context) {
        System.out.println("Debug: Requested Handle Animation Complete");

        CombatModel model = context.getModel();

        boolean wasPlayerTurn = !model.isPlayerTurn();

        if (wasPlayerTurn) {
            context.applyPostTurnEffects();
            // TODO: Check what wasPlayerTurn is
            // model.setPlayerTurn(false); // Flip the turn flag
            // model.setBossTurn(false);
            // context.setState(new EnemyTurnState());
            // TODO: make applypostTurnEffects() generic
        }

        if (context.checkGameOver()) {
            // Create gameOverState
            return;
        }

        if (wasPlayerTurn) {
            // TODO: Check if enemyTurnState sets model flags
            context.getModel().setPlayerTurn(false);
            context.setState(new EnemyTurnState());
        } else {
            // TODO: Check if playerturnstate sets model flags
            context.getModel().setPlayerTurn(true);
            context.setState(new PlayerTurnState());
        }

    }

    @Override
    public final void handleCurePoisonInput(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(

            "Unimplemented method 'handleCurePoisonInput'");
    }
    /**
     * Method to handle the boss death ray attack.
     * @param context The combat controller context.
     */
    public void handleBossDeathRayAttack(final CombatController context) { }

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
