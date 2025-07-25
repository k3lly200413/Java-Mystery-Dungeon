package it.unibo.progetto_oop.Combat.MVC_Pattern;

import java.util.List;

import it.unibo.progetto_oop.Combat.CommandPattern.MeleeButton;
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
    private final MeleeButton meleeCommand;

    private static final int POST_ATTACK_DELAY = 500;   // ms

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
        this.meleeCommand = new MeleeButton();

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
        if (this.model.isPlayerTurn()) {
            return;
        }
        this.zoomerAnimation();
        this.view.showInfo("Enemy Info:\nName: " + this.model.getEnemyName());
    }

    /**
     * Delegates all the necessary commands to the correct files 
     * I.E. MeleeButton
     * 
     * @author kelly.applebee@studio.unibo.it
     */
    private void handlePlayerPhysicalAttack() {
        this.view.showInfo("Player uses Physical Attack!");

        this.meleeCommand.setAttributes(model.getPlayerPosition(), model.getEnemyPosition(), 1, 1);

        List<Position> newPosition = meleeCommand.execute();

        this.model.setPlayerPosition(newPosition.get(0));
        this.model.setEnemyPosition(newPosition.get(1));

        this.redrawView();
    }

    /**
     * Handler for Generic Long range attack
     * @param applyPoison   boolean to tell controller wether to apply poison to target or not 
     * 
     * @author kelly.applebee@studio.unibo.it
     */
    private void handlePlayerLongRangeAttack(boolean applyPoison) {
        if (!this.model.isPlayerTurn() || this.isAnimationRunning()) {
            return;
        }
        this.view.setButtonsEnabled(false);
        view.clearInfo();
        this.view.showInfo(applyPoison ? "Player uses poison!" : "Player uses long range attack!");
        
        this.longRangeAttackAnimation(applyPoison, () -> {
            this.model.decreaseEnemyHealth(model.getPlayerPower());
        if (applyPoison){
            this.model.setEnemyPoisoned(true);
            this.view.showInfo("Enemy is Poisoned!");
        }
        });

        this.view.updateEnemyHealth(this.model.getEnemyHealth());
        
        this.startDelayedEnemyTurn(POST_ATTACK_DELAY);
        this.redrawView();
    }

    private void longRangeAttackAnimation(boolean isPoison, Runnable onHit) {
        stopAnimationTimer();
        model.setFlamePosition(model.getPlayerPosition()); // Start flame at player

        animationTimer = new Timer(ANIMATION_DELAY, e -> {
            // Check if flame reached the enemy
            if (model.getFlamePosition().x() >= model.getEnemyPosition().x() - 1) {
                stopAnimationTimer();
                model.setFlamePosition(model.getPlayerPosition()); // Reset flame position
                view.redrawGrid(model.getPlayerPosition(), model.getEnemyPosition(), model.getFlamePosition(), true, true, false, false, 1, 1);
                if (onHit != null) {
                    onHit.run();
                }
                return;
            }

            // Move flame forward using the command
            longRangeCommand.setAttributes(model.getFlamePosition(), 1);
            Position nextFlamePos = longRangeCommand.execute().get(0);
            model.setFlamePosition(nextFlamePos);

            // Redraw showing the projectile
            view.redrawGrid(model.getPlayerPosition(), model.getEnemyPosition(), model.getFlamePosition(), true, true, !isPoison, isPoison, 1, 1);
        });
        animationTimer.start();
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
