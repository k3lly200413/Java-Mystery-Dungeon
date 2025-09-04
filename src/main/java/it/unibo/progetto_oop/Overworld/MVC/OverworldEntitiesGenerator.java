package it.unibo.progetto_oop.Overworld.MVC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.potion_factory.ItemFactory;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern.EnemyFactory;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern.EnemyFactoryImpl;
import it.unibo.progetto_oop.Overworld.GridNotifier.GridNotifier;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.Floor;
import it.unibo.progetto_oop.Overworld.Player.Player;

//in questa classe vengono generati e inizializzati gli elementi in base alla posizione della grid
//NON deve essere aggiornata la view
public class OverworldEntitiesGenerator {
    List<Item> itemList = new ArrayList<>();
    ItemFactory itemFactory = new ItemFactory();

    List<Enemy> enemyList = new ArrayList<>();
    private static final int ENEMY_HP = 100;
    private static final int ENEMY_POWER = 20;

    public OverworldEntitiesGenerator(Floor curreFloor, Player player, OverworldModel overworldModel, GridNotifier gridNotifier) {
        generateItems(curreFloor);
        generateEnemies(curreFloor, gridNotifier);
        spawnPlayer(curreFloor, player);
        overworldModel.setSpawnObjects(enemyList, itemList, new HashSet<>());  // l'ho messo per testare, poi vedi tu come vuoi implementare questa cosa
    }

    private void generateItems(Floor currentFloor) {
        List<String> types = List.of("Health Potion", "Antidote", "Attack Buff");
        var rand = ThreadLocalRandom.current();
        for (Position pos : currentFloor.getObjectsPositions(TileType.ITEM)) {
            String type = types.get(rand.nextInt(types.size()));
            itemList.add(itemFactory.createItem(type, pos));
        }
    }

    private void generateEnemies(Floor currentFloor, GridNotifier gridNotifier) {
        EnemyFactory factory = new EnemyFactoryImpl();
        for (Position pos : currentFloor.getObjectsPositions(TileType.ENEMY)) {
            int roll = ThreadLocalRandom.current().nextInt(3);
            Enemy enemy;
            switch (roll) {
                case 0 -> enemy = factory.createFollowerEnemy(ENEMY_HP, ENEMY_POWER, pos, true, Collections.emptySet(), gridNotifier);
                case 1 -> enemy = factory.createSleeperEnemy(ENEMY_HP, ENEMY_POWER, pos, true, Collections.emptySet(), gridNotifier);
                default -> enemy = factory.createPatrollerEnemy(ENEMY_HP, ENEMY_POWER, pos, false, Collections.emptySet(), gridNotifier);
            }
            enemyList.add(enemy);
        }
    }

    private void spawnPlayer(Floor currentFloor, Player player) {
        player.setPosition(currentFloor.getObjectsPositions(TileType.PLAYER).get(0));
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public List<Enemy> getEnemyList() {
        return enemyList;
    }
}
