package it.unibo.progetto_oop.combat.mvc_pattern;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;

import it.unibo.progetto_oop.combat.combat_builder.RedrawContext;
import it.unibo.progetto_oop.combat.command_pattern.GameButton;
import it.unibo.progetto_oop.combat.command_pattern.LongRangeButton;
import it.unibo.progetto_oop.combat.command_pattern.MeleeButton;
import it.unibo.progetto_oop.combat.helper.Neighbours;
import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.potion_factory.ItemFactory;
import it.unibo.progetto_oop.combat.state_pattern.AnimatingState;
import it.unibo.progetto_oop.combat.state_pattern.CombatState;
import it.unibo.progetto_oop.combat.state_pattern.EnemyTurnState;
import it.unibo.progetto_oop.combat.state_pattern.GameOverState;
import it.unibo.progetto_oop.combat.state_pattern.InfoDisplayState;
import it.unibo.progetto_oop.combat.state_pattern.ItemSelectionState;
import it.unibo.progetto_oop.combat.state_pattern.PlayerTurnState;
import it.unibo.progetto_oop.overworld.combat_collision.CombatCollision;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;


/**.
 * Controller class in Model View Controller Pattern
 *
 * @author Kelly.applebee@studio.unibo.it
 * @author matteo.monari6@studio.unibo.it
 */
public class CombatController {

    /**
     * Animation delay for each step in the animation.
     */
    private static final int ANIMATION_DELAY = 100;                     //ms
    /**
     * Delay for the info zoom animation.
     */
    private static final int INFO_ZOOM_DELAY = 200;                     // ms
    /**
     * Delay for the next draw in the info zoom animation.
     */
    private static final int INFO_NEXT_DRAW_DELAY = 300;                // ms
    /**
     * Minimum stamina required for special attacks.
     * This is a placeholder value
     */
    private static final int MINIMUM_STAMINA_FOR_SPECIAL_ATTACK = 5;
    /**
     * Height of each square in the grid.
     */
    private static final int SQUARE_HEIGHT = 20;
    /**
     * Width of each square in the grid.
     */
    private static final int SQUARE_WIDTH = 20;

    /**
     * Static instance of CurePoison item to be used across states.
     */
    private final Item curePoisonItem;

    /**
     * Static instance of AttackBuff item to be used across states.
     */
    private final Item attackBuffItem;


    /**
     * Static instance of HealingItem item to be used across states.
     */
    private final Item healingItem;

    /**
     * Static instance of Player item to be used across states.
     */
    private final Player player;

    /**
     * Static instance of ItemFactory to be used across states.
     */
    private final ItemFactory itemFactory;

    /**
     * Model which holds information necessary to controller.
     */
    private final CombatModel model;
    /**
     * View which displays on screen information taken from controller.
     */
    private final CombatView view;
    /**
     * MeleeButton command for melee attacks.
     */
    private GameButton meleeCommand;
    /**
     * LongRangeButton command for long range attacks.
     */
    private GameButton longRangeCommand;
    /**
     * Neighbours instance to check if two positions are neighbours.
     * This is used to determine if the player can attack the enemy.
     */
    private final Neighbours neighbours;

    /**
     * Static instance of Enemy to be used across states.
     */
    private Enemy enemy;

    /**
     * Timer for animations.
     * This is used to control the timing of animations in the combat.
     */
    private Timer animationTimer;
    /**
     * Timer for enemy actions.
     */
    private Timer enemyActionTimer;
    /**
     * Current state of the combat.
     * This is used to manage the state of the combat.
     */
    private CombatState currentState;

    private CombatState enemyState;

    /** Combat collision handler. */
    private final CombatCollision combatCollision;

    /** Grid notifier for managing grid updates. */
    private final GridNotifier gridNotifier;

    /**
     * Constructor of CombatController takes in both model and view.
     *
     * @author kelly.applebee@studio.unibo.it
     * @author matteo.monari6@studio.unibo.it
     *
     * @param modelToUse Model which holds information necessary to controller
     * @param viewToUse  View which displays on screen information
     * @param newPlayer  Player instance to manage inventory and stats
     * @param newCombatCollision Collision handler for combat state
     * @param newGridNotifier Grid notifier for managing grid updates
     * @param newEnemy Enemy instance representing the current combat opponent
     */
    public CombatController(
        final CombatModel modelToUse,
        final CombatView viewToUse, final Player newPlayer,
        final CombatCollision newCombatCollision,
        final GridNotifier newGridNotifier) {

        this.model = modelToUse;
        this.view = viewToUse;
        this.neighbours = new Neighbours();

        this.view.setPlayerHealthBarMax(model.getPlayerMaxHealth());
        this.view.setEnemyHealthBarMax(this.model.getEnemyMaxHealth());
        this.view.updatePlayerHealth(model.getPlayerHealth());
        this.view.updateEnemyHealth(model.getEnemyHealth());

        this.combatCollision = newCombatCollision;
        this.gridNotifier = newGridNotifier;

        this.attachListeners();

        this.redrawView();

        this.currentState = new PlayerTurnState();

        this.itemFactory = new ItemFactory();
        this.player = newPlayer;
        this.attackBuffItem = itemFactory.createItem("Attack Buff", null);
        this.curePoisonItem = itemFactory.createItem("Antidote", null);
        this.healingItem = itemFactory.createItem("Health Potion", null);
    }

    /**
     * Makes the main combat window visible.
     */
    public void startCombat() {
        this.view.display();
    }

    /**
     * Default method to redraw View.
     *
     *
     * @author kelly.applebee@studio.unibo.it
     */
    public final void redrawView() {

        final RedrawContext defaultRedraw = new RedrawContext.Builder()
        .player(this.model.getPlayerPosition())
        .enemy(this.model.getEnemyPosition())
        .drawPlayer(true)
        .drawEnemy(true)
        .playerRange(1)
        .enemyRange(1)
        .setIsGameOver(this.model.isGameOver())
        .squareHeight(SQUARE_HEIGHT)
        .squareWidth(SQUARE_WIDTH)
        .whoDied(this.model.isPlayerTurn()
        ? this.model.getEnemyPosition()
        : this.model.getPlayerPosition())
        .build();

        this.view.redrawGrid(defaultRedraw);
    }

    /**
     * Uses private methods to Assing Actionlisteners to buttons inside view.
     */
    private void attachListeners() {
        this.view.addAttackButtonListener(e -> handleAttackMenu());
        this.view.addPhysicalButtonListener(e -> handlePlayerPhysicalAttack());
        this.view.addLongRangeButtonListener(
            e -> handlePlayerLongRangeAttack(false, true));
        this.view.addPoisonButtonListener(
            e -> handlePlayerLongRangeAttack(true, false));
        this.view.addBackButtonListener(e -> handleBackToMainMenu());
        this.view.addInfoButtonListener(e -> handleInfo());
        this.view.addBagButtonListener(e -> handleBagMenu());
        this.view.addRunButtonListener(e -> exitCombat());
        this.view.addCurePoisonButtonListener(
            e -> this.handleCurePoisonInput());
        this.view.addAttackButtonListener(e -> handleAttackMenu());
        this.view.addAttackBuffButtonListener(e -> handleAttackBuff());
        this.view.addHealButtonListener(e -> handleHeal());
    }

    private void exitCombat() {
        stopAnimationTimer();
        if (enemyActionTimer != null && enemyActionTimer.isRunning()) {
            enemyActionTimer.stop();
        }
        enemyActionTimer = null;
        combatCollision.setInCombat(false);
        this.setState(new GameOverState(combatCollision, gridNotifier, enemy, player));
        // this.view.close();
    }

    private void handleAttackMenu() {
        this.view.showAttackOptions(); // Show the attack sub-menu
        if (this.model.getPlayerStamina()
        < MINIMUM_STAMINA_FOR_SPECIAL_ATTACK) {
            this.view.setCustomButtonDisabled(
                this.view.getLongRangeAttackButton());
            this.view.setCustomButtonDisabled(
                this.view.getPoisonAttackButton());
        }

    }

    private void handleBagMenu() {
        this.setState(new ItemSelectionState());
        this.view.showBagButtons();
        this.view.setBagButtonsEnabled();
        System.out.println(this.player.getInventory().getItemCount(attackBuffItem));
        System.out.println(this.player.getInventory().getItemCount(curePoisonItem));
        System.out.println(this.player.getInventory().getItemCount(healingItem));
        if (!this.player.getInventory().canUseItem(attackBuffItem)) {
            this.view.setCustomButtonDisabled(this.view.getAttackBuffButton());
        }
        if (!this.player.getInventory().canUseItem(curePoisonItem)) {
            this.view.setCustomButtonDisabled(this.view.getCurePoisonButton());
        }
        if (!this.player.getInventory().canUseItem(healingItem)) {
            this.view.setCustomButtonDisabled(this.view.getHealingButton());
        }
        view.clearInfo();
    }

    private void handleBackToMainMenu() {
        this.setState(new PlayerTurnState());
        this.currentState.enterState(this);
        this.currentState.handleBackInput(this);
    }

    /**
     * Handles the back button click event.
     * This method is called when the back button is clicked in the view.
     * It transitions back to the main menu or previous state.
     */
    public final void performBackToMainMenu() {
        view.showOriginalButtons(); // Go back to the main menu
        this.model.resetPositions();
        this.redrawView();
        this.setState(new PlayerTurnState());
    }

    private void handleInfo() {
        this.currentState.enterState(this);
        this.currentState.handleInfoInput(this);
    }

    /**
     * Handles the info button click event.
     * This method is called when the info button is clicked in the view.
     * It displays information about the enemy.
     */
    public void performInfoAnimation() {
        performInfoZoomInAnimation(() -> {
            this.setState(new InfoDisplayState());
        });
        this.view.showInfo("Enemy Info:\nName: " + this.model.
        getEnemyName() + "\nPower: " + model.getEnemyPower());
    }

    /**
     * Handles the info button click event.
     * This method is called when the info button is clicked in the view.
     * It displays information about the enemy.
     */
    public void performInfo() {
        performInfoZoomInAnimation(() -> {
            this.setState(new InfoDisplayState());
        });
        this.view.showInfo("Enemy Info:\nName: " + this.model.getEnemyName());

    }

    private void handlePlayerPhysicalAttack() {
        this.currentState.enterState(this);
        this.currentState.handlePhysicalAttackInput(this);
        // call playerturnstate and have it run performPlayerphysical Attack
    }

    private void handleCurePoisonInput() {
        this.currentState.handlePotionUsed(this, this.curePoisonItem, null);
        this.player.getInventory().decreaseItemCount(curePoisonItem);
        currentState.handleBackInput(this);
    }

    /**
     * Performs a physical attack by the player.
     * This method is called when the player performs a physical attack.
     * It checks if it's the player's turn
     * also if an animation is not already running.
     * If conditions are met, it animates the physical move
     * thenhandles the attack completion.
     */
    public final void performPlayerPhysicalAttack() {
        if (!model.isPlayerTurn() || isAnimationRunning()) {
            return;
        }
        final Runnable onPlayerAttackComplete = () -> {
            this.currentState.handleAnimationComplete(this);
        };

        animatePhysicalMove(
                model.getPlayerPosition(),
                model.getEnemyPosition(),
                true, // isPlayerAttacker
                model.getPlayerPower(),
                onPlayerAttackComplete);
    }

    /**
     * Executes a delayed enemy action using a Swing Timer.
     * Stops any existing timer and runs the given action after the delay
     * only if the current state is EnemyTurnState.
     *
     * @param delay  delay in milliseconds before execution
     * @param action the task to perform
     */
    public final void performDelayedEnemyAction(
        final int delay,
        final Runnable action) {
        // Logic for delayed enemy action
        if (enemyActionTimer != null && enemyActionTimer.isRunning()) {
            enemyActionTimer.stop(); // Stop any previous timer
        }
        enemyActionTimer = new Timer(delay, e -> {
            if (currentState instanceof EnemyTurnState) {
                action.run(); // Execute the action
            }
        });
        enemyActionTimer.setRepeats(false); // Ensure it only runs once
        enemyActionTimer.start();
    }

    /**
     * Executes the enemy's turn.
     * Disables player control, shows attack info, performs a physical attack
     * with simple AI, and restores player control after the attack.
     */
    public final void enemyTurn() {
        model.setPlayerTurn(false);
        view.showInfo("Enemy attacks!");

        // Enemy attacks player
        final Runnable onEnemyAttackComplete = () -> {
            if (checkGameOver()) {
                return; // Check if player was defeated
            }

            model.setPlayerTurn(this.model.isPlayerTurn());
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

    private void handlePlayerLongRangeAttack(
        final boolean applyPoison, final boolean applyFlameIntent) {
        this.currentState.enterState(this);
        this.currentState.handleLongRangeAttackInput(
            this, applyPoison, applyFlameIntent);
    }

    /**
     * Animates a long-range attack.
     * This method handles the animation of a long-range attack,
     * moving the projectile towards
     * the target and executing an action upon hit.
     *
     * @param attacker   Position of the attacker
     * @param direction  Direction of the attack (1 for right, -1 for left)
     * @param isAttack    True if the attack is a flame attack
     * @param isPoison   True if the attack is a poison attack
     * @param onHit      Runnable to execute when the attack hits the target
     */
    private void longRangeAttackAnimation(final Position attacker,
    final int direction, final boolean isAttack,
            final boolean isPoison, final Runnable onHit) {
        stopAnimationTimer(); // Ensure no other animation is running

        // Player => Flame <= Enemy

        model.setAttackPosition(new Position(
            attacker.x() + direction, attacker.y())); // Start flame at player
        if (!this.checkGameOver()){
            final RedrawContext redrawContext = new RedrawContext.Builder()
            .player(this.model.getPlayerPosition())
            .enemy(this.model.getEnemyPosition())
            .flame(this.model.getAttackPosition())
            .drawPlayer(true)
            .drawEnemy(true)
            .drawFlame(!isPoison)
            .drawPoison(isPoison)
            .playerRange(1)
            .enemyRange(1)
            .setIsGameOver(this.model.isGameOver())
            .build();
            this.view.redrawGrid(redrawContext);
            /*this.redrawView(
                this.model.getPlayerPosition(), this.model.getEnemyPosition(),
                this.model.getAttackPosition(), 0, true, true, !isPoison, isPoison,
                1, 1, this.model.isGameOver(), (model.isPlayerTurn()
                ? model.getEnemyPosition()
                : model.getPlayerPosition()), false, new ArrayList<>(),
                false, 0, false, 0
            );*/
        }

        animationTimer = new Timer(INFO_ZOOM_DELAY, e -> {
            // Check if flame reached or passed the enemy
            if (model.getAttackPosition().x() > model.getEnemyPosition().x() - 1
                ||
                model.getAttackPosition().x() < model.
                getPlayerPosition().x() + 1) {
                // if (model.getFlamePosition().x() >= enemy.x() -direction) {
                // -1 to hit when
                // adjacent
                stopAnimationTimer();
                // Reset flame position visually (optional, could just hide it)
                model.setAttackPosition(attacker); // Move flame back instantly
                redrawView();

                final int remaining = model.applyAttackHealth(
                    this.model.isPlayerTurn(),
                    this.model.getPlayerLongRangePower()
                    );

                if (this.model.isPlayerTurn()) {
                    view.updateEnemyHealth(remaining);
                } else {
                    view.updatePlayerHealth(remaining);
                }

                if (onHit != null) {
                    onHit.run(); // Execute the action upon hitting
                }
                return;
            }

            // Move flame forward using the command
            longRangeCommand = new LongRangeButton(
                this.model.getAttackPosition(), direction);
            final Position nextFlamePos = longRangeCommand.execute().get(0);
            this.model.setAttackPosition(nextFlamePos);
            // Redraw showing the projectile
            final RedrawContext defaultRedraw = new RedrawContext.Builder()
            .player(this.model.getPlayerPosition())
            .enemy(this.model.getEnemyPosition())
            .flame(this.model.getAttackPosition())
            .flameSize((isAttack || isPoison) ? 0 : 1)
            .drawPlayer(true)
            .drawEnemy(true)
            .drawFlame(!isPoison)
            .drawPoison(isPoison)
            .playerRange(1)
            .enemyRange(1)
            .drawBossRayAttack(!(isAttack || isPoison))
            .setIsGameOver(this.model.isGameOver())
            .build();
            this.view.redrawGrid(defaultRedraw);
            /*      this.redrawView(
                    this.model.getPlayerPosition(),
                    this.model.getEnemyPosition(),
                    this.model.getAttackPosition(),
                    (isFlame || isPoison) ? 0 :
                    1, true, true, !isPoison, isPoison, 1, 1, false,
                    model.getPlayerPosition(),
                    (isFlame || isPoison) ? false : true,
                    new ArrayList<Position>(), false, 0, false, 0);
            */
        });
        animationTimer.start();
    }

    /**
     * Handles the boss death ray attack.
     * This method is called when the boss unleashes a death ray attack.
     * It should be called from the boss state.
     */
    public void handleBossDeathRayAttack() {
        // call boss state and run handleBossDeathRayAttack(this);
    }

    /**
     * Performs the boss death ray attack.
     * This method is called when the boss unleashes a death ray attack.
     * It sets up the animation and handles the attack logic.
     */
    public final void performBossDeathRayAttack() {
        this.view.clearInfo();
        this.view.showInfo("Boss Unleasehs Death Ray");

        this.longRangeAttackAnimation(
            model.getEnemyPosition(), -1, false, false, () -> {
            // Animation finished - just signal the state machine
            if (currentState != null) {
                // No extra args needed if state handles it
                currentState.handleAnimationComplete(this);
            }
        });
    }

    /**
     * Animates the boss death ray attack.
     * This method handles the animation of the boss death ray attack.
     * It moves the death ray towards the player and checks for hits.
     *
     * @param onHit Runnable to execute when the death ray hits the player
     */
    public final void animateBossDeathRay(final Runnable onHit) {
        this.stopAnimationTimer();

        final List<Position> deathRayLastPosition = new ArrayList<>();

        this.animationTimer = new Timer(ANIMATION_DELAY, e -> {
            if (deathRayLastPosition.stream()
                    .anyMatch(
                            passsedPosition -> passsedPosition
                                    .equals(this.model.getPlayerPosition()))) {
                this.stopAnimationTimer();
                // we want to reset the position of the ray
                deathRayLastPosition.clear();
                this.redrawView();

                this.model.decreasePlayerHealth(
                    this.model.getEnemyLongRangePower());
                if (onHit != null) {
                    onHit.run(); // Execute the action upon hitting
                }
            } else {
                deathRayLastPosition.add(
                    new Position(
                        deathRayLastPosition.get(
                            deathRayLastPosition.size() - 1).x() - 1,
                        this.model.getEnemyPosition().y()));
                final RedrawContext defaultRedraw = new RedrawContext.Builder()
                .player(this.model.getPlayerPosition())
                .enemy(this.model.getEnemyPosition())
                .flame(this.model.getAttackPosition())
                .flameSize(2)
                .drawPlayer(true)
                .drawEnemy(true)
                .playerRange(1)
                .enemyRange(1)
                .setIsGameOver(this.model.isGameOver())
                .drawBossRayAttack(true)
                .deathRayPath(deathRayLastPosition)
                .build();
                this.view.redrawGrid(defaultRedraw);
                /*this.redrawView(this.model.getPlayerPosition(),
                        this.model.getEnemyPosition(), new Position(0, 0),
                        2, true, true, false,
                        false, 1, 1, false, new Position(0, 0),
                        true, deathRayLastPosition, false, 0, false, 0);*/
            }
        });

    }

    /**
     * Method to cleanly stop a Timer which is running.
     *
     * @author kelly.applebee@studio.unibo.it
     */
    public void stopAnimationTimer() {
        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
            animationTimer = null; // Release reference
        }
    }

    /**
     * Checks if the animation is currently running.
     *
     * @return true if the animation is running, false otherwise
     */
    public final boolean isAnimationRunning() {
        return animationTimer != null && animationTimer.isRunning();
    }

    private void animatePhysicalMove(
            final Position attackerStartPos,
            final Position targetStartPos,
            final boolean isPlayerAttacker,
            final int attackPower,
            final Runnable onComplete) {
        this.stopAnimationTimer();

        final int moveDirection = isPlayerAttacker ? 1 : -1;
        final int returnDirection = -moveDirection;
        final int meleeCheckDistance = 1;

        final Position[] currentAttackerPos = {
            new Position(attackerStartPos.x(), attackerStartPos.y()) };
        final Position[] currentTargetPos = {
            new Position(targetStartPos.x(), targetStartPos.y()) };

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
        this.animationTimer.addActionListener(e -> {

            final Position nextAttackerPos;
            final Position nextTargetPos;

            // --- State Logic ---
            switch (state[0]) {
                case 0 -> {
                    this.meleeCommand = new MeleeButton(
                            currentAttackerPos[0],
                            currentTargetPos[0],
                            moveDirection);
                    final List<Position> result = this.meleeCommand.execute();
                    nextAttackerPos = result.get(0);
                    nextTargetPos = result.get(1);
                    if (this.neighbours.neighbours(
                    nextAttackerPos, nextTargetPos, meleeCheckDistance)
                            || !nextTargetPos.equals(currentTargetPos[0])) {
                        state[0] = 1;
                    } else if (nextAttackerPos.equals(currentAttackerPos[0])) {
                        if (this.neighbours.neighbours(
                                nextAttackerPos,
                                nextTargetPos,
                                meleeCheckDistance + 1)) {
                            state[0] = 1;
                        } else {
                            state[0] = 1;
                        }
                    }
                    currentAttackerPos[0] = nextAttackerPos;
                    currentTargetPos[0] = nextTargetPos;
                }
                case 1 -> {
                    if (!damageApplied[0]) {
                        final int remaining = this.model.
                                applyAttackHealth(
                                    isPlayerAttacker, attackPower);
                        if (isPlayerAttacker) {
                            view.updateEnemyHealth(remaining);
                        } else {
                            view.updatePlayerHealth(remaining);
                        }
                        damageApplied[0] = true;
                    }
                    nextAttackerPos = new Position(
                            currentAttackerPos[0].x() + returnDirection,
                            currentAttackerPos[0].y());
                    nextTargetPos = new Position(
                            currentTargetPos[0].x() + returnDirection,
                            currentTargetPos[0].y());
                    currentAttackerPos[0] = nextAttackerPos;
                    currentTargetPos[0] = nextTargetPos;
                    state[0] = 2;
                }
            /*
            Da cambiare */
                default -> {
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
                        nextAttackerPos = new Position(
                                currentAttackerPos[0].x() + returnDirection,
                                currentAttackerPos[0].y());
                        currentAttackerPos[0] = nextAttackerPos;
                    }
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

    /**
     * Performs a super attack by the enemy.
     */
    public final void performEnemySuperAttack() {

        final Runnable onSuperAttackComplete = () -> {
            this.currentState.handleAnimationComplete(this);
        };
        // Now start the animation itself
        final int superAttackPower = model.getEnemyPower() * 2;
        animatePhysicalMove(
            model.getEnemyPosition(),
            model.getPlayerPosition(),
            false, // isPlayerAttacker
            superAttackPower,
            onSuperAttackComplete
        );

    }

    /**
     * Performs a physical attack by the enemy.
     * This method is called when the enemy performs a physical attack.
     * It checks if it's the enemy's turn
     * also if an animation is not already running.
     * If conditions are met, it animates the physical move
     * then handles the attack completion.
     */
    public final void performEnemyPhysicalAttack() {
        view.clearInfo();
        view.showInfo("Enemy attacks!");
        final Runnable onEnemyAttackComplete = () -> {
            currentState.handleAnimationComplete(this);
        };
        this.animatePhysicalMove(
                model.getEnemyPosition(),
                model.getPlayerPosition(),
                false, // isPlayerAttacker
                model.getEnemyPower(),
                onEnemyAttackComplete);
    }

    private void performInfoZoomInAnimation(final Runnable onZoomComplete) {
        this.stopAnimationTimer();
        this.view.setAllButtonsDisabled();

        final int size = (model.getSize() / 2) - 2; // Target size for zoom

        final int targetX = (model.getSize() / 2);

        this.animationTimer = new Timer(INFO_ZOOM_DELAY, e -> {
            final Position currentEnemyPosition = model.getEnemyPosition();
            if (currentEnemyPosition.x() <= targetX) {
                stopAnimationTimer(); // Increase offset for zoom effect
                model.setEnemyPosition(
                    new Position(targetX, currentEnemyPosition.y()));
                this.model.setEnemyPosition(
                    new Position(targetX, currentEnemyPosition.y()));
                this.makeBigger(size, onZoomComplete);
            } else {
                model.setEnemyPosition(new Position(
                    model.getEnemyPosition().x() - 1,
                    model.getEnemyPosition().y()));
                final RedrawContext defaultRedraw = new RedrawContext.Builder()
                .player(this.model.getPlayerPosition())
                .enemy(this.model.getEnemyPosition())
                .flame(this.model.getAttackPosition())
                .drawEnemy(true)
                .playerRange(1)
                .enemyRange(1)
                .setIsGameOver(this.model.isGameOver())
                .build();
                this.view.redrawGrid(defaultRedraw);
                /*this.redrawView(this.model.getPlayerPosition(),
                this.model.getEnemyPosition(), this.model.getAttackPosition(),
                0, false, true, false, false, 1, 1,
                this.model.isGameOver(), this.model.isPlayerTurn()
                ? this.model.getEnemyPosition()
                : this.model.getPlayerPosition(), false,
                new ArrayList<>(), false, 0, false, 0); */
            }
        });
        animationTimer.start();
    }

    /**
     * Animates size increase.
     *
     * @param size grandezza necessaria
     * @param onZoomComplete Runnable per fine animazione
     */
    private void makeBigger(final int size, final Runnable onZoomComplete) {
        final int[] conto = {1};
        animationTimer = new Timer(INFO_ZOOM_DELAY, e -> {
            if (conto[0] > size) {
                stopAnimationTimer();
                conto[0] = 0;
                if (onZoomComplete != null) {
                    onZoomComplete.run(); // farà partire nuovo State
                }
            } else {
                final RedrawContext defaultRedraw = new RedrawContext.Builder()
                .player(this.model.getPlayerPosition())
                .enemy(this.model.getEnemyPosition())
                .flame(this.model.getAttackPosition())
                .drawEnemy(true)
                .playerRange(1)
                .enemyRange(conto[0])
                .setIsGameOver(this.model.isGameOver())
                .build();
                this.view.redrawGrid(defaultRedraw);
                conto[0]++;
                /* this.redrawView(this.model.getPlayerPosition(),
                this.model.getEnemyPosition(), this.model.getAttackPosition(),
                0, false, true, false, false, 1, 1,
                this.model.isGameOver(), this.model.isPlayerTurn()
                ? this.model.getEnemyPosition()
                : this.model.getPlayerPosition(), false,
                new ArrayList<>(), false, 0, false, 0);*/
            }
        });
        animationTimer.start();
    }

    /**
     * Animates poison damage effect.
     * This method animates the poison damage effect on the affected character.
     */
    public final void animatePoisonDamage() {
        this.stopAnimationTimer();
        final int[] step = {4};
        this.animationTimer = new Timer(INFO_NEXT_DRAW_DELAY, e -> {
            if (step[0] == 1) {
                step[0]--;
                this.stopAnimationTimer();
                this.redrawView();

                final int remaining = model.applyAttackHealth(
                    this.model.isPlayerTurn(),
                    this.model.getPlayerPoisonPower()
                    );

                if (this.model.isPlayerTurn() && !this.checkGameOver()) {
                    view.updateEnemyHealth(remaining);
                    this.setState(this.enemyState);
                } else {
                    view.updatePlayerHealth(remaining);
                    this.setState(new PlayerTurnState());
                }
            } else {
                final RedrawContext defaultRedraw = new RedrawContext.Builder()
                .player(this.model.getPlayerPosition())
                .enemy(this.model.getEnemyPosition())
                .flame(this.model.getAttackPosition())
                .drawPlayer(true)
                .drawEnemy(true)
                .playerRange(1)
                .enemyRange(1)
                .setIsGameOver(this.model.isGameOver())
                .drawPoisonDamage(true)
                .whoIsPoisoned(this.model.isPlayerTurn()
                    ? this.model.getEnemyPosition()
                    : this.model.getPlayerPosition())
                .poisonYCoord(step[0])
                .build();
                this.view.redrawGrid(defaultRedraw);
                /*redrawView(
                    this.model.getPlayerPosition(),
                    this.model.getEnemyPosition(),
                    this.model.getAttackPosition(),
                    0, true, true, false, false, 1, 1, this.model.isGameOver(),
                    (this.model.isPlayerTurn()
                    ? this.model.getEnemyPosition()
                    : this.model.getPlayerPosition()),
                    false, new ArrayList<Position>(),
                    true, step[0], false, 0);
                    */
                step[0]--;
            }
        });
        this.animationTimer.start();
    }

    /*
    private void infoNextDrawAnimation(final Position originalEnemyPosition) {
        stopAnimationTimer();
        final int defaultZoom = 6;
        animationTimer = new Timer(INFO_NEXT_DRAW_DELAY, e -> {
            zoomerStep++;
            if (zoomerStep >= 6) {
                stopAnimationTimer();
                model.setEnemyPosition(
                    originalEnemyPosition); // Reset enemy position
                redrawView();
                this.view.setAllButtonsEnabled();
                zoomerStep = 0;
            } else {
                this.redrawView(
                    model.getPlayerPosition(),
                    model.getEnemyPosition(),
                    model.getAttackPosition(),
                    0, true, true, false,
                    false, 1, zoomerStep, false, model.getPlayerPosition(),
                    false, new ArrayList<Position>(), false, 0, false, 0);
            }
        });
        animationTimer.start();
    }
    */

    /**
     * Applies post-turn effects such as poison damage.
     * This method checks if the enemy is poisoned and applies poison damage
     * if the enemy is still alive.
     */
    public final void applyPostTurnEffects() {
        if (model.isEnemyPoisoned() && model.getEnemyHealth() > 0) {
            view.showInfo("Enemy take poison damage!");
            model.decreaseEnemyHealth(model.getPlayerPoisonPower());
            view.updateEnemyHealth(model.getEnemyHealth());
            this.animatePoisonDamage();
        }
    }

    /**
     * Checks if the game is over.
     * This method checks if either the player or the enemy has no health left.
     * If the game is over, it stops the animation timer and displays a message.
     *
     * @return true if the game is over, false otherwise
     */
    public final boolean checkGameOver() {
        if (model.isGameOver()) {
            stopAnimationTimer();
            view.setAllButtonsDisabled();
            final String winner =
                model.getPlayerHealth() <= 0 ? "Enemy" : "Player";
            view.showInfo("Game Over! " + winner + " wins!");
            this.setState(new GameOverState(
                combatCollision, gridNotifier, enemy, player));
            return true;
        }
        return false;
    }

    /**
     * Performs the death animation for the player or enemy.
     *
     * @param death Position of the character that died
     * @param isCharging Whether the boss is charging
     * @param onComplete Runnable to execute after animation completes
     */
    public final void performDeathAnimation(
        final Position death,
        final boolean isCharging,
        final Runnable onComplete) {
        // this.animationTimer.setRepeats(false);
        // this.animationTimer.start();

        final int defaultPosition = 4;
        final int defaultTimes = 3;

        final int[] position = {defaultPosition};
        final int[] times = {defaultTimes};

        if (isCharging) {
            // animating State
            this.setState(new AnimatingState());
            this.animationTimer = new Timer(INFO_ZOOM_DELAY, e -> {
                position[0]--;
                final RedrawContext defaultRedraw = new RedrawContext.Builder()
                .player(this.model.getPlayerPosition())
                .enemy(this.model.getEnemyPosition())
                .flame(this.model.getAttackPosition())
                .drawPlayer(true)
                .drawEnemy(true)
                .playerRange(1)
                .enemyRange(1)
                .setIsGameOver(this.model.isGameOver())
                .setIsCharging(isCharging)
                .chargingCellDistance(position[0])
                .build();
                this.view.redrawGrid(defaultRedraw);
                if (position[0] == 0) {
                    times[0]--;
                    if (times[0] == 0) {
                        this.stopAnimationTimer();
                        this.redrawView();
                        if (onComplete != null) {
                            onComplete.run();
                        }
                    } else {
                        position[0] = defaultPosition;
                    }
                }
            });
            this.animationTimer.start();
        }
    }

    // ------ Getters ------
    /**
     * Getters for the model and view.
     *
     * @return the view and model of the combat controller
     */
    public final CombatView getView() {
        return this.view;
    }

    /**
     * Getters for the model and view.
     *
     * @return the model of the combat controller
     */
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
            currentState.handlePotionUsed(this, this.attackBuffItem, null);
            currentState.handleBackInput(this);
            this.player.getInventory().decreaseItemCount(attackBuffItem);
        }
    }

    private void handleHeal() {
        if (this.currentState != null) {
            currentState.handlePotionUsed(this, this.healingItem, null);
            this.view.updatePlayerHealth(this.model.getPlayerHealth());
            currentState.handleBackInput(this);
            this.player.getInventory().decreaseItemCount(healingItem);
            this.redrawView();
        }
    }

    /**
     * Sets the current state of the combat controller.
     * This method handles the transition between states,
     * calling exitState on the old state and enterState on the new one.
     *
     * @param state the new state to set
     */
    public final void setState(final CombatState state) {
        // Temporarily store the old state
        final CombatState oldState = this.currentState;

        if (oldState != null) {
            oldState.exitState(this);
        }

        // Now update to the new state
        this.currentState = state;

        // And call enterState on the new one
        if (this.currentState != null) {
            this.currentState.enterState(this);
        }
    }

    public final void setEncounteredEnemy(Enemy encounteredEnemy) {
        this.enemy = encounteredEnemy;
        this.model.setEnemyState(this.enemy.isBoss());
        this.enemyState = this.model.getEnemyState();
    }

    /**
     * Gets the current state of the combat controller.
     *
     * @return the current state
     */
    public final CombatState getCurrentState() {
        return currentState;
    }

    /**
     * Performs an attack by the enemy.
     * This method randomly selects between a physical attack
     * and a long-range attack for the enemy.
     */
    public final void performEnemyAttack() {
        final int physical = 0;
        final int longRange = 1;
        final int num = new Random().nextInt(2);

        switch (num) {
            case physical -> performEnemyPhysicalAttack();
            case longRange -> performLongRangeAttack(
                    model.getEnemyPosition(),
                    -1,
                    false,
                    true);
            default -> {
            }
        }

    }

    /**
     * Performs a long-range attack by the specified attacker.
     * This method animates the long-range attack and applies any effects
     * such as flame or poison.
     *
     * @param attacker the position of the attacker (player or enemy)
     * @param direction the direction of the attack (1 for player, -1 for enemy)
     * @param applyFlameIntent true if flame effect should be applied
     * @param applyPoisonIntent true if poison effect should be applied
     */
    public final void performLongRangeAttack(
        final Position attacker, final int direction,
        final boolean applyFlameIntent, final boolean applyPoisonIntent) {
        // Pass the intent to the animation, AND create the completion runnable

        longRangeAttackAnimation(attacker, direction,
        applyFlameIntent, applyPoisonIntent, () -> {
            if (currentState != null) {
                if (applyPoisonIntent) {
                    if (this.model.isPlayerTurn()) {
                        this.model.setEnemyPoisoned(applyPoisonIntent);
                    } else {
                        this.model.setPlayerPoisoned(applyPoisonIntent);
                    }
                }
                currentState.handleAnimationComplete(this);
            }
        });
    }

    /**
     * Checks if the game is over and updates the view accordingly.
     *
     * @return true if the game is over, false otherwise.
     */
    public final boolean checkGameOverAndUpdateView() {
        if (model.isGameOver()) {
            stopAnimationTimer(); // Controller still manages timers directly
            // Game over display logic is
            // now handled by GameOverState.enterState
            return true;
        }
        return false;
    }

    /**
     * Performs the poison effect animation.
     * This method animates the poison effect on the affected character.
     */
    public final void performPoisonEffectAnimation() {
        stopAnimationTimer();
        final int[] conto = {4};
        // array perché così posso dichiararlo
        // final usarlo nel Timer se no sarebbe stato più scomodo
        model.setPoisonAnimation(true);
        animationTimer = new Timer(INFO_NEXT_DRAW_DELAY,
        (final ActionEvent e) -> {
            // perché così potevo vedere da tablet che laggava ahahahahaha
            if (conto[0] == 1) {
                // fine del timer resetto tutto
                conto[0] = 0;
                stopAnimationTimer();
                redrawView();
                model.setPoisonAnimation(false);
                // this.currentState.handleAnimationComplete(this);
                // chiamo la funzione che tratta la fine delle animazioni
                final int remaining = model.applyAttackHealth(
                    this.model.isPlayerTurn(),
                    this.model.getPlayerPoisonPower()
                    );

                if (this.model.isPlayerTurn() && !this.checkGameOver()) {
                    this.model.setPlayerTurn(false);
                    view.updateEnemyHealth(remaining);
                    CombatController.this.setState(this.enemyState);
                } else {
                    CombatController.this.model.setPlayerTurn(true);
                    view.updatePlayerHealth(remaining);
                    CombatController.this.setState(new PlayerTurnState());
                }
                //this.currentState.stateChange(this);
            } else {
                // ridisegno tutto con il veleno che sale
                final RedrawContext defaultRedraw =
                    new RedrawContext.Builder()
                        .player(CombatController.this.model.getPlayerPosition())
                        .enemy(CombatController.this.model.getEnemyPosition())
                        .flame(CombatController.this.model.getAttackPosition())
                        .drawPlayer(true).drawEnemy(true)
                        .playerRange(1).enemyRange(1)
                        .drawPoisonDamage(true).poisonYCoord(conto[0])
                        .setIsGameOver(CombatController.this.model.isGameOver())
                        .whoIsPoisoned(
                            CombatController.this.model.isPlayerTurn()
                                ? CombatController.this.model.getEnemyPosition()
                                : CombatController.this.model
                                .getPlayerPosition()).build();
                CombatController.this.view.redrawGrid(defaultRedraw);
                /* this.redrawView(this.model.getPlayerPosition(),
                this.model.getEnemyPosition(),
                this.model.getAttackPosition(), 0, true, true,
                false, false, 1, 1,
                this.model.isGameOver(), this.model.getWhoDied(),
                false, new ArrayList<>(), true,
                conto[0], false, 0);*/
                // faccio salire il veleno
                conto[0]--;
            }
        });
        animationTimer.start(); // faccio partire il timer
        //(finisce tutte le prossime chiamate poi fa partire
        //il timer non è coe un for (lo so è strano))f

    }

    public final void resetForNewCombat() {
        this.model.setPlayerMaxHp(this.player.getMaxHp());
        // this.model.setPlayerCurrentHp(this.player.getCurrentHp());
        this.view.setPlayerHealthBarMax(model.getPlayerMaxHealth());
        System.out.println("Max Helth => " + this.model.getPlayerMaxHealth());
        this.view.setEnemyHealthBarMax(this.model.getEnemyMaxHealth());
        this.view.updateEnemyHealth(this.model.getEnemyHealth());
        this.model.resetPositions();
        this.setState(new PlayerTurnState());
        this.view.updatePlayerHealth(this.model.getPlayerHealth());
        this.view.updateEnemyHealth(this.model.getEnemyHealth());
    }

}
