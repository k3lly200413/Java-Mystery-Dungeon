package it.unibo.progetto_oop.Combat.StatePattern;

import javax.swing.Timer;

import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class EnemyTurnState implements CombatState {

    private static final int ENEMY_ACTION_DELAY = 500; // Delay in milliseconds before enemy action

    @Override
    public void enterState(CombatController context) {
        // Logic for entering enemy turn state
        System.out.println("Entering enemy turn state.");
        CombatModel model = context.getModel();

        // Check if this is a boss turn AND if its special attack sequence is active
        // context.setState(new BossState());
        System.out.println("Boss Turn : " + model.isBossTurn());
        if (model.isBossTurn() && model.getBossAttackCounter() < model.getMaxBossHit()) {
            // --- The boss performs another attack in its sequence ---
            model.increaseBossAttackCounter(); // We are performing hit #1, #2, or #3
            System.out.println("Boss Sequence: Preparing hit #" + model.getBossAttackCounter());

            // Use the controller's helper to perform a delayed action
            context.performDelayedEnemyAction(ENEMY_ACTION_DELAY, () -> {
                // This is the action to perform after the delay.
                // In this case, it's always the super attack.
                context.performEnemySuperAttack();
            });
            // The state machine will cycle through AnimatingState and come back here
            // for the next hit if the sequence isn't over.
        }
        else {
            if (model.isBossTurn() && model.getBossAttackCounter() > 0){
                System.out.println("\n>>> Starting Boss Attack");
                model.clearBossAttackCount();
                model.setBossTurn(false);
                context.setState(new PlayerTurnState());
                model.setPlayerTurn(true);
            }
            else{
                context.stopAnimationTimer();
                System.out.println("No Boss Attack");
                model.setBossTurn(false);
                Timer enemyDelay = new Timer(ENEMY_ACTION_DELAY, e -> {
                    // Ensure we are still in the EnemyTurnState before acting
                    if (context.getCurrentState() == this) {
                        System.out.println("Enemy choosing action...");
                        context.setState(new AnimatingState()); // Assume enemy action leads to animation
                        // context.performEnemySuperAttack();
                        context.performEnemyAttack(); // Renamed controller method
                    }
                });
                enemyDelay.setRepeats(false);
                enemyDelay.start();
            }
        }
    }

    @Override
    public void exitState(CombatController context) {
        // Logic for exiting enemy turn state
        context.getModel().setPlayerTurn(true);
        // context.getView().updatePlayerTurnView();
    }

    @Override
    public void handlePhysicalAttackInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePhysicalAttackInput'");
    }

    @Override
    public void handleLongRangeAttackInput(CombatController context, boolean isPoison, boolean isFlame) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleLongRangeAttackInput'");
    }

    @Override
    public void handleInfoInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleInfoInput'");
    }

    @Override
    public void handleBackInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleBackInput'");
    }

    @Override
    public void handleBagInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleBagInput'");
    }

    @Override
    public void handleRunInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleRunInput'");
    }

    @Override
    public void handleAttackBuffInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleAttackBuffInput'");
    }

    @Override
    public void handleHealInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleHealInput'");
    }

    @Override
    public void handlePotionUsed(CombatController context, Item selectedPotion, Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePotionUsed'");
    }

    @Override
    public void handleCurePoisonInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleCurePoisonInput'");
    }

    @Override
    public void handleAnimationComplete(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleAnimationComplete'");
    }

}
