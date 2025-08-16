package it.unibo.progetto_oop.Combat.StatePattern;

import javax.swing.Timer;

import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class BossTurnState implements CombatState{

    private static final int ENEMY_ACTION_DELAY = 500; // ms delay before enemy acts
    private int attackSequenceCounter = 0;
    private static final int TOTAL_ATTACKS_IN_SEQUENCE = 3;
    private int bossHealthPercent;
    private String bossState = "NORMAL";

    @Override
    public void handlePhysicalAttackInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePhysicalAttackInput'");
    }

    @Override
    public void handleLongRangeAttackInput(CombatController context, boolean isPoison, boolean isFalme) {
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
        this.bossHealthPercent = (context.getModel().getEnemyHealth() / context.getModel().getMaxHealth()) * 100;

        if (this.bossHealthPercent < 50 && this.bossState.toUpperCase().equals("NORMAL")) {
            this.bossState = "ENRAGED";
            context.getView().showInfo("The Boss is now ENRAGED");
            // TODO: Change colour of Boss
        }
        if (this.bossState.toUpperCase().equals("ENRAGED")){
            context.performEnemySuperAttack();
            this.attackSequenceCounter = 0;
        }
        else {
            if (this.attackSequenceCounter % 5 == 0){
                // Set new Animating State
                context.performBossDeathRayAttack();
            }
            else if (this.attackSequenceCounter % 4 == 0) {
                context.getView().showInfo("The Boss is charging up his Super Attack!");
                // animating State
                context.performDeathAnimation(context.getModel().getEnemyPosition(), true, () -> {
                    // handleAnimationComplete
                });
            }
            else if (this.attackSequenceCounter % 3 == 0) {
                // set animating State
                // enemy long range attack
            }
            else {
                // animating state
                // enemy physical attack
            }
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
    public void enter(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enter'");
    }
    
}
