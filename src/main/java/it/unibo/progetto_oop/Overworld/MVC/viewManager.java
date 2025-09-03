package it.unibo.progetto_oop.Overworld.MVC;

import javax.swing.*;

import java.awt.CardLayout;

import it.unibo.progetto_oop.Combat.Inventory.*;
import it.unibo.progetto_oop.Overworld.PlayGround.view.SwingMapView;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatView;
import it.unibo.progetto_oop.Combat.CombatLauncher;

public class ViewManager {

    private CardLayout cardLayout; 
    private JPanel mainCardPanel; 
    private JPanel overworldView;
    private InventoryView invView;
    private CombatView combatView;
    private CombatController combatController;

    public static final String INVENTORY_CARD = "INVENTORY";
    public static final String OVERWORLD_CARD = "OVERWORLD";

    public void start(SwingMapView overworldView) {
        //this.overworldView = overworldView.getPanel();

        // Setup il CardLayout e il panel principale (solo UNA volta)
        this.cardLayout = new CardLayout();
        this.mainCardPanel = new JPanel(cardLayout);

        /* 
        // Aggiungo l'overworld
        this.mainCardPanel.add(this.overworldView, OVERWORLD_CARD);

        // Mostro l'overworld come default
        this.cardLayout.show(this.mainCardPanel, OVERWORLD_CARD);

        // Setup del JFrame
        JFrame frame = new JFrame("Overworld Adventure");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this.mainCardPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);*/
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

    public void showCombat() {
        JFrame frame = new JFrame("Combattimento");
        if (this.combatView == null) {
            this.combatController = CombatLauncher.buildCombat();
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