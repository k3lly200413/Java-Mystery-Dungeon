package it.unibo.progetto_oop.Combat.MVC_Pattern;

import java.util.List;

import javax.swing.Timer;

import it.unibo.progetto_oop.Combat.CommandPattern.LongRangeButton;
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
    private final LongRangeButton longRangeCommand;

    private static final int ANIMATION_DELAY = 100;      //ms
    private static final int POST_ATTACK_DELAY = 500;    // ms
    private static final int INFO_ZOOM_DELAY = 200; // ms
    private static final int INFO_NEXT_DRAW_DELAY = 300; // ms

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
        this.meleeCommand = new MeleeButton();

        this.longRangeCommand = new LongRangeButton();

        this.view.setHealthBarMax(model.getMaxHealth());
        // TODO: make methods in model that divides playerMaxHleath and enemyMaxHealth
        this.view.updatePlayerHealth(model.getPlayerHealth());
        this.view.updateEnemyHealth(model.getEnemyHealth());
        
        this.attachListeners();
        
        this.redrawView();
    }

    /**
     * Makes the main combat window visible
     */
    public void startCombat() {
        this.view.display();
    }

    /**
     * Default method to redraw View 
     * 
     * 
     * @author kelly.applebee@studio.unibo.itc
     */
    private void redrawView(){
        this.view.redrawGrid(this.model.getPlayerPosition(), this.model.getEnemyPosition(), 
                        this.model.getAttackPosition(), true, true, 
                        false, false, 1, 1);
    }

    /**
     * Uses private methods to Assing Actionlisteners to buttons inside view
     */
    private void attachListeners() {
        this.view.addAttackButtonListener(e -> handleAttackMenu());
        this.view.addPhysicalButtonListener(e -> handlePlayerPhysicalAttack());
        this.view.addLongRangeButtonListener(e -> handlePlayerLongRangeAttack(false));
        this.view.addPoisonButtonListener(e -> handlePlayerLongRangeAttack(true));
        this.view.addBackButtonListener(e -> handleBackToMainMenu());
        this.view.addInfoButtonListener(e -> handleInfo());
        this.view.addBagButtonListener(e -> System.out.println("Bag clicked - Not Yet Implemented"));
        this.view.addRunButtonListener(e -> System.out.println("Run clicked - Not Yet Implemented"));
    }

    private void handleAttackMenu() {
        System.out.println("Attack Menu button clicked.");
        this.view.showAttackOptions(); // Show the attack sub-menu
    }

    private void handleBackToMainMenu() {
        System.out.println("Back button clicked.");
        this.view.showOriginalButtons(); // Go back to the main menu
    }

    private void handleInfo() {
        if (!this.model.isPlayerTurn()) {
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
        if (!model.isPlayerTurn() || isAnimationRunning()){
            return;
        } 
        
        this.view.setButtonsEnabled(false); // Disable buttons during animation
        this.view.clearInfo();
        this.view.showInfo("Player Has used physical Attack");
        System.out.println("Physical Attack button clicked.");

        Runnable onPlayerAttackComplete = () -> {
            // We will add logic here later for game over checks and the enemy's turn
            System.out.println("Animation complete! Next up: enemy turn.");
            this.view.setButtonsEnabled(true); // Re-enable buttons for now
        };

        animatePhysicalMove(
                model.getPlayerPosition(),
                model.getEnemyPosition(),
                true, // isPlayerAttacker
                model.getPlayerPower(),
                onPlayerAttackComplete
        );
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
        this.view.clearInfo();
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
        this.stopAnimationTimer();
        this.model.setAttackPosition(this.model.getPlayerPosition()); // Start flame at player

        animationTimer = new Timer(ANIMATION_DELAY, e -> {
            // Check if flame reached the enemy
            if (this.model.getAttackPosition().x() >= this.model.getEnemyPosition().x() - 1) {
                this.stopAnimationTimer();
                this.model.setAttackPosition(this.model.getPlayerPosition()); // Reset flame position
                this.view.redrawGrid(this.model.getPlayerPosition(), this.model.getEnemyPosition(), this.model.getAttackPosition(), true, true, false, false, 1, 1);
                if (onHit != null) {
                    onHit.run();
                }
                return;
            }

            // Move flame forward using the command
            longRangeCommand.setAttributes(this.model.getAttackPosition(), 1);
            Position nextFlamePos = longRangeCommand.execute().get(0);
            this.model.setAttackPosition(nextFlamePos);
            // Redraw showing the projectile
            this.view.redrawGrid(this.model.getPlayerPosition(), this.model.getEnemyPosition(), this.model.getAttackPosition(), true, true, !isPoison, isPoison, 1, 1);
        });
        animationTimer.start();
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
        this.stopAnimationTimer();

        final int moveDirection = isPlayerAttacker ? 1 : -1;
        final int returnDirection = -moveDirection;
        final int meleeCheckDistance = 1; 

        final Position[] currentAttackerPos = { new Position(attackerStartPos.x(), attackerStartPos.y()) };
        final Position[] currentTargetPos = { new Position(targetStartPos.x(), targetStartPos.y()) };

        final int[] state = {0}; 
        final boolean[] damageApplied = {false};

        if (isPlayerAttacker) {
            this.model.setPlayerPosition(currentAttackerPos[0]);
            this.model.setEnemyPosition(currentTargetPos[0]);
        } else {
            this.model.setPlayerPosition(currentTargetPos[0]); 
            this.model.setEnemyPosition(currentAttackerPos[0]); 
        }

        this.animationTimer = new Timer(ANIMATION_DELAY, null);
        this.animationTimer.addActionListener(event -> {

            Position nextAttackerPos = currentAttackerPos[0];
            Position nextTargetPos = currentTargetPos[0];

            // --- State Logic ---
            if (state[0] == 0) { 
                this.meleeCommand.setAttributes(currentAttackerPos[0], currentTargetPos[0], moveDirection, meleeCheckDistance);
                List<Position> result = this.meleeCommand.execute(); 
                nextAttackerPos = result.get(0);
                nextTargetPos = result.get(1); 

                if (this.meleeCommand.neighbours(nextAttackerPos, nextTargetPos, meleeCheckDistance) || !nextTargetPos.equals(currentTargetPos[0])) {
                    state[0] = 1; 
                } else if (!nextAttackerPos.equals(currentAttackerPos[0])) {

                } else {

                    if (this.meleeCommand.neighbours(nextAttackerPos, nextTargetPos, meleeCheckDistance + 1)) { // Check slightly wider range
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
                        this.model.decreaseEnemyHealth(attackPower);
                        this.view.updateEnemyHealth(this.model.getEnemyHealth());
                    } else {
                        this.model.decreasePlayerHealth(attackPower);
                        this.view.updatePlayerHealth(this.model.getPlayerHealth());
                    }
                    damageApplied[0] = true;

                    if(checkGameOver()) {
                        this.stopAnimationTimer(); 

                        if (isPlayerAttacker){
                            this.model.setEnemyPosition(currentTargetPos[0]);
                        } else {
                            this.model.setPlayerPosition(currentTargetPos[0]);
                            return;
                        }
                    }
                }

                nextAttackerPos = new Position(currentAttackerPos[0].x() + returnDirection, currentAttackerPos[0].y());
                nextTargetPos = new Position(currentTargetPos[0].x() + returnDirection, currentTargetPos[0].y());
                currentAttackerPos[0] = nextAttackerPos;
                currentTargetPos[0] = nextTargetPos;
                state[0] = 2; 

            } else { 
                if (currentAttackerPos[0].x() == attackerStartPos.x()) { 
                    this.stopAnimationTimer();

                    currentAttackerPos[0] = attackerStartPos;

                    if (isPlayerAttacker) {
                        this.model.setPlayerPosition(currentAttackerPos[0]);
                        this.model.setEnemyPosition(currentTargetPos[0]);
                    } else {
                        this.model.setPlayerPosition(currentTargetPos[0]); 
                        this.model.setEnemyPosition(currentAttackerPos[0]); 
                    }
                    this.redrawView(); 

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
                this.model.setPlayerPosition(currentAttackerPos[0]);
                this.model.setEnemyPosition(currentTargetPos[0]);
            } else {
                this.model.setPlayerPosition(currentTargetPos[0]); 
                this.model.setEnemyPosition(currentAttackerPos[0]); 
            }

            this.redrawView(); 

        });
        this.animationTimer.start();
    }
    
    private int zoomerStep = 0;

    private void zoomerAnimation() {
        this.stopAnimationTimer();
        this.view.setButtonsEnabled(false);

        final Position originalEnemyPosition = this.model.getEnemyPosition();
        final int targetX= model.getSize()/2;
        final int targetY = model.getEnemyPosition().y();;

        this.animationTimer = new Timer(ANIMATION_DELAY, e -> {
            if (model.getEnemyPosition().x() < targetX) {
                stopAnimationTimer(); // Increase offset for zoom effect
                model.setEnemyPosition(new Position(targetX, targetY));
                this.redrawView();
                infoNextDrawAnimation(originalEnemyPosition);
            } else {
                model.setEnemyPosition(new Position(model.getEnemyPosition().x()-1, originalEnemyPosition.y()));
                this.redrawView();
            }
        });
        zoomerStep = 0;
        animationTimer.start();
    }

    private void infoNextDrawAnimation(Position originalEnemyPosition) {
        stopAnimationTimer();

        animationTimer = new Timer(INFO_ZOOM_DELAY, e -> {
            zoomerStep++;
            if (zoomerStep >= 6) {
                stopAnimationTimer();
                model.setEnemyPosition(originalEnemyPosition); // Reset enemy position
                redrawView();
                this.view.setButtonsEnabled(true);
            } else {
                this.view.redrawGrid(model.getPlayerPosition(), model.getEnemyPosition(), model.getAttackPosition(), true, true, false, false, 1, zoomerStep);
            }
        });
    }

    private void applyPostTurnEffects() {
        if (model.isEnemyPoisoned() && model.getEnemyHealth() > 0){
            view.showInfo("Enemy take oison damage!");
            model.decreaseEnemyHealth(model.getPlayerPoisonPower());
            view.updateEnemyHealth(model.getEnemyHealth());
        }
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
