package it.unibo.progetto_oop;

import java.util.Random;

import javax.swing.SwingUtilities;

import it.unibo.progetto_oop.combat.CombatLauncher;
import it.unibo.progetto_oop.combat.game_over_view.GameOverPanel;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.overworld.mvc.EntityStatsConfig;
import it.unibo.progetto_oop.overworld.mvc.OverworldController;
import it.unibo.progetto_oop.overworld.mvc.ViewManager;
import it.unibo.progetto_oop.overworld.playground.OverworldLuncher;
import it.unibo.progetto_oop.overworld.playground.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playground.view.GameStartView;

public final class GameLuncher {
    /**
     * Configuration for the floor settings.
     */
    private final FloorConfig floorConfig = new FloorConfig.Builder().build();

    /**
     * Configuration for the entity stats.
     */
    private final EntityStatsConfig entityStatsConfig =
        new EntityStatsConfig.Builder().build();

    /**
     * Random number generator for game logic.
     */
    private final Random rand = new Random();

    public void start() {
        SwingUtilities.invokeLater(() -> {
            // Schermata iniziale
            GameStartView startView = new GameStartView();
            ViewManager viewManager = new ViewManager();

            viewManager.start(startView);

            startView.setOnStart(() -> {
                OverworldLuncher session = new OverworldLuncher(floorConfig, entityStatsConfig, rand);

                CombatController combatController = 
                new CombatLauncher().buildCombat(
                    session.getModel().getPlayer(), 
                    session.getModel().getCombatCollision(),
                    session.getModel().getGridNotifier()
                );
                GameOverPanel gameOverPanel = new GameOverPanel(() -> {
                    combatController.restartGame();
                });
                viewManager.setPlayGroundView(session.getView());
                viewManager.setCombatController(combatController);
                viewManager.setGameOverPanel(gameOverPanel);
                OverworldController overworldController = new OverworldController(session.getModel(), session.getView(), viewManager);
                session.getModel().setCombatTransitionListener(overworldController);
                session.start();
                viewManager.showOverworld();
            });
        });
    }
}
