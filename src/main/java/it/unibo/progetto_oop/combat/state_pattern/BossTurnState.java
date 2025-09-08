package it.unibo.progetto_oop.combat.state_pattern;

import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
/**
 * Boss' Turn State during combat.
 */
public class BossTurnState implements CombatState {

    /** Health percentage threshold to switch the boss state to ENRAGED. */
    private static final int BOSS_ENRAGED_THRESHOLD = 20;
    /** Interval of turns for the boss to perform the Death Ray attack. */
    private static final int DEATH_RAY_INTERVAL = 5;
    /** Interval of turns for the boss to charge a super attack. */
    private static final int SUPER_ATTACK_CHARGE_INTERVAL = 4;
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
        context.getView().showInfo("Starting Boss Turn");
        if (context.getModel().getEnemyHealth() < BOSS_ENRAGED_THRESHOLD
            && "NORMAL".equalsIgnoreCase(this.bossState)) {
            this.bossState = "ENRAGED";
            context.getView().showInfo("The Boss is now ENRAGED");
        }
        if ("ENRAGED".equalsIgnoreCase(this.bossState)) {
            context.setState(new AnimatingState());
            context.performEnemySuperAttack();
        } else {
            if (context.getModel().getBossTurnCounter() == 0) {
                context.setState(new AnimatingState());
                context.performEnemyPhysicalAttack();
                context.getModel().setBossTurn(false);
                context.getModel().increaseBossTurnCounter();
            } else if (context.getModel().getBossTurnCounter()
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
            } else {
                context.setState(new AnimatingState());
                context.performEnemyAttack();
                context.getModel().setBossTurn(false);
                context.getModel().increaseBossTurnCounter();
            }
        }
    }

    @Override
    public final void exitState(final CombatController context) {
        context.getView().showInfo("Finished Boss turn");
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
