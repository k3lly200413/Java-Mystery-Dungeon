package it.unibo.progetto_oop;

import javax.swing.SwingUtilities;

import it.unibo.progetto_oop.combat.CombatLauncher;
import it.unibo.progetto_oop.combat.game_over_view.GameOverPanel;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatPresenter;
import it.unibo.progetto_oop.combat.win_view.WinPanel;
import it.unibo.progetto_oop.overworld.mvc.ViewManager;
import it.unibo.progetto_oop.overworld.mvc.generation_entities.EntityStatsConfig;
import it.unibo.progetto_oop.overworld.playground.OverworldLauncher;
import it.unibo.progetto_oop.overworld.playground.data.FloorConfig;
import it.unibo.progetto_oop.overworld.playground.view.game_start.GameStartView;

/**
 * The GameLauncher class is responsible for starting the game.
 * It initializes the main components and views, and manages the transition
 * between the overworld and combat modes.
 */
public final class GameLauncher {
    /**
     * Configuration for the floor settings.
     */
    private final FloorConfig floorConfig = new FloorConfig.Builder().build();

    /**
     * Configuration for the entity stats.
     */
    private final EntityStatsConfig entityStatsConfig = new EntityStatsConfig.Builder().build();

    /**
     * Starts the game by initializing the main game components and views.
     */
    public void start() {
        SwingUtilities.invokeLater(() -> {
            // Schermata iniziale
            final GameStartView startView = new GameStartView();
            final ViewManager viewManager = new ViewManager();

            viewManager.start(startView);

            startView.setOnStart(() -> {
                final OverworldLauncher session = new OverworldLauncher(
                        floorConfig, entityStatsConfig);
                final CombatPresenter combatPresenter = session.buildCombat(new CombatLauncher());

                final GameOverPanel gameOverPanel = new GameOverPanel(() -> {
                    combatPresenter.restartGame();
                });

                final WinPanel winPanel = new WinPanel(() -> {
                    combatPresenter.restartGame();
                });

                session.attachPlaygroundView(viewManager);
                viewManager.setCombatPresenter(combatPresenter);
                viewManager.setGameOverPanel(gameOverPanel);
                viewManager.setWinPanel(winPanel);

                session.wireOverworldController(viewManager);

                session.start();
                viewManager.showOverworld();
            });
        });
    }
}
