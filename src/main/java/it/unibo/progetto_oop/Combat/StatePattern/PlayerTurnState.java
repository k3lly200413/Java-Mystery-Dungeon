package it.unibo.progetto_oop.Combat.StatePattern;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;

public class PlayerTurnState implements CombatState{

    @Override
    public void handlePhysicalAttackInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handlePhysicalAttackInput'");
    }

    @Override
    public void handleLongRangeAttackInput(CombatController context, boolean isPoison) {
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
    public void enterState(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'enterState'");
    }

    @Override
    public void exitState(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exitState'");
    }

    @Override
    public void handleAnimationComplete(CombatController context){
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleAnimationComplete'");
    }
    
}
