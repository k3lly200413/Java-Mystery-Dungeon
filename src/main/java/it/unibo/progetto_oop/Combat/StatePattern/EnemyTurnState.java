package it.unibo.progetto_oop.Combat.StatePattern;

public class EnemyTurnState implements CombatState {

    private final CombatController controller;

    public EnemyTurnState(CombatController controller) {
        this.controller = controller;
    }

    @Override
    public void enter() {
        // Logic for entering enemy turn state
        controller.setEnemyActionTimer();
        controller.getModel().setPlayerTurn(false);
        controller.getView().updateEnemyTurnView();
    }

    @Override
    public void exit() {
        // Logic for exiting enemy turn state
        controller.getModel().setPlayerTurn(true);
        controller.getView().updatePlayerTurnView();
    }

    @Override
    public void handleInput(String input) {
        // Handle any input during enemy turn if necessary
    }

}
