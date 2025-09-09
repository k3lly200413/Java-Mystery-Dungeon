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

    /** Combat collision instance. */
    private final CombatCollision combatCollision;

    /** Grid notifier instance. */
    private final GridNotifier gridNotifier;

    /** Enemy instance. */
    private final Enemy enemy;

    /** User player instance. */
    private final PossibleUser userPlayer;

    /** Amount to increase player stats. */
    private final int increaseAmount = 5;

    /** Timer duration in milliseconds. */
    private final int timerDuration = 700;

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
        final Timer enemyActionTimer = new Timer(timerDuration, e -> {
            if (context.getModel().getPlayerHealth() <= 0) {
                combatCollision.showGameOver();
            } else if (context.getModel().getEnemyHealth() <= 0) {
                userPlayer.increasePlayerMaxPower(increaseAmount);
                userPlayer.increasePlayerMaxHealth(increaseAmount);
                userPlayer.increasePlayerMaxStamina(increaseAmount);
                // userPlayer.increasePlayerHealth(increaseAmount);
                gridNotifier.notifyEnemyRemoved(enemy.getCurrentPosition());
                gridNotifier.notifyListEnemyRemoved(enemy.getCurrentPosition());
                combatCollision.setInCombat(false);
                // context.getView().close();
                context.getView().showInfo(
                    "You Win! Returning to Overworld...");
                this.combatCollision.showOverworld();
            } else {
                combatCollision.setInCombat(false);
                this.combatCollision.showOverworld();
                this.enemy.setHp(context.getModel().getEnemyHealth());
                System.out.println(
                    "Enemy health after combat => " + enemy.getCurrentHp());
            }
        });
        enemyActionTimer.setRepeats(false);
        enemyActionTimer.start();

        final RedrawContext defaultRedraw = new RedrawContext.Builder()
                .player(context.getModel().getPlayerPosition())
                .enemy(context.getModel().getEnemyPosition())
                .flame(context.getModel().getAttackPosition())
                .drawPlayer(true)
                .drawEnemy(true)
                .playerRange(2)
                .enemyRange(2)
                .setIsGameOver(context.getModel().isGameOver())
                .whoDied(context.getModel().getWhoDied())
                .build();
        context.getView().redrawGrid(defaultRedraw);
    }

    /**
     * @param context Istance of the controller
     *
     *                This method is called when exiting a combat state.
     */
    @Override
    public void exitState(final CombatController context) {
        context.getView().showInfo("Exiting Game Over State");
    }

    /**
     * @param context Istance of the controller
     *
     *                This method is called when an animation is complete during
     *                combat.
     */
    @Override
    public void handleAnimationComplete(final CombatController context) {
        context.getView().showInfo("Animation Complete in Game Over State");
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
    public void handlePotionUsed(final CombatController context,
            final Item selectedPotion, final Player player) {
        // TODO Auto-generated method stub

    }

    @Override
    public void handleRunInput(final CombatController context) {
        // TODO Auto-generated method stub

    }

}
