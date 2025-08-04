package it.unibo.progetto_oop.Combat.StatePattern;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.PotionStrategy.CurePoison;

public class PlayerTurnState implements CombatState{

    private CurePoison curePoison;

    public PlayerTurnState(){
        this.curePoison = new CurePoison();
    }

    @Override
    public void handlePhysicalAttackInput(CombatController context) {
        // TODO: Call controller and have it change state to animating state so this can be all done during the animation
        context.getView().setAllButtonsDisabled(); // Disable buttons during animation
        context.getView().clearInfo();
        // TODO: call model to remove 10 (placeholder) points of stamina
        context.getView().showInfo("Player Has used physical Attack");
        System.out.println("Debug Log: Requested Physicaln\nCurrent State: Player Turn State");
        context.performPlayerPhysicalAttack();
    }

    @Override
    public void handleLongRangeAttackInput(CombatController context, boolean isPoison, boolean isFalme) {
        // TODO: Call controller and have it change state to animating state so this can be all done during the animation
        context.getView().setAllButtonsDisabled(); // Disable buttons during animation
        context.getView().clearInfo();
        // TODO: call model to remove 20 (placeholder) points of stamina
        context.getView().showInfo("Player Has used Long Range Attack");
        System.out.println("Debug Log: Requested Long Range\nCurrent State: Player Turn State");
        context.performPlayerLongRangeAttack(isPoison, isFalme);
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
        System.out.println("PlayerTurnState: Bag action requested");
    }

    @Override
    public void handleRunInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleRunInput'");
    }

    @Override
    public void enterState(CombatController context) {
        context.getView().setAllButtonsEnabled();
        context.getView().showOriginalButtons();
        context.getView().showInfo("Your Turn!");
        System.out.println("------ Entering Player Turn State ------");
    }

    @Override
    public void exitState(CombatController context) {
        System.out.println("------ Exiting Player Turn State ------");
        context.getView().clearInfo();
    }

    @Override
    public void handleAnimationComplete(CombatController context){
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleAnimationComplete'");
    }

    @Override
    public void handleCurePoisonInput(CombatController context) {
        this.curePoison.applyEffect(context.getModel());
    }
    
}
