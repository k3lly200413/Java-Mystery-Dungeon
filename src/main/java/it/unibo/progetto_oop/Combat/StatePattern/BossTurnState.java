package it.unibo.progetto_oop.Combat.StatePattern;

import javax.swing.Timer;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;

public class BossTurnState implements CombatState{

    private static final int ENEMY_ACTION_DELAY = 500; // ms delay before enemy acts
    private int attackSequenceCounter = 0;
    private static final int TOTAL_ATTACKS_IN_SEQUENCE = 3;

    @Override
    public void handlePhysicalAttackInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePhysicalAttackInput'");
    }

    @Override
    public void handleLongRangeAttackInput(CombatController context, boolean isPoison) {
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
    public void handleCurePoisonInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleCurePoisonInput'");
    }

    @Override
    public void enterState(CombatController context) {
        System.out.println("\nBoss State: Entering Boss Turn State\n");

        if (this.attackSequenceCounter < TOTAL_ATTACKS_IN_SEQUENCE) {
            this.attackSequenceCounter++;

            System.out.println("DEBUG: Boss Attack Sequence: Performing hit # " + this.attackSequenceCounter);

            Timer singleAttackTimer = new Timer(ENEMY_ACTION_DELAY, e -> {
                context.performEnemySuperAttack();
            });
            singleAttackTimer.setRepeats(false);
            singleAttackTimer.start();
        }

    }

    @Override
    public void exitState(CombatController context) {
        System.out.println("\nBoss State: Exiting Boss Turn State\n");
    }

    @Override
    public void handleAnimationComplete(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleAnimationComplete'");
    }
    
}
