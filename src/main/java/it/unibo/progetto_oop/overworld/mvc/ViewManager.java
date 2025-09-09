package it.unibo.progetto_oop.overworld.mvc;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import it.unibo.progetto_oop.combat.game_over_view.GameOverPanel;
import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.combat.inventory.InventoryView;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatView;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.playground.view.GameStartView;
import it.unibo.progetto_oop.overworld.playground.view.SwingMapView;

public class ViewManager {
    private static final String START_GAME = "START GAME";
    public static final String INVENTORY_CARD = "INVENTORY";
    public static final String OVERWORLD_CARD = "OVERWORLD";
    public static final String COMBAT_CARD = "COMBAT";
    private static final String GAME_OVER = "GAME OVER";

    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainCardPanel;
    
    private InventoryView invView;
    private CombatView combatView;
    private SwingMapView playGroundView;

    private GameStartView startView;
    //private GameOverView gameOverView;

    private CombatController combatController;

    private GameOverPanel gameOverPanel;

    /**
     * Method to start the view manager with the initial start view.
     * @param startView the start view to display
     */
    public void start(GameStartView startView) {
        this.startView = startView;
        this.frame = new JFrame("Java Mystery Dungeon");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setup il CardLayout e il panel principale
        this.cardLayout = new CardLayout();
        this.mainCardPanel = new JPanel(cardLayout);

        // prima card
        this.mainCardPanel.add(this.startView, START_GAME);
        this.frame.setContentPane(this.mainCardPanel);
        this.frame.setPreferredSize(new Dimension(1000, 700));
        this.mainCardPanel.setMinimumSize(new Dimension(960, 640));
        this.frame.pack();
        this.frame.setVisible(true);

        // Mostro start game come default
        this.cardLayout.show(this.mainCardPanel, START_GAME);
    }

    public void showOverworld() {
        this.cardLayout.show(this.mainCardPanel, OVERWORLD_CARD);
    }

    public void setPlayGroundView(SwingMapView playGroundView) {
        this.playGroundView = playGroundView;
        this.mainCardPanel.add(this.playGroundView, OVERWORLD_CARD);
    }

    /**
     * Method to set the inventory view.
     * @param newInvView the inventory view to set
     */
    public void setInventoryView(final InventoryView newInvView) {
        this.invView = newInvView;
        this.mainCardPanel.add(this.invView, INVENTORY_CARD);
    }

    public void setCombatController(CombatController currentCombatController) {
        this.combatController = currentCombatController;
        this.mainCardPanel.add(combatController.getView(), COMBAT_CARD);
    }


    /**
     * Method to show the inventory view.
     * @param inventory the inventory to display
     */
    public void showInventory(final Inventory inventory) {
        if (this.invView == null) {  // prima volta
            this.setInventoryView(new InventoryView(inventory, this));
        } else { // aggiorna la view esistente
            this.invView.updateInventoryModel(inventory);
            this.invView.refreshView();
        }
        this.cardLayout.show(this.mainCardPanel, INVENTORY_CARD);
    }

    /*
    public void showInventory(Inventory inventory) {
        JFrame frame = new JFrame("Inventario");

        if (this.invView == null) {
            this.invView = new InventoryView(inventory, this);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 400);
            frame.add(this.invView);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

        } else {
            this.invView.updateInventoryModel(inventory); 
            this.invView.refreshView(); 
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 400);
            frame.add(this.invView);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    } */

    public void showCombat(Enemy encounteredEnemy) {
        JFrame frame = new JFrame("Combattimento");
        // if (this.combatView == null) {
            // frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            // frame.setSize(600, 400);
            // frame.add(this.combatController.getView());
            // frame.setLocationRelativeTo(null);
            // frame.setVisible(true);
        // } else {
        this.combatController.setEncounteredEnemy(encounteredEnemy);
        this.combatController.getModel().setEnemyCurrentHp(encounteredEnemy.getCurrentHp());
        System.out.println("Enemy Health => " + encounteredEnemy.getCurrentHp());
        this.combatController.getModel().setEnemyMaxHp(encounteredEnemy.getMaxHp());
        this.combatController.resetForNewCombat();
        this.combatController.redrawView();
        // }
        this.cardLayout.show(this.mainCardPanel, COMBAT_CARD);
    }

    public void setGameOverPanel(final GameOverPanel newGameOverPanel) {
        this.gameOverPanel = newGameOverPanel;
        this.mainCardPanel.add(this.gameOverPanel, GAME_OVER);
    }

    public void showGameOver() {
        this.cardLayout.show(this.mainCardPanel, GAME_OVER);
    }
}