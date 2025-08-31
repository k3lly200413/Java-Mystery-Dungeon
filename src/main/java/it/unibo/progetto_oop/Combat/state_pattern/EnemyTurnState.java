package it.unibo.progetto_oop.combat.state_pattern;

import javax.swing.Timer;

import it.unibo.progetto_oop.combat.Inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatModel;

public class EnemyTurnState implements CombatState {

    /** Delay in milliseconds before enemy action. */
    private static final int ENEMY_ACTION_DELAY = 500;

    /**
     * Handles the transition logic when entering the enemy turn state.
     *
     * If the boss is active and still within its attack sequence,
     * it performs a delayed super attack. Otherwise, it checks if the boss
     * has completed its sequence and returns control to the player,
     * or performs a standard enemy attack after a short delay.
     *
     * @param context the CombatController providing access
     *                to the model, view, and state transitions
     */
    @Override
    public void enterState(final CombatController context) {
        // Logic for entering enemy turn state
        System.out.println("Entering enemy turn state.");
        CombatModel model = context.getModel();

        // Check if this is a boss turn
        // AND if its special attack sequence is active
        // context.setState(new BossState());
        System.out.println("Boss Turn : " + model.isBossTurn());
        if (model.isBossTurn()
        && model.getBossAttackCounter() < model.getMaxBossHit()) {
            // --- The boss performs another attack in its sequence ---
            // We are performing hit #1, #2, or #3
            model.increaseBossAttackCounter();
            System.out.println("Boss Sequence: Preparing hit #"
            + model.getBossAttackCounter());

            // Use the controller's helper to perform a delayed action
            context.performDelayedEnemyAction(ENEMY_ACTION_DELAY, () -> {
                // This is the action to perform after the delay.
                // In this case, it's always the super attack.
                context.performEnemySuperAttack();
            });
            // The state machine will
            // cycle through AnimatingState and come back here
            // for the next hit if the sequence isn't over.
        } else {
            if (model.isBossTurn() && model.getBossAttackCounter() > 0) {
                System.out.println("\n>>> Starting Boss Attack");
                model.clearBossAttackCount();
                model.setBossTurn(false);
                context.setState(new PlayerTurnState());
                model.setPlayerTurn(true);
            } else {
                // context.stopAnimationTimer();
                System.out.println("No Boss Attack");
                model.setBossTurn(false);
                Timer enemyDelay = new Timer(ENEMY_ACTION_DELAY, e -> {
                    // Ensure we are still in the EnemyTurnState before acting
                    if (context.getCurrentState() == this) {
                        System.out.println("Enemy choosing action...");
                        // Assume enemy action leads to animation
                        context.setState(new AnimatingState());
                        // context.performEnemySuperAttack();
                        // Renamed controller method
                        context.performEnemyAttack();
                    }
                });
                enemyDelay.setRepeats(false);
                enemyDelay.start();
            }
        }
    }

    @Override
    public void exitState(final CombatController context) {
        // Logic for exiting enemy turn state
        // context.getModel().setPlayerTurn(true);
        // context.getView().updatePlayerTurnView();
    }

    @Override
    public void handlePhysicalAttackInput(final CombatController context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleLongRangeAttackInput(final CombatController context,
    final boolean isPoison, final boolean isFlame) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleInfoInput(final CombatController context) {
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
    public void handleRunInput(final CombatController context) {
        // TODO Auto-generated method stub
    }

    @Override
    public void handleAttackBuffInput(final CombatController context) {
        // TODO Auto-generated method stub
    }

    @Override
    public void handleHealInput(final CombatController context) {
        // TODO Auto-generated method stub
    }

    @Override
    public void handlePotionUsed(final CombatController context,
    final Item selectedPotion) {
        // TODO Auto-generated method stub
    }

    @Override
    public void handleCurePoisonInput(final CombatController context) {
        // TODO Auto-generated method stub
    }

    @Override
    public void handleAnimationComplete(final CombatController context) {
        // TODO Auto-generated method stub
    }

}
