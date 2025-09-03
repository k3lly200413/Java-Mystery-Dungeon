package it.unibo.progetto_oop.combat.StatePattern;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class BossTurnState implements CombatState {

    private static final int ENEMY_ACTION_DELAY = 500; // ms delay before enemy acts
    private static final int TOTAL_ATTACKS_IN_SEQUENCE = 3;
    private int bossHealthPercent;
    private String bossState = "NORMAL";

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
            final boolean isFalme) {
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
        System.out.println("\nBoss State: Entering Boss Turn State\n");
        this.bossHealthPercent =
        (context.getModel().getEnemyHealth()
        / context.getModel().getMaxHealth()) * 100;

        if (this.bossHealthPercent < 50
            && this.bossState.toUpperCase().equals("NORMAL")) {
            this.bossState = "ENRAGED";
            context.getView().showInfo("The Boss is now ENRAGED");
            // TODO: Change colour of Boss
        }
        if (this.bossState.toUpperCase().equals("ENRAGED")) {
            context.performEnemySuperAttack();
        } else {
            if (context.getModel().getBossTurnCounter() % 5 == 0) {
                context.setState(new AnimatingState());
                context.performBossDeathRayAttack();
                context.getModel().setBossTurn(false);
                context.getModel().increaseBossTurnCounter();
            } else if (context.getModel().getBossTurnCounter() % 4 == 0) {
                context.getView().showInfo(
                    "The Boss is charging up his Super Attack!");
                context.setState(new AnimatingState());
                context.performDeathAnimation(
                    context.getModel().getEnemyPosition(), true, () -> {
                    context.getCurrentState().handleAnimationComplete(context);
                });
                context.getModel().setBossTurn(false);
                context.getModel().increaseBossTurnCounter();
            } else if (context.getModel().getBossTurnCounter() % 3 == 0) {
                context.setState(new AnimatingState());
                context.performEnemyAttack();
                context.getModel().setBossTurn(false);
                context.getModel().increaseBossTurnCounter();
            } else {
                context.setState(new AnimatingState());
                context.performEnemyPhysicalAttack();
                context.getModel().setBossTurn(false);
                context.getModel().increaseBossTurnCounter();
            }
        }

    }

    @Override
    public final void exitState(final CombatController context) {
        System.out.println("\nBoss State: Exiting Boss Turn State\n");
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
