package it.unibo.progetto_oop.combat.state_pattern;

import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.combat.combat_builder.RedrawContext;
import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;

public class GameOverState implements  CombatState {

    /**
     *
     * @param context Istance of the controller
     *
     * This method is called when entering a combat state.
     */
    @Override
    public void enterState(final CombatController context) {
        final RedrawContext defaultRedraw = new RedrawContext.Builder()
        .player(context.getModel().getPlayerPosition())
        .enemy(context.getModel().getEnemyPosition())
        .flame(context.getModel().getAttackPosition())
        .drawPlayer(true)
        .drawEnemy(true)
        .playerRange(2)
        .enemyRange(2)
        .setIsGameOver(context.getModel().isGameOver())
        .whoDied(context.getModel().getWhoDied())
        .build();
        context.getView().redrawGrid(defaultRedraw);

        if (context.getModel().getPlayerHealth() <= 0) {
            context.getView().showGameOver(() -> {
            // TODO: qui in futuro resetta il Model e cambia stato, es:
            // context.restartMatch();
            // context.setState(new PlayerTurnState());
        });
        } else if (context.getModel().getEnemyHealth() <= 0) {
            context.getView().showInfo("You Win! Closing in 3 seconds...");
            context.getView().close();
        }
        
    }

    /**
     *
     * @param context Istance of the controller
     *
     * This method is called when exiting a combat state.
     */
    @Override
    public void exitState(final CombatController context) {
        context.getView().showInfo("Exiting Game Over State");
    }

    /**
     *
     * @param context Istance of the controller
     *
     * This method is called when an animation is complete during combat.
     */
    @Override
    public void handleAnimationComplete(final CombatController context) {
        context.getView().showInfo("Animation Complete in Game Over State");
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
