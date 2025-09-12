package it.unibo.progetto_oop.combat.state_pattern;

import javax.swing.Timer;

import it.unibo.progetto_oop.combat.combat_builder.RedrawContext;
import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.overworld.combat_collision.CombatCollision;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.player.adapter_pattern.OverworldPlayerAdapter;
import it.unibo.progetto_oop.overworld.player.adapter_pattern.PossibleUser;

/**
 * This class represents the Game Over state in a combat scenario.
 * It implements the CombatState interface and defines the behavior
 * when the game is over, either due to the player's defeat or victory.
 */
public class GameOverState implements CombatState {

    /** Amount to increase player stats. */
    private static final int INCREASE_AMOUNT = 5;

    /** Timer duration in milliseconds. */
    private static final int TIMER_DURATION = 700;

    /** Combat collision instance. */
    private final CombatCollision combatCollision;

    /** Grid notifier instance. */
    private final GridNotifier gridNotifier;

    /** Enemy instance. */
    private final Enemy enemy;

    /** User player instance. */
    private final PossibleUser userPlayer;

    /**
     * @param newCombatCollision    Instance of the CombatCollision
     * @param newGridNotifier        Instance of the GridNotifier
     * @param newEnemy               Instance of the Enemy
     * @param player                 Instance of the Player
     *
     *                               Constructor of the GameOverState class.
     */
    public GameOverState(final CombatCollision newCombatCollision,
    final GridNotifier newGridNotifier,
    final Enemy newEnemy, final Player player) {
        this.combatCollision = newCombatCollision;
        this.gridNotifier = newGridNotifier;
        this.enemy = newEnemy;
        this.userPlayer = new OverworldPlayerAdapter(player);
    }

    /**
     * @param context Istance of the controller
     *
     *                This method is called when entering a combat state.
     */
    @Override
    public void enterState(final CombatController context) {
        final Timer enemyActionTimer = new Timer(TIMER_DURATION, e -> {
            if (context.getReadOnlyModel().getPlayerHealth() <= 0) {
                combatCollision.showGameOver();
            } else if (context.getReadOnlyModel().getEnemyHealth() <= 0) {
                if (context.getReadOnlyModel()
                .getEnemyState() instanceof EnemyTurnState) {
                    userPlayer.increasePlayerMaxPower(INCREASE_AMOUNT);
                    userPlayer.increasePlayerMaxHealth(INCREASE_AMOUNT);
                    userPlayer.increasePlayerMaxStamina(INCREASE_AMOUNT);
                    gridNotifier.notifyEnemyRemoved(enemy.getCurrentPosition());
                    gridNotifier.notifyListEnemyRemoved(
                        enemy.getCurrentPosition());
                    combatCollision.setInCombat(false);
                    context.getViewApi().showInfo(
                        "You Win! Returning to Overworld...");
                    this.combatCollision.showOverworld();
                } else {
                    gridNotifier.notifyEnemyRemoved(enemy.getCurrentPosition());
                    gridNotifier.notifyListEnemyRemoved(
                        enemy.getCurrentPosition());
                    combatCollision.setInCombat(false);
                    this.combatCollision.showWin();
                }
            } else {
                combatCollision.setInCombat(false);
                this.combatCollision.showOverworld();
                this.enemy.setHp(context.getReadOnlyModel().getEnemyHealth());
                }
        });
        enemyActionTimer.setRepeats(false);
        enemyActionTimer.start();

        final RedrawContext defaultRedraw = new RedrawContext.Builder()
                .player(context.getReadOnlyModel().getPlayerPosition())
                .enemy(context.getReadOnlyModel().getEnemyPosition())
                .flame(context.getReadOnlyModel().getAttackPosition())
                .drawPlayer(true)
                .drawEnemy(true)
                .playerRange(2)
                .enemyRange(2)
                .setIsGameOver(context.getReadOnlyModel().isGameOver())
                .whoDied(context.getReadOnlyModel().getWhoDied())
                .build();
        context.getViewApi().updateDisplay(defaultRedraw);
    }

    /**
     * @param context Istance of the controller
     *
     *                This method is called when exiting a combat state.
     */
    @Override
    public void exitState(final CombatController context) {
        context.getViewApi().showInfo("Exiting Game Over State");
    }

    /**
     * @param context Istance of the controller
     *
     *                This method is called when an animation is complete during
     *                combat.
     */
    @Override
    public void handleAnimationComplete(final CombatController context) {
        context.getViewApi().showInfo("Animation Complete in Game Over State");
    }

    @Override
    public void handleAttackBuffInput(final CombatController context) {
        // TODO Auto-generated method stub
    }

    @Override
    public void handleBackInput(final CombatController context) {
        // TODO Auto-generated method stub
    }

    @Override
    public void handleBagInput(final CombatController context) {
        // TODO Auto-generated method stub
    }

    @Override
    public void handleCurePoisonInput(final CombatController context) {
        // TODO Auto-generated method stub
    }

    @Override
    public void handleHealInput(final CombatController context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleInfoInput(final CombatController context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleLongRangeAttackInput(final CombatController context,
            final boolean isPoison, final boolean isFlame) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handlePhysicalAttackInput(final CombatController context) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handlePotionUsed(final PossibleUser user,
            final Item selectedPotion, final Player player) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleRunInput(final CombatController context) {
        // TODO Auto-generated method stub

    }

}
