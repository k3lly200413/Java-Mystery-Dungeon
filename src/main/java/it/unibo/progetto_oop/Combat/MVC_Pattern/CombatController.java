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

    private static final int ANIMATION_DELAY = 100      //ms
    private static final int POST_ATTACK_DELAY = 500    // ms

    private Timer animationTimer;

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
        // TODO: make methods in model that divides playerMaxHleath and enemyMaxHealth
        this.view.updatePlayerHealth(model.getMaxHealth());
        this.view.updateEnemyHealth(model.getMaxHealth());
        
        this.attachListeners();
        
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

    /**
     * Uses private methods to Assing Actionlisteners to buttons inside view
     */
    private void attachListeners() {
        view.addAttackButtonListener(e -> handleAttackMenu());
        view.addPhysicalButtonListener(e -> handlePlayerPhysicalAttack());
        view.addLongRangeButtonListener(e -> handlePlayerLongRangeAttack(false));
        view.addPoisonButtonListener(e -> handlePlayerLongRangeAttack(true));
        view.addBackButtonListener(e -> handleBackToMainMenu());
        view.addInfoButtonListener(e -> handleInfo());
        view.addBagButtonListener(e -> System.out.println("Bag clicked - Not Yet Implemented"));
        view.addRunButtonListener(e -> System.out.println("Run clicked - Not Yet Implemented"));
    }

    private void handleAttackMenu() {
        System.out.println("Attack Menu button clicked.");
        view.showAttackOptions(); // Show the attack sub-menu
    }

    private void handleBackToMainMenu() {
        System.out.println("Back button clicked.");
        view.showOriginalButtons(); // Go back to the main menu
    }

    private void handleInfo() {
        System.out.println("Info button clicked.");
    }

    private void handlePlayerPhysicalAttack() {
        System.out.println("Physical Attack button clicked.");
    }

    private void handlePlayerLongRangeAttack(boolean applyPoison) {
        System.out.println("Long Range Attack clicked. Is Poison: " + applyPoison);
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
