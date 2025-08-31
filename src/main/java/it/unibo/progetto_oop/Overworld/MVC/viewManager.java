package it.unibo.progetto_oop.Overworld.MVC;

import javax.swing.*;

import java.awt.CardLayout;

import it.unibo.progetto_oop.Combat.Inventory.*;

public class ViewManager {

    private CardLayout cardLayout; 
    private JPanel mainCardPanel; 

    private Inventory inventory;
    private InventoryView invView;
    public static final String INVENTORY_CARD = "INVENTORY";

    public void start() {
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

        // Show the inventory for testing purposes
        // this.showInventory(inventory);
    }

    public void showInventory(Inventory inventory){
        if (this.invView == null) {  // first time
            this.invView = new InventoryView(inventory, this);
            this.mainCardPanel.add(this.invView, INVENTORY_CARD);
        } else { // update existing view
            this.invView.updateInventoryModel(inventory); 
            this.invView.refreshView(); 
        }
    
        this.cardLayout.show(this.mainCardPanel, INVENTORY_CARD);
    }
}
