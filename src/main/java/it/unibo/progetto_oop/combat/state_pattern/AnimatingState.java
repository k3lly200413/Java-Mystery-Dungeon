package it.unibo.progetto_oop.combat.state_pattern;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatModel;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatView;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class AnimatingState implements CombatState {

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
        System.out.println("Exiting Animating State");
    }

    @Override
    public final void handleAnimationComplete(final CombatController context) {
        System.out.println("Debug: Requested Handle Animation Complete");

        CombatModel model = context.getModel();
        CombatView view = context.getView();

        boolean wasPlayerTurn = model.isPlayerTurn();

        if (wasPlayerTurn) {
            if (model.isEnemyPoisoned() && model.getEnemyHealth() > 0) {
                System.out.println("Applying poison damage to enemy.");
                view.showInfo("Enemy takes poison damage!");
                context.performPoisonEffectAnimation();
                model.decreaseEnemyHealth(
                    model.getPlayerPoisonPower()); // Apply damage
                view.updateEnemyHealth(
                    model.getEnemyHealth());          // Update bar
            }
            System.out.println(model.isEnemyPoisoned());
            System.out.println(model.getEnemyHealth() > 0);
        } else { // Enemy's turn just ended
            // Apply effects to PLAYER after enemy's turn
            if (model.isPlayerPoison() && model.getPlayerHealth() > 0) {
                System.out.println("Applying poison damage to player.");
                view.showInfo("Player takes poison damage!");
                context.performPoisonEffectAnimation();
                model.decreasePlayerHealth(model.getEnemyPoisonPower());
                view.updatePlayerHealth(model.getPlayerHealth());
            }
        }

        if (context.checkGameOver()) {
            context.setState(new GameOverState());
            return;
        }

        if (wasPlayerTurn) {
            if (!model.isEnemyPoisoned()) {
                context.getModel().setPlayerTurn(false);
                context.setState(new EnemyTurnState());
            }
        } else {
            if (!model.isPlayerPoison()) {
                context.getModel().setPlayerTurn(true);
                context.setState(new PlayerTurnState());
            }
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
     *
     * @param context The combat controller context.
     */
    public void handleBossDeathRayAttack(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(

            "Unimplemented method 'handleBossDeathRayAttack'");
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
