package it.unibo.progetto_oop.Combat.MVC_Pattern;

import it.unibo.progetto_oop.Combat.Position.Position;

// import javax.swing.Timer;

public class CombatController {
    private CombatModel model;
    private CombatView view;

    public CombatController(CombatModel model, CombatView view){
        this.model = model;
        this.view = view;
        this.redrawView();
        view.setVisible(true);
    }

    private void redrawView(){
        view.redrawGrid(model.getPlayerPosition(), model.getEnemyPosition(), new Position(0, 0), true, true, false, false, 1, 1);
    }
    
    /*private void performAttack() {
        
        Timer playerTimer = new Timer(100, e -> {
            model.movePlayer(1, 0);
            if (model.areNeighbours(model.getEnemyPosition())) {
                model.decreaseEnemyHealth();
                ((Timer) e.getSource()).stop();
            }
            view.redraw(model.getCells(), model.getPlayerPosition(), model.getEnemyPosition());
        });
        playerTimer.start();
    }*/

}
