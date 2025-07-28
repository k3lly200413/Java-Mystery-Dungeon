package it.unibo.progetto_oop.Combat.MVC_Pattern;

import java.util.List;

import javax.swing.Timer;

import it.unibo.progetto_oop.Combat.CommandPattern.MeleeButton;
import it.unibo.progetto_oop.Combat.Position.Position;

/**
 * Controller class in Model View Controller Pattern
 * 
 * @author Kelly.applebee@studio.unibo.it
 */
public class CombatController {
    private final CombatModel model;
    private final CombatView view;
    private final MeleeButton meleeCommand;

    private static final int ANIMATION_DELAY = 100;      //ms
    private static final int POST_ATTACK_DELAY = 500;    // ms

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
        if (!model.isPlayerTurn() || isAnimationRunning()) return;
        
        view.setButtonsEnabled(false); // Disable buttons during animation
        view.clearInfo();
        System.out.println("Physical Attack button clicked.");

        Runnable onPlayerAttackComplete = () -> {
            // We will add logic here later for game over checks and the enemy's turn
            System.out.println("Animation complete! Next up: enemy turn.");
            view.setButtonsEnabled(true); // Re-enable buttons for now
        };

        animatePhysicalMove(
                model.getPlayerPosition(),
                model.getEnemyPosition(),
                true, // isPlayerAttacker
                model.getPlayerPower(),
                onPlayerAttackComplete
        );
    }

    private void handlePlayerLongRangeAttack(boolean applyPoison) {
        System.out.println("Long Range Attack clicked. Is Poison: " + applyPoison);
    }

    /**
     * Method to cleanly stop a Timer which is running
     * 
     * @author kelly.applebee@studio.unibo.it
     */
    private void stopAnimationTimer() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
            animationTimer = null; // Release reference
        }
    }

    private boolean isAnimationRunning() {
         return animationTimer != null && animationTimer.isRunning();
    }


    private void animatePhysicalMove( 
        Position attackerStartPos, 
        Position targetStartPos, 
        boolean isPlayerAttacker, 
        int attackPower, 
        Runnable onComplete) {
        // TODO: Add javaDoc and comments I'm to tired at this point
        stopAnimationTimer();

        final int moveDirection = isPlayerAttacker ? 1 : -1;
        final int returnDirection = -moveDirection;
        final int meleeCheckDistance = 1; 

        final Position[] currentAttackerPos = { new Position(attackerStartPos.x(), attackerStartPos.y()) };
        final Position[] currentTargetPos = { new Position(targetStartPos.x(), targetStartPos.y()) };

        final int[] state = {0}; 
        final boolean[] damageApplied = {false};

        if (isPlayerAttacker) {
            model.setPlayerPosition(currentAttackerPos[0]);
            model.setEnemyPosition(currentTargetPos[0]);
        } else {
            model.setPlayerPosition(currentTargetPos[0]); 
            model.setEnemyPosition(currentAttackerPos[0]); 
        }

        animationTimer = new Timer(ANIMATION_DELAY, null);
        animationTimer.addActionListener(event -> {

            Position nextAttackerPos = currentAttackerPos[0];
            Position nextTargetPos = currentTargetPos[0];

            // --- State Logic ---
            if (state[0] == 0) { 
                meleeCommand.setAttributes(currentAttackerPos[0], currentTargetPos[0], moveDirection, meleeCheckDistance);
                List<Position> result = meleeCommand.execute(); 
                nextAttackerPos = result.get(0);
                nextTargetPos = result.get(1); 

                if (meleeCommand.neighbours(nextAttackerPos, nextTargetPos, meleeCheckDistance) || !nextTargetPos.equals(currentTargetPos[0])) {
                    state[0] = 1; 
                } else if (!nextAttackerPos.equals(currentAttackerPos[0])) {

                } else {

                    if (meleeCommand.neighbours(nextAttackerPos, nextTargetPos, meleeCheckDistance + 1)) { // Check slightly wider range
                        state[0] = 1;
                    } else {

                        System.err.println("Animation stuck in state 0? Forcing state 1.");
                        state[0] = 1;
                    }
                }
                currentAttackerPos[0] = nextAttackerPos;
                currentTargetPos[0] = nextTargetPos;

            } else if (state[0] == 1) { 
                if (!damageApplied[0]) {
                if (isPlayerAttacker) {
                        model.decreaseEnemyHealth(attackPower);
                        view.updateEnemyHealth(model.getEnemyHealth());
                    } else {
                        model.decreasePlayerHealth(attackPower);
                        view.updatePlayerHealth(model.getPlayerHealth());
                    }
                    damageApplied[0] = true;

                    if(checkGameOver()) {
                        stopAnimationTimer(); 

                        if (isPlayerAttacker) model.setEnemyPosition(currentTargetPos[0]);
                        else model.setPlayerPosition(currentTargetPos[0]);

                        return;
                    }
                }

                nextAttackerPos = new Position(currentAttackerPos[0].x() + returnDirection, currentAttackerPos[0].y());
                nextTargetPos = new Position(currentTargetPos[0].x() + returnDirection, currentTargetPos[0].y());
                currentAttackerPos[0] = nextAttackerPos;
                currentTargetPos[0] = nextTargetPos;
                state[0] = 2; 

            } else { 
                if (currentAttackerPos[0].x() == attackerStartPos.x()) { 
                    stopAnimationTimer();

                    currentAttackerPos[0] = attackerStartPos;

                    if (isPlayerAttacker) {
                        model.setPlayerPosition(currentAttackerPos[0]);
                        model.setEnemyPosition(currentTargetPos[0]);
                    } else {
                        model.setPlayerPosition(currentTargetPos[0]); 
                        model.setEnemyPosition(currentAttackerPos[0]); 
                    }
                    redrawView(); 

                    if (onComplete != null) {
                        onComplete.run();
                    }
                    return; 

                } else { 
                    nextAttackerPos = new Position(currentAttackerPos[0].x() + returnDirection, currentAttackerPos[0].y());

                    currentAttackerPos[0] = nextAttackerPos;
                }
            }

            if (isPlayerAttacker) {
                model.setPlayerPosition(currentAttackerPos[0]);
                model.setEnemyPosition(currentTargetPos[0]);
            } else {
                model.setPlayerPosition(currentTargetPos[0]); 
                model.setEnemyPosition(currentAttackerPos[0]); 
            }

            redrawView(); 

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
