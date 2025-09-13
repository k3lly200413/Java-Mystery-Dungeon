package it.unibo.progetto_oop.combat.state_pattern;

import javax.swing.Timer;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatPresenter;
import it.unibo.progetto_oop.combat.mvc_pattern.ReadOnlyCombatModel;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.player.adapter_pattern.PossibleUser;

/**
 * Class representing the Enemy Turn State in the combat state pattern.
 * In this state, the enemy (or boss) takes its turn to perform actions
 * against the player.
 */

public class EnemyTurnState implements CombatState {

    /** Delay in milliseconds before enemy action. */
    private static final int ENEMY_ACTION_DELAY = 500;

    /**
     * Handles the transition logic when entering the enemy turn state.
     * If the boss is active and still within its attack sequence,
     * it performs a delayed super attack. Otherwise, it checks if the boss
     * has completed its sequence and returns control to the player,
     * or performs a standard enemy attack after a short delay.
     *
     * @param context the CombatController providing access
     *                to the model, view, and state transitions
     */
    @Override
    public void enterState(final CombatPresenter context) {
        final ReadOnlyCombatModel model = context.getReadOnlyModel();

        if (model.isBossTurn()
        && model.getBossAttackCounter() < model.getMaxBossHit()) {
            model.increaseBossAttackCounter();

            context.performDelayedEnemyAction(ENEMY_ACTION_DELAY, () -> {
                context.performEnemySuperAttack();
            });
        } else {
            if (model.isBossTurn() && model.getBossAttackCounter() > 0) {
                model.clearBossAttackCount();
                model.setBossTurn(false);
                context.setState(new PlayerTurnState());
                model.setPlayerTurn(true);
            } else {
                model.setBossTurn(false);
                final Timer enemyDelay = new Timer(ENEMY_ACTION_DELAY, e -> {
                    if (context.getCurrentState().equals(this)) {
                        context.setState(new AnimatingState());
                        context.performEnemyAttack();
                    }
                });
                enemyDelay.setRepeats(false);
                enemyDelay.start();
            }
        }
    }

    @Override
    public void exitState(final CombatPresenter context) {
        // Method should not be called
    }

    @Override
    public void handlePhysicalAttackInput(final CombatPresenter context) {
        // Method should not be called
    }

    @Override
    public void handleLongRangeAttackInput(final CombatPresenter context,
    final boolean isPoison, final boolean isFlame) {
        // Method should not be called
    }

    @Override
    public void handleInfoInput(final CombatPresenter context) {
        // Method should not be called
    }

    @Override
    public void handleBackInput(final CombatPresenter context) {
        // Method should not be called
    }

    @Override
    public void handleBagInput(final CombatPresenter context) {
        // Method should not be called
    }

    @Override
    public void handleRunInput(final CombatPresenter context) {
        // Method should not be called
    }

    @Override
    public void handleAttackBuffInput(final CombatPresenter context) {
        // Method should not be called
    }

    @Override
    public void handleHealInput(final CombatPresenter context) {
        // Method should not be called
    }

    @Override
    public void handlePotionUsed(final PossibleUser user,
    final Item selectedPotion, final Player player) {
        // Method should not be called
    }

    @Override
    public void handleCurePoisonInput(final CombatPresenter context) {
        // Method should not be called
    }

    @Override
    public void handleAnimationComplete(final CombatPresenter context) {
        // Method should not be called
    }

}
