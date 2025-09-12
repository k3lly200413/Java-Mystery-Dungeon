package it.unibo.progetto_oop;

import java.util.Random;

import javax.swing.SwingUtilities;

import it.unibo.progetto_oop.combat.CombatLauncher;
import it.unibo.progetto_oop.combat.game_over_view.GameOverPanel;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.win_view.WinPanel;
import it.unibo.progetto_oop.overworld.mvc.OverworldController;
import it.unibo.progetto_oop.overworld.mvc.ViewManager;
import it.unibo.progetto_oop.overworld.mvc.generation_entities.EntityStatsConfig;
import it.unibo.progetto_oop.overworld.playground.OverworldLauncher;
import it.unibo.progetto_oop.overworld.playground.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playground.view.game_start.GameStartView;

public final class GameLauncher {
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

    /**
     * Starts the game by initializing the main game components and views.
     */
    public void start() {
        SwingUtilities.invokeLater(() -> {
            // Schermata iniziale
            GameStartView startView = new GameStartView();
            ViewManager viewManager = new ViewManager();

            viewManager.start(startView);

            startView.setOnStart(() -> {
                OverworldLauncher session = new OverworldLauncher(
                    floorConfig, entityStatsConfig, rand
                );

                final CombatController combatController =
                new CombatLauncher().buildCombat(
                    session.getModel().getPlayer(),
                    session.getModel().getCombatCollision(),
                    session.getModel().getGridNotifier()
                );
                GameOverPanel gameOverPanel = new GameOverPanel(() -> {
                    combatController.restartGame();
                });

                WinPanel winPanel = new WinPanel(() -> {
                    combatController.restartGame();
                });
                viewManager.setPlayGroundView(session.getView());
                viewManager.setCombatController(combatController);
                viewManager.setGameOverPanel(gameOverPanel);
                viewManager.setWinPanel(winPanel);
                OverworldController overworldController =
                    new OverworldController(
                        session.getModel(),
                        session.getView(),
                        viewManager
                );
                session.getModel()
                    .setCombatTransitionListener(overworldController);
                session.start();
                viewManager.showOverworld();
            });
        });
    }
}
