package it.unibo.progetto_oop.Overworld.MVC;

import javax.swing.*;

import java.awt.CardLayout;
import java.util.*;

import it.unibo.progetto_oop.combat.Inventory.*;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.combat.PotionFactory.ItemFactory;
import it.unibo.progetto_oop.combat.position.Position;
import it.unibo.progetto_oop.combat.potion_strategy.Potion;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern.*;

public class OverworldApplication {
    // MVC components
    private OverworldModel model;
    private OverworldView view;
    private OverworldController controller;

    private CardLayout cardLayout; 
    private JPanel mainCardPanel; 

    private Inventory inventory;
    private InventoryView invView;

    // costants
    private static final int MAX_HP = 100; // Example max HP, can be adjusted
    private static final Position START_PLAYER_POS = new Position(5, 5); // Starting position of the player
    private static final int ENEMY_HP = 100;
    private static final int ENEMY_POWER = 20;
    public static final String INVENTORY_CARD = "INVENTORY";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OverworldApplication gameApp = new OverworldApplication();
            gameApp.start();
        });
    }

    private void start() {
        // The player
        Player player = new Player(MAX_HP, inventory); // Example max HP
        player.setPosition(START_PLAYER_POS);
       
        // Items on the map
        List<Item> items = new ArrayList<>(); // TODO: Initialize with actual items
        
        ItemFactory itemFactory = new ItemFactory();

        Item firstItem = itemFactory.createItem("Health Potion", new Position(4, 4));
        
        Item secondItem = itemFactory.createItem("Antidote", new Position(8, 5));
        
        Item thirdItem = itemFactory.createItem("Attack Buff", new Position(7, 4));
        
        items.add(firstItem);
        items.add(secondItem);
        items.add(thirdItem);
        
        // Walls on the map
        Set<Position> walls = Set.of(// TODO: Initialize with actual wall positions
                new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(3, 0), new Position(4, 0),
                new Position(5, 0), new Position(6, 0), new Position(7, 0), new Position(8, 0), new Position(9, 0),
                new Position(0, 9), new Position(1, 9), new Position(2, 9), new Position(3, 9), new Position(4, 9),
                new Position(5, 9), new Position(6, 9), new Position(7, 9), new Position(8, 9), new Position(9, 9),
                new Position(0, 1), new Position(0, 2), new Position(0, 3), new Position(0, 4), new Position(0, 5),
                new Position(0, 6), new Position(0, 7), new Position(0, 8),
                new Position(9, 1), new Position(9, 2), new Position(9, 3), new Position(9, 4), new Position(9, 5),
                new Position(9, 6), new Position(9, 7), new Position(9, 8),
                // Some internal walls
                new Position(3, 3), new Position(3, 4), new Position(3, 5),
                new Position(6, 6), new Position(7, 6)
        ); 

        // Enemies on the map
        List<Position> enemies = new ArrayList<>(); // TODO: Initialize with actual enemies
        enemies.add(new Position(2, 7));
        enemies.add(new Position(2, 8));

        
        List<Enemy> enemyList = new ArrayList<>();

        // create inventory
        this.inventory = new Inventory();
        Potion healthPotion = (Potion) itemFactory.createItem("Health Potion", null);
        Potion antidote = (Potion) itemFactory.createItem("Antidote", null);
        Potion attackBuff = (Potion) itemFactory.createItem("Attack Buff", null);
        this.inventory.addItem(healthPotion);
        this.inventory.addItem(antidote);
        this.inventory.addItem(attackBuff);
        
        // create model
        this.model = new OverworldModel(player, enemyList, items, walls);

        // Create enemies using the EnemyFactory
        EnemyFactory factory = new EnemyFactoryImpl();

        Enemy hider = factory.createFollowerEnemy(ENEMY_HP, ENEMY_POWER, enemies.get(0), true, this.model, this);
        Enemy patroller = factory.createPatrollerEnemy(ENEMY_HP,ENEMY_POWER, enemies.get(0), true, this.model, this); 
        enemyList.add(hider);
        enemyList.add(patroller);
        
        // create view
        // this.view = new OverworldView(); // TODO

        // create controller
        //this.controller = new OverworldController(model, view); // TODO

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
        this.showInventory(inventory);
    }

    public OverworldModel getOverworldModel(){
        return this.model;
    }

    public OverworldView getView(){
        return this.view;
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
        this.model.clearInCombatFlag(); // ensure we are not in combat mode while in inventory
    }
}
