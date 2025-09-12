package it.unibo.progetto_oop.combat.mvc_pattern;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.progetto_oop.GameLauncher;
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

/**
 * Controller class in Model View Controller Pattern.
 *
 * @author Kelly.applebee@studio.unibo.it
 * @author matteo.monari6@studio.unibo.it
 */
public class CombatController implements CombatControllerApi {

    /**
     * Animation delay for each step in the animation.
     */
    private static final int ANIMATION_DELAY = 100;
    /**
     * Delay for the info zoom animation.
     */
    private static final int INFO_ZOOM_DELAY = 200;
    /**
     * Delay for the next draw in the info zoom animation.
     */
    private static final int INFO_NEXT_DRAW_DELAY = 300;
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
     * Random instance for any randomization needs.
     */
    private static final Random RANDOM = new Random();

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
    private final CombatViewInterface view;
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

    /** State representing the enemy's turn. */
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
     */
    @SuppressFBWarnings(
        value = "EI_EXPOSE_REP2",
        justification =
        "CombatController must keep a live reference to the CombatCollision "
        + "to manage combat lifecycle (setInCombat etc.). "
        + "The controller is the logical owner of this object at runtime."
    )
    public CombatController(
        final CombatModel modelToUse,
        final CombatViewInterface viewToUse, final Player newPlayer,
        final CombatCollision newCombatCollision,
        final GridNotifier newGridNotifier) {

        this.model = modelToUse;
        this.view = viewToUse;
        this.view.setController(this);
        this.neighbours = new Neighbours();

        this.view.setPlayerHealthBarMax(model.getPlayerMaxHealth());
        this.view.setEnemyHealthBarMax(this.model.getEnemyMaxHealth());
        this.view.updatePlayerHealth(model.getPlayerHealth());
        this.view.updateEnemyHealth(model.getEnemyHealth());

        this.combatCollision = newCombatCollision;
        this.gridNotifier = newGridNotifier;

        this.redrawView();

        this.currentState = new PlayerTurnState();

        this.itemFactory = new ItemFactory();
        this.player = newPlayer;
        this.attackBuffItem = itemFactory.createItem("Attack Buff", null);
        this.curePoisonItem = itemFactory.createItem("Antidote", null);
        this.healingItem = itemFactory.createItem("Health Potion", null);
        this.view.setAllMenusEnabled();
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
    @Override
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

        this.view.updateDisplay(defaultRedraw);
    }

    /**
     * Exits the combat and transitions to the game over state.
     */
    public void exitCombat() {
        stopAnimationTimer();
        if (enemyActionTimer != null && enemyActionTimer.isRunning()) {
            enemyActionTimer.stop();
        }
        enemyActionTimer = null;
        combatCollision.setInCombat(false);
        this.setState(new GameOverState(
            combatCollision, gridNotifier, enemy, player));
    }

    /**
     * Used in View to show the attack sub-menu.
     */
    public void handleAttackMenu() {
        this.view.showAttackMenu();
        if (this.model.getPlayerStamina()
        < MINIMUM_STAMINA_FOR_SPECIAL_ATTACK) {
            this.view.setActionEnabled(ActionType.LONG_RANGE, false);
            this.view.setActionEnabled(ActionType.POISON, false);
        }

    }

    /**
     * Used in View to show the bag sub-menu.
     */
    public void handleBagMenu() {
        this.setState(new ItemSelectionState());
        this.view.showBagMenu();
        if (!this.player.getInventory().canUseItem(attackBuffItem)) {
            this.view.setActionEnabled(ActionType.ATTACK_BUFF, false);
        } else {
            this.view.setActionEnabled(ActionType.ATTACK_BUFF, true);
        }

        if (!this.player.getInventory().canUseItem(curePoisonItem)) {
            this.view.setActionEnabled(ActionType.CURE_POISON, false);
        } else {
            this.view.setActionEnabled(ActionType.CURE_POISON, true);
        }

        if (!this.player.getInventory().canUseItem(healingItem)) {
            this.view.setActionEnabled(ActionType.HEAL, false);
        } else {
            this.view.setActionEnabled(ActionType.HEAL, true);
        }

        view.clearInfo();
    }

    /**
     * Used in View to go back to the main menu.
     */
    public void handleBackToMainMenu() {
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
        view.showMainMenu();
        this.model.resetPositions();
        this.redrawView();
        this.setState(new PlayerTurnState());
    }

    /**
     * Handles the info button click event.
     */
    public void handleInfo() {
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
     * Handles the physical attack button click.
     */
    public void handlePlayerPhysicalAttack() {
        this.currentState.enterState(this);
        this.currentState.handlePhysicalAttackInput(this);
    }

    /**
     * Handles the Cure Poison button click.
     */
    public void handleCurePoisonInput() {
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
                true,
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
        if (enemyActionTimer != null && enemyActionTimer.isRunning()) {
            enemyActionTimer.stop();
        }
        enemyActionTimer = new Timer(delay, e -> {
            if (currentState instanceof EnemyTurnState) {
                action.run();
            }
        });
        enemyActionTimer.setRepeats(false);
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

        final Runnable onEnemyAttackComplete = () -> {
            if (checkGameOver()) {
                return;
            }

            model.setPlayerTurn(this.model.isPlayerTurn());
            view.setAllMenusEnabled();
            view.showInfo("Player's turn!");
            view.showMainMenu();
        };

        animatePhysicalMove(
                model.getEnemyPosition(),
                model.getPlayerPosition(),
                false,
                model.getEnemyPower(),
                onEnemyAttackComplete);
    }

    /**
     * Handles the long-range attack button click.
     *
     * @param applyPoison     True if the attack should apply poison
     * @param applyFlameIntent True if the attack should only be a flame
     */
    public void handlePlayerLongRangeAttack(
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
        stopAnimationTimer();

        model.setAttackPosition(new Position(
            attacker.x() + direction, attacker.y()));
        if (!this.checkGameOver()) {
            final RedrawContext redrawContext = new RedrawContext.Builder()
            .player(this.model.getPlayerPosition())
            .enemy(this.model.getEnemyPosition())
            .flame(this.model.getAttackPosition())
            .drawPlayer(true)
            .drawEnemy(true)
            .drawFlame(isAttack)
            .drawPoison(isPoison)
            .playerRange(1)
            .enemyRange(1)
            .setIsGameOver(this.model.isGameOver())
            .build();
            this.view.updateDisplay(redrawContext);
        }

        animationTimer = new Timer(INFO_ZOOM_DELAY, e -> {
            if (model.getAttackPosition().x() > model.getEnemyPosition().x() - 1
                ||
                model.getAttackPosition().x() < model.
                getPlayerPosition().x() + 1) {
                stopAnimationTimer();
                model.setAttackPosition(attacker);
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
                    onHit.run();
                }
                return;
            }

            longRangeCommand = new LongRangeButton(
                this.model.getAttackPosition(), direction);
            final Position nextFlamePos = longRangeCommand.execute().get(0);
            this.model.setAttackPosition(nextFlamePos);
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
            this.view.updateDisplay(defaultRedraw);
        });
        animationTimer.start();
    }

    /**
     * Handles the boss death ray attack.
     * This method is called when the boss unleashes a death ray attack.
     * It should be called from the boss state.
     */
    public void handleBossDeathRayAttack() {
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
            if (currentState != null) {
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
                deathRayLastPosition.clear();
                this.redrawView();

                this.model.decreasePlayerHealth(
                    this.model.getEnemyLongRangePower());
                if (onHit != null) {
                    onHit.run();
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
                this.view.updateDisplay(defaultRedraw);
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
            animationTimer = null;
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
                            || !nextTargetPos.equals(currentTargetPos[0])
                            || nextAttackerPos.equals(currentAttackerPos[0])) {
                        state[0] = 1;
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
        final int superAttackPower = model.getEnemyPower() * 2;
        animatePhysicalMove(
            model.getEnemyPosition(),
            model.getPlayerPosition(),
            false,
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
                false,
                model.getEnemyPower(),
                onEnemyAttackComplete);
    }

    private void performInfoZoomInAnimation(final Runnable onZoomComplete) {
        this.stopAnimationTimer();
        this.view.setAllMenusDisabled();
        final int size = (model.getSize() / 2) - 2;

        final int targetX = model.getSize() / 2;

        this.animationTimer = new Timer(INFO_ZOOM_DELAY, e -> {
            final Position currentEnemyPosition = model.getEnemyPosition();
            if (currentEnemyPosition.x() <= targetX) {
                stopAnimationTimer();
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
                this.view.updateDisplay(defaultRedraw);
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
                    onZoomComplete.run();
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
                this.view.updateDisplay(defaultRedraw);
                conto[0]++;
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
                this.view.updateDisplay(defaultRedraw);
                step[0]--;
            }
        });
        this.animationTimer.start();
    }

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
            view.setAllMenusDisabled();
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

        final int defaultPosition = 4;
        final int defaultTimes = 3;

        final int[] position = {defaultPosition};
        final int[] times = {defaultTimes};

        if (isCharging) {
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
                this.view.updateDisplay(defaultRedraw);
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

    /**
     * Return a narrow view API to avoid exposing the full view implementation.
     *
     * @return a CombatViewApi instance
     */
    public final CombatViewApi getViewApi() {
        final CombatViewInterface backing = this.view;
        return new CombatViewApi() {

            @Override
            public JPanel getViewPanel() {
                return backing.getViewPanel();
            }

            @Override
            public void showInfo(final String text) {
                backing.showInfo(text);
            }

            @Override
            public void clearInfo() {
                backing.clearInfo();
            }

            @Override
            public void updateDisplay(
                final RedrawContext ctx) {
                backing.updateDisplay(ctx);
            }

            @Override
            public void setAllMenusEnabled() {
                backing.setAllMenusEnabled();
            }

            @Override
            public void setAllMenusDisabled() {
                backing.setAllMenusDisabled();
            }

            @Override
            public void setPlayerHealthBarMax(final int max) {
                backing.setPlayerHealthBarMax(max);
            }

            @Override
            public void setEnemyHealthBarMax(final int max) {
                backing.setEnemyHealthBarMax(max);
            }

            @Override
            public void updatePlayerHealth(final int hp) {
                backing.updatePlayerHealth(hp);
            }

            @Override
            public void updateEnemyHealth(final int hp) {
                backing.updateEnemyHealth(hp);
            }

            @Override
            public void showMainMenu() {
                backing.showMainMenu();
            }

            @Override
            public void updatePlayerStamina(final int stamina) {
                backing.updatePlayerStamina(stamina);
            }

            @Override
            public void showAttackMenu() {
                backing.showAttackMenu();
            }

            @Override
            public void setActionEnabled(
                final ActionType action, final boolean enabled) {
                backing.setActionEnabled(action, enabled);
            }

        };
    }

    /**
     * Get the main panel of the view.
     */
    @Override
    public JPanel getViewPanel() {
        return this.view.getViewPanel();
    }

    /**
     * Set enemy HP in the model.
     */
    @Override
    public void setEnemyHp(final int currentHp, final int maxHp) {
        this.model.setEnemyCurrentHp(currentHp);
        this.model.setEnemyMaxHp(maxHp);
    }

    /**
     * Getters for the model and view.
     *
     * @return the model of the combat controller
     */
    @SuppressFBWarnings(
    value = "EI_EXPOSE_REP",
    justification =
        "CombatController returns the internal model by design. " +
        "The controller owns the lifecycle, and external callers cannot break invariants. " +
        "Safe in this context."
    )
    public final CombatModel getModel() {
        return this.model;
    }

    /**
     * Handles the attack buff button click.
     */
    public void handleAttackBuff() {
        if (this.currentState != null) {
            currentState.handlePotionUsed(this, this.attackBuffItem, null);
            currentState.handleBackInput(this);
            this.player.getInventory().decreaseItemCount(attackBuffItem);
        }
    }

    /**
     * Handles the healing button click.
     */
    public void handleHeal() {
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
        final CombatState oldState = this.currentState;

        if (oldState != null) {
            oldState.exitState(this);
        }

        this.currentState = state;

        if (this.currentState != null) {
            this.currentState.enterState(this);
        }
    }

    /**
     * Sets the encountered enemy for the combat.
     * This method updates the model with the new enemy
     * and adjusts the enemy state accordingly.
     *
     * @param encounteredEnemy the enemy to set
     */
    @edu.umd.cs.findbugs.annotations.SuppressFBWarnings(
    value = "EI_EXPOSE_REP2",
    justification =
        "CombatController must keep a live reference to the current Enemy " +
        "to manage combat logic and lifecycle. " +
        "The controller is the logical owner of this object at runtime."
    )
    @Override
    public final void setEncounteredEnemy(final Enemy encounteredEnemy) {
        this.enemy = encounteredEnemy;
        this.model.setEnemyState(this.enemy.isBoss());
        this.enemyState = this.model.getEnemyState();
        this.model.setEnemyPower(enemy.getPower());
    }

    /**
     * Sets the enemy's power level.
     *
     * @param newEnemyPower the new power level for the enemy
     */
    public final void setEnemyPower(final int newEnemyPower) {
        this.model.setEnemyPower(newEnemyPower);
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
        final int num = RANDOM.nextInt(2);

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
            stopAnimationTimer();
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
        model.setPoisonAnimation(true);
        animationTimer = new Timer(INFO_NEXT_DRAW_DELAY,
        (final ActionEvent e) -> {
            if (conto[0] == 1) {
                conto[0] = 0;
                stopAnimationTimer();
                redrawView();
                model.setPoisonAnimation(false);
                final int remaining = model.applyAttackHealth(
                    this.model.isPlayerTurn(),
                    this.model.getPlayerPoisonPower()
                    );

                if (this.model.isPlayerTurn() && !this.checkGameOver()) {
                    this.model.setPlayerTurn(false);
                    view.updateEnemyHealth(remaining);
                    this.setState(this.enemyState);
                } else {
                    this.model.setPlayerTurn(true);
                    view.updatePlayerHealth(remaining);
                    this.setState(new PlayerTurnState());
                }
            } else {
                final RedrawContext defaultRedraw =
                    new RedrawContext.Builder()
                        .player(this.model.getPlayerPosition())
                        .enemy(this.model.getEnemyPosition())
                        .flame(this.model.getAttackPosition())
                        .drawPlayer(true).drawEnemy(true)
                        .playerRange(1).enemyRange(1)
                        .drawPoisonDamage(true).poisonYCoord(conto[0])
                        .setIsGameOver(this.model.isGameOver())
                        .whoIsPoisoned(
                            this.model.isPlayerTurn()
                                ? this.model.getEnemyPosition()
                                : this.model
                                .getPlayerPosition()).build();
                this.view.updateDisplay(defaultRedraw);
                conto[0]--;
            }
        });
        animationTimer.start();

    }

    /**
     * Resets the combat for a new encounter.
     * This method resets the model and view to their initial states
     * for a new combat encounter.
     */
    @Override
    public final void resetForNewCombat() {
        this.model.setPlayerMaxHp(this.player.getMaxHp());
        this.view.setPlayerHealthBarMax(model.getPlayerMaxHealth());
        this.view.setEnemyHealthBarMax(this.model.getEnemyMaxHealth());
        this.view.updateEnemyHealth(this.model.getEnemyHealth());
        this.model.resetPositions();
        this.setState(new PlayerTurnState());
        this.view.updatePlayerHealth(this.model.getPlayerHealth());
        this.view.updateEnemyHealth(this.model.getEnemyHealth());
        this.model.setPlayerPower(this.player.getPower());
        this.model.setPlayerStamina(this.player.getStamina());
        this.view.setPlayerMaxStaminaBar(this.player.getMaxStamina());
        this.view.updatePlayerStamina(this.player.getStamina());
    }

    /**
     * Restarts the game by resetting the model and view.
     */
    public void restartGame() {
    javax.swing.SwingUtilities.invokeLater(() -> {
        final java.awt.Window window = javax.swing.FocusManager.
        getCurrentManager().getActiveWindow();
        if (window != null) {
            window.dispose();
        }

        final GameLauncher app = new GameLauncher();
        app.start();
    });
}

}
