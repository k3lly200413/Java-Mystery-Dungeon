package it.unibo.progetto_oop.Overworld.MVC;

import javax.swing.*;

import java.awt.CardLayout;

import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.combat.inventory.InventoryView;
import it.unibo.progetto_oop.Overworld.PlayGround.view.SwingMapView;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatView;

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
    //private GameStartView startView;
    //private GameOverView gameOverView;

    private CombatController combatController;


    public void start(SwingMapView playGroundView) {
        this.playGroundView = playGroundView;
        this.frame = new JFrame("Java Mystery Dungeon");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Setup il CardLayout e il panel principale
        this.cardLayout = new CardLayout();
        this.mainCardPanel = new JPanel(cardLayout);

        
        // prima card
        this.mainCardPanel.add(this.playGroundView, OVERWORLD_CARD);
        this.frame.setContentPane(this.mainCardPanel);
        this.frame.pack();
        this.frame.setVisible(true);

        // Mostro l'overworld come default
        this.cardLayout.show(this.mainCardPanel, OVERWORLD_CARD);
    }

    /* 
    public void showInventory(Inventory inventory){
        if (this.invView == null) {  // prima volta
            this.invView = new InventoryView(inventory, this);
            this.mainCardPanel.add(this.invView, INVENTORY_CARD);

        } else { // aggiorna la view esistente
            this.invView.updateInventoryModel(inventory); 
            this.invView.refreshView(); 
        }
        this.cardLayout.show(this.mainCardPanel, INVENTORY_CARD);
        
    }*/

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
    }

    public void showCombat(CombatController combatController) {
        JFrame frame = new JFrame("Combattimento");
        if (this.combatView == null) {
            this.combatController = combatController;
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 400);
            frame.add(this.combatController.getView());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        } else {
            this.combatController.redrawView();
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(600, 400);
            frame.add(this.combatController.getView());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

    public void showOverworld() {
        this.cardLayout.show(this.mainCardPanel, OVERWORLD_CARD);
    }
}