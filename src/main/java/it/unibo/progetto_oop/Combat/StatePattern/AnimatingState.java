package it.unibo.progetto_oop.Combat.StatePattern;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatView;

public class AnimatingState implements CombatState{

    private final boolean playerTurn = true;

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
        context.getView().showInfo("Entered Animating State!\nNo issues for now");
        System.out.println("------ Entered Animating State ------");
        context.getView().setButtonsEnabled(false);
    }

    @Override
    public void exitState(CombatController context) {
        System.out.println("------ Exeting Animating State ------");
        if (context.getModel().isPlayerTurn()) {
            context.getModel().setPlayerTurn(!this.playerTurn);
        }
        else{
            context.getModel().setPlayerTurn(this.playerTurn);
            // TODO: implement setState in Controller
            // context.setState(new PlayerturnState())
        }
    }

    @Override
    public void handleAnimationComplete(CombatController context) {
        System.out.println("Debug: Requested Handle Animation Complete");
        
        CombatModel model = context.getModel();
        
        boolean wasPlayerTurn = !model.isPlayerTurn();

        if (context.checkGameOver()) {
            // Create gameOverState
            return;
        }

        else if (wasPlayerTurn) {
            context.applyPostTurnEffects();
        }

        if (wasPlayerTurn) {
            model.setPlayerTurn(this.playerTurn);
            // Add EnemyTurnState
        }
        else {
            model.setPlayerTurn(!this.playerTurn);
            // Add setState to call PlayerturnState       
        }
    
    }

    @Override
    public void handleCurePoisonInput(CombatController context) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'handleCurePoisonInput'");
    }
    
}
