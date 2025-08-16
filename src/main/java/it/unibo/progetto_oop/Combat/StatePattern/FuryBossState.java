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
    public void handlePhysicalAttackInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePhysicalAttackInput'");
    }

    @Override
    public void handleLongRangeAttackInput(CombatController context, boolean isPoison, boolean isFlame) {
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
        CombatModel model = context.getModel();
        this.bossCounter++;
        double boosHealthPercent = model.getEnemyHealth() / model.getMaxHealth();

        if ( boosHealthPercent < 0.5 && this.curranteBossState.toUpperCase().equals("NORMAL")){
            curranteBossState = "ENRAGED";
            context.getView().showInfo("The boss is now ENRAGED");
            // TODO: Change colour of Boss
        }
        if (this.curranteBossState.toUpperCase().equals("test")){
            context.performEnemySuperAttack();
        }
        else {
            if (this.bossCounter % 5 == 0) {
                // context perform death ray attack
            }
            else if (this.bossCounter % 4 == 0) {
                context.getView().showInfo("The Boss is charging up his Super Attack!");
                // player Turn
            }
            else if (this.bossCounter % 3 == 0) {
                // long range attack
            }
            else {
                // physical attack
            }
        }

    }

    @Override
    public void exitState(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exitState'");
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
