package it.unibo.progetto_oop.Combat.StatePattern;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;

public class PlayerTurnState implements CombatState{

    @Override
    public void handlePhysicalAttackInput(CombatController context) {
        // TODO: Call controller and have it change state to animating state so this can be all done during the animation
        context.getView().setButtonsEnabled(false); // Disable buttons during animation
        context.getView().clearInfo();
        context.getView().showInfo("Player Has used physical Attack");
        System.out.println("Debug Log: Requested Physicaln\nCurrent State: Player Turn State");
        context.performPlayerPhysicalAttack();
    }

    @Override
    public void handleLongRangeAttackInput(CombatController context, boolean isPoison) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleLongRangeAttackInput'");
    }

    @Override
    public void handleInfoInput(CombatController context) {
        context.performInfo();
    }

    @Override
    public void handleBackInput(CombatController context) {
        context.performBackToMainMenu();
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
        context.getView().setButtonsEnabled(true);
        context.getView().showOriginalButtons();
        context.getView().showInfo("Your Turn!");
        System.out.println("------ Entering Player Turn State ------");
    }

    @Override
    public void exitState(CombatController context) {
        System.out.println("------ Exiting Player Turn State ------");
        context.getView().clearInfo();
    }
    
}
