package it.unibo.progetto_oop.combat.state_pattern;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatPresenter;
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
        final CombatPresenter context) {
        // Method should not be called
    }

    @Override
    public final void handleLongRangeAttackInput(
        final CombatPresenter context, final boolean isPoison,
            final boolean isFalme) {
        // Method should not be called
    }

    @Override
    public final void handleInfoInput(final CombatPresenter context) {
        // Method should not be called
    }

    @Override
    public final void handleBackInput(final CombatPresenter context) {
        // Method should not be called
    }

    @Override
    public final void handleBagInput(final CombatPresenter context) {
        // Method should not be called
    }

    @Override
    public final void handleRunInput(final CombatPresenter context) {
        // Method should not be called
    }

    @Override
    public final void handleCurePoisonInput(final CombatPresenter context) {
        // Method should not be called
    }

    // Constants for Boss logic
    @Override
    public final void enterState(final CombatPresenter context) {
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
    public final void exitState(final CombatPresenter context) {
        context.getViewApi().showInfo("Finished Boss turn");
    }

    @Override
    public final void handleAnimationComplete(final CombatPresenter context) {
        // Method should not be called
    }

    @Override
    public final void handleAttackBuffInput(final CombatPresenter context) {
        // Method should not be called
    }

    @Override
    public final void handleHealInput(final CombatPresenter context) {
        // Method should not be called
    }

    @Override
    public final void handlePotionUsed(
        final PossibleUser user,
        final Item selectedPotion,
        final Player player) {
        // Method should not be called
    }

}
