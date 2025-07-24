package it.unibo.progetto_oop.Combat.MVC_Pattern;

import it.unibo.progetto_oop.Combat.Position.Position;

// import javax.swing.Timer;

/**
 * Controller class in Model View Controller Pattern
 * 
 * @author Kelly.applebee@studio.unibo.it
 */
public class CombatController {
    private final CombatModel model;
    private final CombatView view;

    /**
     * Contructor of CombatController takes in both model and view
     * <p>
     * @param model Model which holds information necessary to controller
     * @param view  View which displays on screen information taken from controlller
     * 
     * 
     * @author kelly.applebee@studio.unibo.it
     */
    public CombatController(CombatModel model, CombatView view){
        this.model = model;
        this.view = view;
        this.view.setHealthBarMax(model.getMaxHealth());
        this.view.updatePlayerHealth(model.getMaxHealth());
        this.view.updateEnemyHealth(model.getMaxHealth());
        this.redrawView();
    }

    /**
     * Makes the main combat window visible
     */
    public void startCombat() {
        view.display();
    }

    /**
     * Default method to redraw View 
     * 
     * 
     * @author kelly.applebee@studio.unibo.itc
     */
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
