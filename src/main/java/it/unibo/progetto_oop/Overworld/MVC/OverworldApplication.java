package it.unibo.progetto_oop.Overworld.MVC;

import javax.swing.*;
import java.util.*;

import it.unibo.progetto_oop.Combat.Inventory.*;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Combat.Position.Position;
import it.unibo.progetto_oop.Combat.PotionFactory.ItemFactory;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern.*;

public class OverworldApplication {
    // MVC components
    private OverworldModel model;
    private OverworldView view;
    private OverworldController controller;

    private Inventory inventory;

    // costants
    private static final int MAX_HP = 100; // Example max HP, can be adjusted
    private static final Position START_PLAYER_POS = new Position(5, 5); // Starting position of the player
    private static final int ENEMY_HP = 100;
    private static final int ENEMY_POWER = 20;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OverworldApplication gameApp = new OverworldApplication();
            gameApp.start();
        });
    }

    private void start() {
        // The player
        Player player = new Player(MAX_HP); // Example max HP
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

        // Create enemies using the EnemyFactory
        List<Enemy> enemyList = new ArrayList<>();
        EnemyFactory factory = new EnemyFactoryImpl();

        Enemy hider = factory.createFollowerEnemy(ENEMY_HP, ENEMY_POWER, enemies.get(0), true, this.model);
        Enemy patroller = factory.createPatrollerEnemy(ENEMY_HP,ENEMY_POWER, enemies.get(0), true, this.model); 
        enemyList.add(hider);
        enemyList.add(patroller);

        this.inventory = new Inventory(); // TODO

        // create model
        this.model = new OverworldModel(player, enemyList, items, walls, inventory);
        
        
        // create view
        // this.view = new OverworldView(); // TODO

        // create controller
        //this.controller = new OverworldController(model, view); // TODO
    }

    public OverworldModel getOverworldModel(){
        return this.model;
    }

    public OverworldView getView(){
        return this.view;
    }
}
