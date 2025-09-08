package it.unibo.progetto_oop.combat.state_pattern;

import javax.swing.Timer;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision.CombatCollision;
import it.unibo.progetto_oop.Overworld.GridNotifier.GridNotifier;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.combat.combat_builder.RedrawContext;
import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;

public class GameOverState implements CombatState {

    private CombatCollision combatCollision;

    private GridNotifier gridNotifier;

    private Enemy enemy;

    public GameOverState(final CombatCollision combatCollision, final GridNotifier gridNotifier, final Enemy enemy) {
        this.combatCollision = combatCollision;
        this.gridNotifier = gridNotifier;
        this.enemy = enemy;
    }

    /**
     *
     * @param context Istance of the controller
     *
     *                This method is called when entering a combat state.
     */
    @Override
    public void enterState(final CombatController context) {
        Timer enemyActionTimer = new Timer(700, e -> {
            if (context.getModel().getPlayerHealth() <= 0) {
                context.getView().showGameOver(() -> {
                    // TODO: qui in futuro resetta il Model e cambia stato, es:
                    // context.restartMatch();
                    // context.setState(new PlayerTurnState());
                });
            } else if (context.getModel().getEnemyHealth() <= 0) {
                gridNotifier.notifyEnemyRemoved(enemy.getCurrentPosition());
                gridNotifier.notifyListEnemyRemoved(enemy.getCurrentPosition());
                combatCollision.setInCombat(false);
                context.getView().showInfo("You Win! Returning to Overworld...");
                this.combatCollision.showOverworld();

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
     *
     * @param context Istance of the controller
     *
     *                This method is called when exiting a combat state.
     */
    @Override
    public void exitState(final CombatController context) {
        context.getView().showInfo("Exiting Game Over State");
    }

    /**
     *
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
