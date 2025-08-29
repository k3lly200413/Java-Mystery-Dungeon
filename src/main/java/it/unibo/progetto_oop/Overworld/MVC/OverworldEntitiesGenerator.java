package it.unibo.progetto_oop.Overworld.MVC;

import java.util.ArrayList;
import java.util.List;

import it.unibo.progetto_oop.Combat.Inventory.Inventory;
import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Combat.PotionFactory.ItemFactory;
import it.unibo.progetto_oop.Combat.PotionStrategy.Potion;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern.EnemyFactory;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern.EnemyFactoryImpl;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Pair;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.Floor;

public class OverworldEntitiesGenerator {
    private static final int MAX_HP = 100; // Example max HP, can be adjusted

    List<Item> itemList = new ArrayList<>();
    ItemFactory itemFactory = new ItemFactory();

    List<Item> enemyList = new ArrayList<>();
    private static final int ENEMY_HP = 100;
    private static final int ENEMY_POWER = 20;

    public OverworldEntitiesGenerator(Floor curreFloor) {
        generateItems(curreFloor);
        generateEnemies(curreFloor);
        spawnPlayer(curreFloor);
    }
    
    private void generateItems(Floor currentFloor) {
        List<Position> posItems = new ArrayList<>();
        posItems.addAll(currentFloor.getObjectsPositions(TileType.ITEM));
        posItems.stream().forEach(pos -> itemFactory.createItem("null", pos));
        

        itemList.add(firstItem);
        itemList.add(secondItem);
        itemList.add(thirdItem);

    }

    private void generateEnemies(Floor currentFloor) {
        EnemyFactory factory = new EnemyFactoryImpl();

        Enemy hider = factory.createFollowerEnemy(ENEMY_HP, ENEMY_POWER, enemies.get(0), true, this.model, this);
        Enemy patroller = factory.createPatrollerEnemy(ENEMY_HP, ENEMY_POWER, enemies.get(0), true, this.model, this);
        enemyList.add(hider);
        enemyList.add(patroller);
    }

    private void spawnPlayer(Floor currentFloor) {
        player.setPosition(START_PLAYER_POS);
    }
}
