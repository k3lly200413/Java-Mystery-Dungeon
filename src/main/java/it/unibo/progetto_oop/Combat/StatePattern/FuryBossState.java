package it.unibo.progetto_oop.Combat.StatePattern;

import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class FuryBossState implements CombatState {
    /**
     * Counter for the number of attacks performed by the boss.
     */
    private int bossCounter;
    /**
     * Counter for the number of attacks performed by the boss.
     */
    private String curranteBossState;
    /**
     * Threshold for the boss to become enraged based on health percentage.
     */
    private static final Double ENRAGED_THRESHOLD = 0.5;
    /**
     * Threshold for the boss to perform a death ray attack.
     */
    private static final int DEATH_RAY_ATTACK_THRESHOLD = 5;
    /**
     * Threshold for the boss to charge up an attack before performing it.
     */
    private static final int CHARGE_UP_ATTACK_THRESHOLD = 4;

    /**
     * Threshold for the boss to perform a long-range attack.
     */
    private static final int LONG_RANGE_ATTACK_THRESHOLD = 3;

    /**
     * Constructor for FuryBossState.
     * Initializes the boss counter and sets the current boss state to "NORMAL".
     */
    public FuryBossState() {
        this.bossCounter = 0;
        this.curranteBossState = "NORMAL";
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
        CombatModel model = context.getModel();
        this.bossCounter++;
        double boosHealthPercent =
        model.getEnemyHealth()
        / model.getMaxHealth();

        if (boosHealthPercent < ENRAGED_THRESHOLD
        && this.curranteBossState.toUpperCase().equals("NORMAL")) {
            curranteBossState = "ENRAGED";
            context.getView().showInfo("The boss is now ENRAGED");
            // TODO: Change colour of Boss
        }
        if (this.curranteBossState.toUpperCase().equals("test")) {
            context.performEnemySuperAttack();
        } else {
            if (this.bossCounter % DEATH_RAY_ATTACK_THRESHOLD == 0) {
                // context perform death ray attack
            } else if (this.bossCounter % CHARGE_UP_ATTACK_THRESHOLD == 0) {
                context.getView().showInfo(
                    "The Boss is charging up his Super Attack!");
                // player Turn
            } else if (this.bossCounter % LONG_RANGE_ATTACK_THRESHOLD == 0) {
                // long range attack
            } else {
                // physical attack
            }
        }

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
        final Item selectedPotion,
        final Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
                "Unimplemented method 'handlePotionUsed'");
    }

    @Override
    public final void enter(final CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
"Unimplemented method 'enter'");
    }
}
