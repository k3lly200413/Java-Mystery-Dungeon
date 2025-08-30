package it.unibo.progetto_oop.Combat.StatePattern;

import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;

public class FuryBossState implements CombatState {

    /** Counter to track the number of boss attacks
     * performed in the fury state.
     */
    private int bossCounter;

    /** Current state of the boss, e.g., "NORMAL", "ENRAGED". */
    private String currentBossState;

    /** Constructor for FuryBossState. */
    public FuryBossState() {
        this.bossCounter = 0;
        this.currentBossState = "NORMAL";
    }


    @Override
    public final void handlePhysicalAttackInput(
            final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'handlePhysicalAttackInput'");
    }

    @Override
    public final void handleLongRangeAttackInput(
            final CombatController context,
            final boolean isPoison,
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
    public final void handleCurePoisonInput(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'handleCurePoisonInput'");
    }

    @Override
    public final void enterState(final CombatController context) {
        context.performEnemySuperAttack();
    }

    @Override
    public final void exitState(final CombatController context) {
        System.out.println("Exiting Fury Boss State");
    }

    @Override
    public final void handleAnimationComplete(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'handleAnimationComplete'");
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
        final Item selectedPotion) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'handlePotionUsed'");
    }
}
