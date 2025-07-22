package it.unibo.progetto_oop.Combat.MVC_Pattern;

import javax.swing.Timer;

public class CombatController {
    private CombatModel model;
    private CombatView view;

    public CombatController(CombatModel model, CombatView view){
        this.model = model;
        this.view = view;
        view.createGrid(model.getCells());
        view.setVisible(true);
    }
    
    private void performAttack() {
        
        Timer playerTimer = new Timer(100, e -> {
            model.movePlayer(1, 0);
            if (model.areNeighbours(model.getEnemyPosition())) {
                model.decreaseEnemyHealth();
                ((Timer) e.getSource()).stop();
            }
            view.redraw(model.getCells(), model.getPlayerPosition(), model.getEnemyPosition());
        });
        playerTimer.start();
    }

}
