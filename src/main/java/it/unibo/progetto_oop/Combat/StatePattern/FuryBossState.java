package it.unibo.progetto_oop.Combat.StatePattern;

import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class FuryBossState implements CombatState{

    private int bossCounter;
    private String curranteBossState;

    public FuryBossState(){
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

        if (boosHealthPercent < 0.5
        && this.curranteBossState.toUpperCase().equals("NORMAL")) {
            curranteBossState = "ENRAGED";
            context.getView().showInfo("The boss is now ENRAGED");
            // TODO: Change colour of Boss
        }
        if (this.curranteBossState.toUpperCase().equals("test")) {
            context.performEnemySuperAttack();
        } else {
            if (this.bossCounter % 5 == 0) {
                // context perform death ray attack
            } else if (this.bossCounter % 4 == 0) {
                context.getView().showInfo(
                    "The Boss is charging up his Super Attack!");
                // player Turn
            } else if (this.bossCounter % 3 == 0) {
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
}
