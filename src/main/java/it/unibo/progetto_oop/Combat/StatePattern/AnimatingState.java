package it.unibo.progetto_oop.Combat.StatePattern;

import javax.swing.Timer;

import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatView;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class AnimatingState implements CombatState{

    private final boolean playerTurn = true;

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
    public void enterState(CombatController context) {
        context.getView().showInfo("Entered Animating State!\nNo issues for now");
        System.out.println("------ Entered Animating State ------");
        context.getView().setAllButtonsDisabled();
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

        if (wasPlayerTurn) {
            // if (context.getModel().isBossTurn()){
            //     // set new BossTurnState()
            // }
            // else{
                context.applyPostTurnEffects();
            //     model.setPlayerTurn(this.playerTurn);
            // }
        }

        if (context.checkGameOver()) {
            // Create gameOverState
            return;
        }

        else if (wasPlayerTurn) {
            context.applyPostTurnEffects();
        }

        if (wasPlayerTurn) {
            // TODO: Shold be able to fix with EnemyTurnState
            model.setPlayerTurn(this.playerTurn);
            Timer enemyDeley = new Timer(500, e -> {
                context.enemyTurn();
            });
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

    public void handleBossDeathRayAttack(CombatController context) {
        
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
