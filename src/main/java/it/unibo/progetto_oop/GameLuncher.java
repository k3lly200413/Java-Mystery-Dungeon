package it.unibo.progetto_oop;

import java.util.Random;
import javax.swing.SwingUtilities;

import it.unibo.progetto_oop.Overworld.PlayGround.OverworldLuncher;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.FloorConfig;
import it.unibo.progetto_oop.Overworld.PlayGround.view.GameStartView;
import it.unibo.progetto_oop.Overworld.mvc.OverworldController;
import it.unibo.progetto_oop.Overworld.mvc.ViewManager;
import it.unibo.progetto_oop.combat.combat_builder.CombatBuilder;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.CombatLauncher;

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

                CombatController combatController = 
                new CombatLauncher().buildCombat(
                    session.getModel().getPlayer(), 
                    session.getModel().getCombatCollision(),
                    session.getModel().getGridNotifier()
                );
                viewManager.setPlayGroundView(session.getView());
                viewManager.setCombatController(combatController);
                OverworldController overworldController = new OverworldController(session.getModel(), session.getView(), viewManager);
                session.getModel().setCombatTransitionListener(overworldController);
                session.start();
                viewManager.showOverworld();
            });
        });
    }
}
