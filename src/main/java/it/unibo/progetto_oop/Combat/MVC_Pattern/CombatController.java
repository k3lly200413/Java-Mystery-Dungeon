package it.unibo.progetto_oop.Combat.MVC_Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

import it.unibo.progetto_oop.Combat.CommandPattern.LongRangeButton;
import it.unibo.progetto_oop.Combat.CommandPattern.MeleeButton;
import it.unibo.progetto_oop.Combat.Position.Position;
import it.unibo.progetto_oop.Combat.StatePattern.AnimatingState;
import it.unibo.progetto_oop.Combat.StatePattern.CombatState;
import it.unibo.progetto_oop.Combat.StatePattern.EnemyTurnState;
import it.unibo.progetto_oop.Combat.StatePattern.InfoDisplayState;
import it.unibo.progetto_oop.Combat.StatePattern.PlayerTurnState;


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

    private static final int ANIMATION_DELAY = 100;                     //ms
    private static final int POST_ATTACK_DELAY = 500;                   // ms
    private static final int INFO_ZOOM_DELAY = 200;                     // ms
    private static final int INFO_NEXT_DRAW_DELAY = 300;                // ms
    private static final int MINIMUM_STAMINA_FOR_SPECIAL_ATTACK = 5;    // Placeholder

    private int zoomerStep = 0;

    private Timer animationTimer;
    private Timer enemyActionTimer; 

    private CombatState currentState;
    /**
     * Contructor of CombatController takes in both model and view
     * <p>
     * @param model Model which holds information necessary to controller
     * @param view  View which displays on screen information taken from controlller
     * 
     * 
     * @author kelly.applebee@studio.unibo.it
     */
    public CombatController(final CombatModel model, final CombatView view) {

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
    public void redrawView() {
        this.view.redrawGrid(this.model.getPlayerPosition(), this.model.getEnemyPosition(),
                this.model.getAttackPosition(), 0, true, true, false, false, 1,
                1, this.model.isGameOver(),
                (this.model.isPlayerTurn() ? this.model.getEnemyPosition() : this.model.getPlayerPosition()),
                false, new ArrayList<Position>(), false, 0, false, 0);
    }

    public final void redrawView(final Position palyerPos, final Position enemyPos, final Position flamePos,
            final int flameSize, final boolean drawPlayer, final boolean drawEnemy,
            final boolean drawFlame, final boolean drawPoison, final int playerRange,
            final int enemyRange, final boolean isGameOver, final Position whoDied,
            final boolean bossRayAttack, final ArrayList<Position> deathRayPath,
            final boolean drawPoisonDamage, final int poisonYCoord,
            final boolean isCharging, final int chargingPosition) {
        this.view.redrawGrid(palyerPos, enemyPos, flamePos, flameSize, drawPlayer,
                drawEnemy, drawFlame, drawPoison, playerRange, enemyRange,
                isGameOver, whoDied, bossRayAttack, deathRayPath, drawPoisonDamage,
                poisonYCoord, isCharging, chargingPosition);
    }

    /**
     * Uses private methods to Assing Actionlisteners to buttons inside view
     */
    private void attachListeners() {
        this.view.addAttackButtonListener(e -> handleAttackMenu());
        this.view.addPhysicalButtonListener(e -> handlePlayerPhysicalAttack());
        this.view.addLongRangeButtonListener(e -> handlePlayerLongRangeAttack(false, true));
        this.view.addPoisonButtonListener(e -> handlePlayerLongRangeAttack(true, false));
        this.view.addBackButtonListener(e -> handleBackToMainMenu());
        this.view.addInfoButtonListener(e -> handleInfo());
        this.view.addBagButtonListener(e -> handleBagMenu());
        this.view.addRunButtonListener(e -> System.out.println("Run clicked - Not Yet Implemented"));
        this.view.addCurePoisonButtonListener(e -> this.handleCurePoisonInput());
        this.view.addAttackButtonListener(e -> handleAttackBuff());
        this.view.addHealButtonListener(e -> handleHeal());

    }

    private void handleAttackMenu() {
        System.out.println("Attack Menu button clicked.");
        this.view.showAttackOptions(); // Show the attack sub-menu
        // TODO: add getter in model to get stamina to then check if it's lower than the
        // minimum then remove comment below
        /*
         * if (this.model.getPlayerStamina() < MINIMUM_STAMINA_FOR_SPECIALS){
         * this.view.setCustomButtonDisabled(this.view.getLongRangeAttackButton())
         * this.view.setCustomButtonDisabled(this.view.getPoisonAttackButton())
         * }
         */
    }

    private void handleBagMenu() {
        this.view.showBagButtons();
        view.clearInfo();
        System.out.println("Debug: Pressed Bag Button");
    }

    private void handleBackToMainMenu() {
        CombatState newState = new PlayerTurnState();
        newState.enterState(this);
        newState.handleBackInput(this);
    }

    public final void performBackToMainMenu() {
        System.out.println("Back button clicked.");
        view.showOriginalButtons(); // Go back to the main menu
    }

    private void handleInfo() {
        CombatState newState = new PlayerTurnState();
        newState.enterState(this);
        newState.handleInfoInput(this);
    }

    public void performInfo() {
        performInfoZoomInAnimation(() -> {
            this.setState(new InfoDisplayState());
        });
        this.view.showInfo("Enemy Info:\nName: " + this.model.getEnemyName());
    }

    private void handlePlayerPhysicalAttack() {
        CombatState newState = new PlayerTurnState();
        newState.enterState(this);
        newState.handlePhysicalAttackInput(this);
        // call playerturnstate and have it run performPlayerphysical Attack
    }

    private void handleCurePoisonInput() {
        CombatState currentState = new PlayerTurnState();
        currentState.handleCurePoisonInput(this);
    }

    public final void performPlayerPhysicalAttack() {
        if (!model.isPlayerTurn() || isAnimationRunning()) {
            return;
        }
        Runnable onPlayerAttackComplete = () -> {
            new AnimatingState().handleAnimationComplete(this);
            applyPostTurnEffects();
            if (checkGameOver()) return; //Check if enemy was defeated
        };

        animatePhysicalMove(
                model.getPlayerPosition(),
                model.getEnemyPosition(),
                true, // isPlayerAttacker
                model.getPlayerPower(),
                onPlayerAttackComplete);
    }

    private void startDelayedEnemyTurn(final int delay) {
        Timer enemyTurnDelayTimer = new Timer(delay, e -> {
            enemyTurn();
        });
        enemyTurnDelayTimer.setRepeats(false); // ensure it only runs once
        enemyTurnDelayTimer.start();
    }

    public final void performDelayedEnemyAction(final int delay, final Runnable action) {
        // Logic for delayed enemy action
        if (enemyActionTimer != null && enemyActionTimer.isRunning()) {
            enemyActionTimer.stop(); // Stop any previous timer
        }
        enemyActionTimer = new Timer(delay, e -> {
            if (currentState instanceof EnemyTurnState) {
                action.run(); // Execute the action
            } else {
                System.err.println("Error: Not in enemy turn state, cannot perform delayed action.");
            }
        });
        enemyActionTimer.setRepeats(false); // Ensure it only runs once
        enemyActionTimer.start();
    }

    public final void enemyTurn() {
        model.setPlayerTurn(false);
        view.showInfo("Enemy attacks!");

        // Enemy attacks player
        Runnable onEnemyAttackComplete = () -> {
            if (checkGameOver()) {
                return; // Check if player was defeated
            }

            model.setPlayerTurn(true);
            view.setAllButtonsEnabled();
            view.showInfo("Player's turn!");
            view.showOriginalButtons();
        };

        // Simple AI: The enemy always uses a physical attack.
        // We re-use the same animation engine, just with the roles reversed.
        animatePhysicalMove(
                model.getEnemyPosition(),
                model.getPlayerPosition(),
                false, // isPlayerAttacker
                model.getEnemyPower(),
                onEnemyAttackComplete);
    }

    private void handlePlayerLongRangeAttack(final boolean applyPoison, final boolean applyFlameIntent) {
        CombatState playerState = new PlayerTurnState();
        playerState.enterState(this);
        playerState.handleLongRangeAttackInput(this, applyPoison, applyFlameIntent);
    }

    /**
     * Handler for Generic Long range attack
     * 
     * @param applyPoison boolean to tell controller wether to apply poison to
     *                    target or not
     * 
     * @author kelly.applebee@studio.unibo.it
     */
    public void performPlayerLongRangeAttack(final boolean applyPoison, final boolean applyFlameIntent) {
        if (!this.model.isPlayerTurn() || this.isAnimationRunning()) {
            return;
        }
        this.view.setAllButtonsDisabled();
        this.view.clearInfo();
        this.view.showInfo(applyPoison ? "Player uses poison!" : "Player uses long range attack!");

        this.longRangeAttackAnimation(applyPoison, applyFlameIntent, () -> {
            this.model.decreaseEnemyHealth(model.getPlayerPower());
            if (applyPoison) {
                this.model.setEnemyPoisoned(true);
                this.view.showInfo("Enemy is Poisoned!");
            }

            this.view.updateEnemyHealth(this.model.getEnemyHealth());
            new AnimatingState().handleAnimationComplete(this);
            this.applyPostTurnEffects();

            if (checkGameOver()) {
                return;
            }

            this.startDelayedEnemyTurn(POST_ATTACK_DELAY);
            this.redrawView();
        });
    }

    private void longRangeAttackAnimation(final Position attacker, final int direction, final boolean isFlame,
            final boolean isPoison, final Runnable onHit) {
        stopAnimationTimer(); // Ensure no other animation is running

        model.setAttackPosition(new Position((attacker.x() + direction), attacker.y())); // Start flame at player

        redrawView(true, true, false, false, (isFlame || isPoison) ? 0 : 1, 1, 1, false, model.getEnemyPosition(),
                (model.isPlayerTurn() ? model.getEnemyPosition() : model.getPlayerPosition()),
                false, model.getEnemyPosition().y(), (isFlame || isPoison) ? false : true, model.getDeathRayPath()); // Redraw
                                                                                                                     // without
                                                                                                                     // flame/poison
                                                                                                                     // visible

        System.out.println("Attacker Position => " + attacker.equals(model.getEnemyPosition()));

        animationTimer = new Timer(INFO_ZOOM_DELAY, e -> {
            // Check if flame reached or passed the enemy
            if (model.getAttackPosition().x() > model.getEnemyPosition().x() - 1 ||
                    model.getAttackPosition().x() < model.getPlayerPosition().x() + 1) {
                // if (model.getFlamePosition().x() >= enemy.x() -direction) { // -1 to hit when
                // adjacent
                System.out.println("\nFinished Long Range Attack Animation\n");
                stopAnimationTimer();
                // Reset flame position visually (optional, could just hide it)
                model.setAttackPosition(attacker); // Move flame back instantly
                redrawView(true, true, false, false, (isFlame || isPoison) ? 0 : 1, 1, 1, false,
                        model.getEnemyPosition(),
                        (model.isPlayerTurn() ? model.getEnemyPosition() : model.getPlayerPosition()),
                        false, model.getEnemyPosition().y(), false, model.getDeathRayPath()); // Redraw without
                                                                                              // flame/poison visible
                if (this.model.isPlayerTurn()) {
                    this.model.decreaseEnemyHealth(this.model.getPlayerLongRangePower());
                } else {
                    this.model.decreasePlayerHealth(this.model.getEnemyLongRangePower());
                }

                if (onHit != null) {
                    System.out.println("\nI now have to apply poison status\nThe State is => "
                            + this.currentState.getClass().getSimpleName());
                    onHit.run(); // Execute the action upon hitting
                }
                return;
            }

            // Move flame forward using the command
            longRangeCommand.setAttributes(this.model.getAttackPosition(), 1);
            Position nextFlamePos = longRangeCommand.execute().get(0);
            this.model.setAttackPosition(nextFlamePos);
            // Redraw showing the projectile

            this.redrawView(this.model.getPlayerPosition(), this.model.getEnemyPosition(),
                    this.model.getAttackPosition(),
                    (isFlame || isPoison) ? 0 : 1, true, true, !isPoison, isPoison, 1, 1, false,
                    model.getPlayerPosition(), (isFlame || isPoison) ? false : true,
                    new ArrayList<Position>(), false, 0, false, 0);
        });
        animationTimer.start();
    }

    public void handleBossDeathRayAttack() {
        // call boss state and run handleBossDeathRayAttack(this);
    }

    public final void performBossDeathRayAttack() {
        this.view.clearInfo();
        this.view.showInfo("Boss Unleasehs Death Ray");

        Runnable onDeathRayAttackComplete = () -> {
            // call animation complete(this);
        };
    }

    public final void animateBossDeathRay(final Runnable onHit) {
        this.stopAnimationTimer();

        final ArrayList<Position> deathRayLastPosition = new ArrayList<>();

        this.animationTimer = new Timer(100, e -> {
            if (deathRayLastPosition.stream()
                    .anyMatch(
                            passsedPosition -> passsedPosition
                                    .equals(this.model.getPlayerPosition()))) {
                this.stopAnimationTimer();
                // we want to reset the position of the ray
                deathRayLastPosition.clear();
                this.redrawView();
                // TODO: Change to longrange enemy power
                this.model.decreasePlayerHealth(this.model.getEnemyPower());
                if (onHit != null) {
                    System.out.println("\nI now have to apply poison status\n");
                    onHit.run(); // Execute the action upon hitting
                }
                return;
            } else {
                deathRayLastPosition.add(new Position(deathRayLastPosition.get(deathRayLastPosition.size() - 1).x() - 1,
                        this.model.getEnemyPosition().y()));
                this.redrawView(this.model.getPlayerPosition(),
                        this.model.getEnemyPosition(), new Position(0, 0),
                        2, true, true, false, false, 1, 1, false, new Position(0, 0),
                        true, deathRayLastPosition, false, 0, false, 0);
            }
        });

    }

    /**
     * Method to cleanly stop a Timer which is running
     * 
     * @author kelly.applebee@studio.unibo.it
     */
    public void stopAnimationTimer() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
            animationTimer = null; // Release reference
        }
    }

    private boolean isAnimationRunning() {
        return animationTimer != null && animationTimer.isRunning();
    }

    private void animatePhysicalMove(
            final Position attackerStartPos,
            final Position targetStartPos,
            final boolean isPlayerAttacker,
            final int attackPower,
            final Runnable onComplete) {
        // TODO: Add javaDoc and comments I'm to tired at this point
        this.stopAnimationTimer();

        final int moveDirection = isPlayerAttacker ? 1 : -1;
        final int returnDirection = -moveDirection;
        final int meleeCheckDistance = 1;

        final Position[] currentAttackerPos = { new Position(attackerStartPos.x(), attackerStartPos.y()) };
        final Position[] currentTargetPos = { new Position(targetStartPos.x(), targetStartPos.y()) };

        final int[] state = { 0 };
        final boolean[] damageApplied = { false };

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
                this.meleeCommand.setAttributes(currentAttackerPos[0], currentTargetPos[0], moveDirection,
                        meleeCheckDistance);
                List<Position> result = this.meleeCommand.execute();
                nextAttackerPos = result.get(0);
                nextTargetPos = result.get(1);

                if (this.meleeCommand.neighbours(nextAttackerPos, nextTargetPos, meleeCheckDistance)
                        || !nextTargetPos.equals(currentTargetPos[0])) {
                    state[0] = 1;
                } else if (!nextAttackerPos.equals(currentAttackerPos[0])) {

                } else {

                    if (this.meleeCommand.neighbours(nextAttackerPos, nextTargetPos, meleeCheckDistance + 1)) { // Check
                                                                                                                // slightly
                                                                                                                // wider
                                                                                                                // range
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

                    if (checkGameOver()) {
                        this.stopAnimationTimer();

                        if (isPlayerAttacker) {
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
                    nextAttackerPos = new Position(currentAttackerPos[0].x() + returnDirection,
                            currentAttackerPos[0].y());

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

    public final void performEnemySuperAttack() {
        System.out.println("\nPerforming Super Attack\n");

        /*
         * Runnable onSuperAttackComplete = () -> {
         * this.currentState.handleAnimationComplete(this);
         * };
         * 
         * // Now start the animation itself
         * int superAttackPower = (int)(model.getEnemyPower() * 1.5);
         * animatePhysicalMove(
         * model.getEnemyPosition(),
         * model.getPlayerPosition(),
         * false, // isPlayerAttacker
         * superAttackPower,
         * onSuperAttackComplete
         * );
         */

    }

    public final void performEnemyPhysicalAttack() {
        view.clearInfo();
        view.showInfo("Enemy attacks!");
        Runnable onEnemyAttackComplete = () -> {
            currentState.handleAnimationComplete(this);
        };
        this.animatePhysicalMove(
                model.getEnemyPosition(),
                model.getPlayerPosition(),
                false, // isPlayerAttacker
                model.getEnemyPower(),
                onEnemyAttackComplete);
    }
    
    private void performInfoZoomInAnimation(Runnable onZoomComplete) {
        this.stopAnimationTimer();
        this.view.setAllButtonsDisabled();

        final Position originalEnemyPosition = this.model.getEnemyPosition();
        final int targetX= model.getSize()/2;

        this.animationTimer = new Timer(INFO_ZOOM_DELAY, e -> {
            Position currentEnemyPosition = model.getEnemyPosition();
            if (currentEnemyPosition.x() <= targetX) {
                stopAnimationTimer(); // Increase offset for zoom effect
                model.setEnemyPosition(new Position(targetX, currentEnemyPosition.y()));
                this.makeBigger(5,onZoomComplete);
            } else {
                model.setEnemyPosition(new Position(model.getEnemyPosition().x() - 1, model.getEnemyPosition().y()));
                this.redrawView();
            }
        });
        animationTimer.start();
    }

    private void animatePoisonDamage() {
        this.stopAnimationTimer();
        final int[] step = { 4 };
        // TODO: Maybe add variable in model to show that we're in the poison animation
        this.animationTimer = new Timer(INFO_NEXT_DRAW_DELAY, e -> {
            if (step[0] == 1) {
                step[0]--;
                this.stopAnimationTimer();
                this.redrawView();
                // TODO: if implemented model step above show that we're not in animation
                // anymore
            } else {
                redrawView(this.model.getPlayerPosition(), this.model.getEnemyPosition(),
                        this.model.getAttackPosition(),
                        0, true, true, false, false, 1, 1, this.model.isGameOver(),
                        (this.model.isPlayerTurn() ? this.model.getEnemyPosition() : this.model.getPlayerPosition()),
                        false, new ArrayList<Position>(), true, step[0], false, 0);
                step[0]--;
            }
        });
        this.animationTimer.start();
    }

    private void infoNextDrawAnimation(final Position originalEnemyPosition) {
        stopAnimationTimer();

        animationTimer = new Timer(INFO_NEXT_DRAW_DELAY, e -> {
            zoomerStep++;
            if (zoomerStep >= 6) {
                stopAnimationTimer();
                model.setEnemyPosition(originalEnemyPosition); // Reset enemy position
                redrawView();
                this.view.setAllButtonsEnabled();
                zoomerStep = 0;
            } else {
                this.redrawView(model.getPlayerPosition(), model.getEnemyPosition(), model.getAttackPosition(),
                        0, true, true, false, false, 1, zoomerStep, false, model.getPlayerPosition(),
                        false, new ArrayList<Position>(), false, 0, false, 0);
            }
        });
        animationTimer.start();
    }

    public final void applyPostTurnEffects() {
        if (model.isEnemyPoisoned() && model.getEnemyHealth() > 0) {
            view.showInfo("Enemy take poison damage!");
            model.decreaseEnemyHealth(model.getPlayerPoisonPower());
            view.updateEnemyHealth(model.getEnemyHealth());
        }
    }

    public final boolean checkGameOver() {
        if (model.isGameOver()) {
            stopAnimationTimer();
            view.setAllButtonsDisabled();
            String winner = model.getPlayerHealth() <= 0 ? "Enemy" : "Player";
            view.showInfo("Game Over! " + winner + " wins!");
            return true;
        }
        return false;
    }

    public final void performDeathAnimation(final Position death, final boolean isCharging, final Runnable onComplete) {
        // this.animationTimer.setRepeats(false);
        // this.animationTimer.start();

        int[] position = { 4 };
        int[] times = { 3 };

        if (isCharging) {
            // animating State
            this.animationTimer = new Timer(INFO_ZOOM_DELAY, e -> {
                position[0]--;
                this.redrawView(this.model.getPlayerPosition(), this.model.getEnemyPosition(),
                        this.model.getAttackPosition(),
                        0, true, true, false, false, 1, 1, false,
                        (model.isPlayerTurn() ? model.getEnemyPosition() : model.getPlayerPosition()),
                        true, new ArrayList<Position>(), true, position[0], false, 0);
            });
            if (position[0] == 0) {
                times[0]--;
                if (times[0] == 0) {
                    this.stopAnimationTimer();
                    this.redrawView();
                    if (onComplete != null) {
                        onComplete.run();
                    }
                } else {
                    position[0] = 4;
                }
            }
            this.animationTimer.start();
        }

        this.redrawView(model.getPlayerPosition(), model.getEnemyPosition(), model.getAttackPosition(),
                1, true, true, false, false, 1, 2, true, model.getEnemyPosition(), false,
                new ArrayList<Position>(), false, 0, false, 0);
        this.animationTimer.start();
        this.redrawView(model.getPlayerPosition(), model.getEnemyPosition(), model.getAttackPosition(),
                0, true, true, false, false, 1, 2, true, model.getEnemyPosition(), false,
                new ArrayList<Position>(), false, 0, false, 0);
        if (onComplete != null) {
            onComplete.run();
        }
    }

    // ------ Getters ------

    public final CombatView getView() {
        return this.view;
    }

    public final CombatModel getModel() {
        return this.model;
    }

    /*
     * private void performAttack() {
     * 
     * Timer playerTimer = new Timer(100, e -> {
     * model.movePlayer(1, 0);
     * if (model.areNeighbours(model.getEnemyPosition())) {
     * model.decreaseEnemyHealth();
     * ((Timer) e.getSource()).stop();
     * }
     * view.redraw(model.getCells(), model.getPlayerPosition(),
     * model.getEnemyPosition());
     * });
     * playerTimer.start();
     * }
     */

    private void handleAttackBuff() {
        if (this.currentState != null) {
            currentState.handleAttackBuffInput(this);
        }
    }

    private void handleHeal() {
        if (this.currentState != null) {
            currentState.handleHealInput(this);
        }
    }

    public void setState(CombatState newState) { // Or setStates
        CombatState oldState = this.currentState; // Temporarily store the old state
    
        // *** CHECK THIS PART ***
        if (oldState != null) {
            System.out.println("setState: Calling exitState() on " + oldState.getClass().getSimpleName()); // Add debug
            oldState.exitState(this); // <-- THIS CALL IS CRUCIAL
        }
        // ***********************
    
        // Now update to the new state
        System.out.println("setState: Changing currentState to " + newState.getClass().getSimpleName()); // Add debug
        this.currentState = newState;
    
        // And call enterState on the new one
        if (this.currentState != null) {
            System.out.println("setState: Calling enterState() on " + this.currentState.getClass().getSimpleName()); // Add debug
            this.currentState.enterState(this);
        }
    }

    public final CombatState getCurrentState() {
        return currentState;
    }

    public final void performEnemyAttack() {
        final int PHYSICAL = 0;
        final int LONG_RANGE = 1;
        int num = new Random().nextInt(2);

        switch (num) {
            case PHYSICAL -> performEnemyPhysicalAttack();
            case LONG_RANGE -> performLongRangeAttack(model.getEnemyPosition(), -1, false, true);
            default:break;
        }

    }

    public final void performLongRangeAttack(final Position attacker, final int direction,
            final boolean applyFlameIntent, final boolean applyPoisonIntent) {
        // Pass the intent to the animation, AND create the completion runnable

        System.out.println(" Performing Long Range Attack");

        longRangeAttackAnimation(attacker, direction, applyFlameIntent, applyPoisonIntent, () -> {
            // Animation finished - just signal the state machine
            if (currentState != null) {
                // Pass the original intent along so the state knows if poison should be applied
                // ON HIT
                // We need a way to pass this... Modify handleAnimationComplete? Or handle
                // poison within animation?
                // --> Simpler approach for now: Handle poison status SETTING inside
                // handleAnimationComplete <--
                System.out.println("About to Apply posion");
                System.out.println("\nCurrent State = > " + this.currentState.toString() + "\n");
                if (applyPoisonIntent) {
                    // TODO: change to make it more efficient
                    if (this.model.isPlayerTurn()) {
                        this.model.setEnemyPoisoned(applyPoisonIntent);
                    } else {
                        this.model.setPlayerPoisoned(applyPoisonIntent);
                    }
                }
                currentState.handleAnimationComplete(this); // No extra args needed if state handles it
            }
        });
    }

    public final boolean checkGameOverAndUpdateView() {
        if (model.isGameOver()) {
            stopAnimationTimer(); // Controller still manages timers directly
            // Game over display logic is now handled by GameOverState.enterState
            return true;
        }
        return false;
    }

    public void performPoisonEffectAnymation() {
        stopAnimationTimer();
        final int conto[] = {4};                                            // array perché così posso dichiararlo final usarlo nel Timer se no sarebbe stato più scomodo
        model.setPoisonAnimation(true);
        animationTimer = new Timer(500, e -> {                      // Timer con delay di 300 ms perché così potevo vedere da tablet che laggava ahahahahaha
            if (conto[0] == 1) {                                        // fine del timer resetto tutto
                conto[0]--;
                stopAnimationTimer();
                redrawView();
                /*
                    * TODO
                    * CHANGE TO USE FUNCTION BELOW
                    */
                model.setPoisonAnimation(false);
                this.currentState.handleAnimationComplete(this);    // chiamo la funzione che tratta la fine delle animazioni 
            }
            else{
                System.out.println("Conto => " + conto[0]);                                         // ridisegno tutto con il veleno che sale 
                redrawView(true, true, false, false, 0, 1, 1, model.isGameOver(), model.getWhoDied(), (model.isPlayerTurn() ? model.getEnemyPosition() : model.getPlayerPosition()), true, conto[0], false, model.getDeathRayPath());
                conto[0]--;                                         // faccio salire il veleno
            }
        });
        animationTimer.start();                                     // faccio partire il timer (finisce tutte le prossime chiamate poi fa partire il timer non è coe un for (lo so è strano))

    }

}
