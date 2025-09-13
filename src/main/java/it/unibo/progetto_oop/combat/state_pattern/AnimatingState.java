package it.unibo.progetto_oop.combat.state_pattern;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatPresenter;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatViewApi;
import it.unibo.progetto_oop.combat.mvc_pattern.ReadOnlyCombatModel;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.player.adapter_pattern.PossibleUser;

/**
 * Class representing the Animating State in the combat state pattern.
 * In this state, the system is handling animations and does not accept
 * user inputs for actions.
 */

public class AnimatingState implements CombatState {

    @Override
    public final void handlePhysicalAttackInput(
        final CombatPresenter context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(

            "Unimplemented method 'handlePhysicalAttackInput'");
    }

    @Override
    public final void handleLongRangeAttackInput(
        final CombatPresenter context, final boolean isPoison,
            final boolean isFlame) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(

            "Unimplemented method 'handleLongRangeAttackInput'");
    }

    @Override
    public final void handleInfoInput(final CombatPresenter context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(

            "Unimplemented method 'handleInfoInput'");
    }

    @Override
    public final void handleBackInput(final CombatPresenter context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(

            "Unimplemented method 'handleBackInput'");
    }

    @Override
    public final void handleBagInput(final CombatPresenter context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(

            "Unimplemented method 'handleBagInput'");
    }

    @Override
    public final void handleRunInput(final CombatPresenter context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(

            "Unimplemented method 'handleRunInput'");
    }

    @Override
    public final void enterState(final CombatPresenter context) {
        context.getViewApi().showInfo(
            "Entered Animating State!\nNo issues for now");
        context.getViewApi().setAllMenusDisabled();
    }

    /**
     * No actions required when exiting the AnimatingState.
     */
    @Override
    public final void exitState(final CombatPresenter context) {
        context.getViewApi().showInfo("Animation Completed");
    }

    @Override
    public final void handleAnimationComplete(final CombatPresenter context) {

        final ReadOnlyCombatModel model = context.getReadOnlyModel();
        final CombatViewApi view = context.getViewApi();

        final boolean wasPlayerTurn = model.isPlayerTurn();

        if (wasPlayerTurn) {
            if (model.isEnemyPoisoned() && model.getEnemyHealth() > 0) {
                view.showInfo("Enemy takes poison damage!");
                context.performPoisonEffectAnimation();
                /*model.decreaseEnemyHealth(
                    model.getPlayerPoisonPower()); // Apply damage
                view.updateEnemyHealth(
                    model.getEnemyHealth());          // Update bar*/
            }
        } else { // Enemy's turn just ended
            // Apply effects to PLAYER after enemy's turn
            if (model.isPlayerPoison() && model.getPlayerHealth() > 0) {
                view.showInfo("Player takes poison damage!");
                context.performPoisonEffectAnimation();
                //model.decreasePlayerHealth(model.getEnemyPoisonPower());
                //view.updatePlayerHealth(model.getPlayerHealth());
                // model.decreasePlayerHealth(model.getEnemyPoisonPower());
                // view.updatePlayerHealth(model.getPlayerHealth());
            }
        }

        if (!context.checkGameOver()) {
            if (wasPlayerTurn && !model.isEnemyPoisoned()) {
                context.getReadOnlyModel().setPlayerTurn(false);
                context.setState(context.getReadOnlyModel().getEnemyState());
            } else if (!wasPlayerTurn && !model.isPlayerPoison()) {
                context.getReadOnlyModel().setPlayerTurn(true);
                context.setState(new PlayerTurnState());
            }
        }
    }

    @Override
    public final void handleCurePoisonInput(final CombatPresenter context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(

            "Unimplemented method 'handleCurePoisonInput'");
    }

    /**
     * Method to handle the boss death ray attack.
     *
     * @param context The combat controller context.
     */
    public void handleBossDeathRayAttack(final CombatPresenter context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(

            "Unimplemented method 'handleBossDeathRayAttack'");
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

    /** */
    @Override
    public void handlePotionUsed(
        final PossibleUser user,
        final Item selectedPotion,
        final Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(

            "Unimplemented method 'handlePotionUsed'");
    }

}
