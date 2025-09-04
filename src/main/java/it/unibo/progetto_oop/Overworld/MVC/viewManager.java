package it.unibo.progetto_oop.Overworld.MVC;

import javax.swing.*;

import java.awt.CardLayout;

import it.unibo.progetto_oop.combat.inventory.*;
import it.unibo.progetto_oop.Overworld.PlayGround.view.SwingMapView;

public class ViewManager {

    private CardLayout cardLayout;
    private JPanel mainCardPanel;
    private JPanel overworldView;

   // private Inventory inventory;
    private InventoryView invView;
    public static final String INVENTORY_CARD = "INVENTORY";
    public static final String OVERWORLD_CARD = "OVERWORLD";

    public void start(SwingMapView overworldView) {
        // Setup the CardLayout and main panel
        this.mainCardPanel = new JPanel();
        this.cardLayout = new CardLayout();
        this.mainCardPanel.setLayout(this.cardLayout);

        // TODO: add overworld and combat views to the card panel

        // Setup the Main Window (JFrame)
        JFrame frame = new JFrame("Overworld Adventure"); // window title
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 

        frame.add(this.mainCardPanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);      

        this.mainCardPanel = new JPanel();
        this.cardLayout = new CardLayout();
        this.mainCardPanel.setLayout(this.cardLayout);

        /* 
        
        // Creo la view dell'overworld
        this.overworldView = overworldView;
        this.mainCardPanel.add(this.overworldView, OVERWORLD_CARD);

        // Mostro l'overworld come default
        this.cardLayout.show(this.mainCardPanel, OVERWORLD_CARD); */
    }

    public void showInventory(Inventory inventory) {
        if (this.invView == null) { // prima volta
            this.invView = new InventoryView(inventory, this);
            this.mainCardPanel.add(this.invView, INVENTORY_CARD);
        } else { // aggiorna la view esistente
            this.invView.updateInventoryModel(inventory);
            this.invView.refreshView();
        }
    
        this.cardLayout.show(this.mainCardPanel, INVENTORY_CARD);
    }
}
