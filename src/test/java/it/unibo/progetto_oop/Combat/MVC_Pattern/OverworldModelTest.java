package it.unibo.progetto_oop.Combat.MVC_Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.Combat.Inventory.Inventory;
import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Combat.Position.Position;
import it.unibo.progetto_oop.Combat.PotionFactory.ItemFactory;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern.EnemyFactory;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern.EnemyFactoryImpl;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Overworld.MVC.*;

public class OverworldModelTest {
    private static final int MAX_HP = 100; // Example max HP, can be adjusted
    private static final Position START_PLAYER_POS = new Position(5, 5); // Starting position of the player
    private static final int ENEMY_HP = 100; // Example enemy max HP
    private static final int ENEMY_POWER = 20; // Example enemy power

    private OverworldModel model;
    private Inventory inventory;


    @BeforeEach
    void setUp(){
        // The player
        Player player = new Player(MAX_HP, inventory); // Example max HP
        player.setPosition(START_PLAYER_POS);
       
        // Items on the map
        List<Item> items = new ArrayList<>(); 
        
        ItemFactory itemFactory = new ItemFactory();

        Item firstItem = itemFactory.createItem("Health Potion", new Position(4, 4));
        
        Item secondItem = itemFactory.createItem("Antidote", new Position(8, 5));
        
        Item thirdItem = itemFactory.createItem("Attack Buff", new Position(7, 4));
        
        items.add(firstItem);
        items.add(secondItem);
        items.add(thirdItem);
        
        // Walls on the map
        Set<Position> walls = Set.of(
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
        List<Position> enemies = new ArrayList<>(); 
        enemies.add(START_PLAYER_POS);
        enemies.add(new Position(2, 8));

        // Create enemies using the EnemyFactory
        List<Enemy> enemyList = new ArrayList<>();
        EnemyFactory factory = new EnemyFactoryImpl();

        // create model
        this.model = new OverworldModel(player, enemyList, items, walls, inventory);

        Enemy hider = factory.createFollowerEnemy(ENEMY_HP, ENEMY_POWER, enemies.get(0), true, this.model);
        Enemy patroller = factory.createPatrollerEnemy(ENEMY_HP,ENEMY_POWER, enemies.get(0), true, this.model); 
        enemyList.add(hider);
        enemyList.add(patroller);
        
        this.inventory = new Inventory();

    }

    @Test
    void movePlayerTest(){
        final Position newPos = new Position(5 + START_PLAYER_POS.x(), 6 + START_PLAYER_POS.y());
        this.model.movePlayer(5, 6);
        assertEquals(newPos, this.model.getPlayer().getPosition());
    }
    
}
