package it.unibo.progetto_oop.combat.state_pattern;

import it.unibo.progetto_oop.combat.Inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.Overworld.Player.Player;

/**
 * Boss' Turn State during Combat.
 */
public class BossTurnState implements CombatState {

    /** Health percentage threshold to switch the boss state to ENRAGED. */
    private static final int BOSS_ENRAGED_THRESHOLD = 50;
    /** Interval of turns for the boss to perform the Death Ray attack. */
    private static final int DEATH_RAY_INTERVAL = 5;
    /** Interval of turns for the boss to charge a super attack. */
    private static final int SUPER_ATTACK_CHARGE_INTERVAL = 4;
    /** Interval of turns for the boss to perform a standard enemy attack. */
    private static final int STANDARD_ATTACK_INTERVAL = 3;
    /** Conversion factor to percentage values (100%). */
    private static final int PERCENT_CONVERSION = 100;

    /** Indicates the boss health percentage. */
    private int bossHealthPercent;
    /** Indicates the boss state. */
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

    // Constants for Boss logic
    @Override
    public final void enterState(final CombatController context) {
        System.out.println("\nBoss State: Entering Boss Turn State\n");
        this.bossHealthPercent =
            (context.getModel().getEnemyHealth()
            / context.getModel().getMaxHealth()) * PERCENT_CONVERSION;

        if (this.bossHealthPercent < BOSS_ENRAGED_THRESHOLD
            && this.bossState.toUpperCase().equals("NORMAL")) {
            this.bossState = "ENRAGED";
            context.getView().showInfo("The Boss is now ENRAGED");
        }
        if (this.bossState.toUpperCase().equals("ENRAGED")) {
            context.performEnemySuperAttack();
        } else {
            if (context.getModel().getBossTurnCounter()
                % DEATH_RAY_INTERVAL == 0) {
                context.setState(new AnimatingState());
                context.performBossDeathRayAttack();
                context.getModel().setBossTurn(false);
                context.getModel().increaseBossTurnCounter();
            } else if (context.getModel().
            getBossTurnCounter() % SUPER_ATTACK_CHARGE_INTERVAL == 0) {
                context.getView().showInfo(
                    "The Boss is charging up his Super Attack!");
                context.setState(new AnimatingState());
                context.performDeathAnimation(
                    context.getModel().getEnemyPosition(), true, () -> {
                    context.getCurrentState().handleAnimationComplete(context);
                });
                context.getModel().setBossTurn(false);
                context.getModel().increaseBossTurnCounter();
            } else if (context.getModel().
            getBossTurnCounter() % STANDARD_ATTACK_INTERVAL == 0) {
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
