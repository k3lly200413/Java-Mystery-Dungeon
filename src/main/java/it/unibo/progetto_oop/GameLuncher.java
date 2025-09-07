package it.unibo.progetto_oop;

import java.util.Random;
import javax.swing.SwingUtilities;

import it.unibo.progetto_oop.Overworld.MVC.OverworldController;
import it.unibo.progetto_oop.Overworld.MVC.ViewManager;
import it.unibo.progetto_oop.Overworld.PlayGround.OverworldLuncher;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.FloorConfig;
import it.unibo.progetto_oop.Overworld.PlayGround.view.GameStartView;

public final class GameLuncher {
    private final FloorConfig config = new FloorConfig.Builder().build();
    private final Random rand = new Random();

    public void start() {
        SwingUtilities.invokeLater(() -> {
            // Schermata iniziale
            GameStartView startView = new GameStartView();
            ViewManager viewManager = new ViewManager();
            viewManager.start(startView);

            startView.setOnStart(() -> {
                OverworldLuncher session = new OverworldLuncher(config, rand);

                viewManager.setPlayGroundView(session.getView());
                new OverworldController(session.getModel(), session.getView(), viewManager);
                session.start();
                viewManager.showOverworld();
            });
        });
    }
}
