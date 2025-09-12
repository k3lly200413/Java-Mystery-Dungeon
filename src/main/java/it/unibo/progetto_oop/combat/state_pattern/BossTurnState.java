package it.unibo.progetto_oop.combat.state_pattern;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.player.adapter_pattern.PossibleUser;

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
        context.getViewApi().showInfo("Starting Boss Turn");
        if (context.getReadOnlyModel().getEnemyHealth() < BOSS_ENRAGED_THRESHOLD
            && "NORMAL".equalsIgnoreCase(this.bossState)) {
            this.bossState = "ENRAGED";
            context.getViewApi().showInfo("The Boss is now ENRAGED");
        }
        if ("ENRAGED".equalsIgnoreCase(this.bossState)) {
            context.setState(new AnimatingState());
            context.performEnemySuperAttack();
        } else {
            if (context.getReadOnlyModel().getBossTurnCounter() == 0) {
                context.setState(new AnimatingState());
                context.performEnemyPhysicalAttack();
                context.getReadOnlyModel().setBossTurn(false);
                context.getReadOnlyModel().increaseBossTurnCounter();
            } else if (context.getReadOnlyModel().getBossTurnCounter()
                % DEATH_RAY_INTERVAL == 0) {
                context.setState(new AnimatingState());
                context.performBossDeathRayAttack();
                context.getReadOnlyModel().setBossTurn(false);
                context.getReadOnlyModel().increaseBossTurnCounter();
            } else if (context.getReadOnlyModel().
            getBossTurnCounter() % SUPER_ATTACK_CHARGE_INTERVAL == 0) {
                context.getViewApi().showInfo(
                    "The Boss is charging up his Super Attack!");
                context.setState(new AnimatingState());
                context.performDeathAnimation(
                    context.getReadOnlyModel().getEnemyPosition(), true, () -> {
                    context.getCurrentState().handleAnimationComplete(context);
                });
                context.getReadOnlyModel().setBossTurn(false);
                context.getReadOnlyModel().increaseBossTurnCounter();
            } else {
                context.setState(new AnimatingState());
                context.performEnemyAttack();
                context.getReadOnlyModel().setBossTurn(false);
                context.getReadOnlyModel().increaseBossTurnCounter();
            }
        }
    }

    @Override
    public final void exitState(final CombatController context) {
        context.getViewApi().showInfo("Finished Boss turn");
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
        final PossibleUser user,
        final Item selectedPotion,
        final Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException(
"Unimplemented method 'handlePotionUsed'");
    }

}
