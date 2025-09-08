package it.unibo.progetto_oop.overworld.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.potion_factory.ItemFactory;
import it.unibo.progetto_oop.overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.overworld.PlayGround.Data.TileType;
import it.unibo.progetto_oop.overworld.PlayGround.DungeonLogic.Floor;
import it.unibo.progetto_oop.overworld.PlayGround.PlacementStrategy.ImplRandomPlacement;
import it.unibo.progetto_oop.overworld.PlayGround.PlacementStrategy.RandomPlacementStrategy;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_pattern.EnemyFactory;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_pattern.EnemyFactoryImpl;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.player.Player;

public class OverworldEntitiesGenerator {

    private final List<Item> itemList = new ArrayList<>();
    private final List<Enemy> enemyList = new ArrayList<>();
    private final ItemFactory itemFactory = new ItemFactory();
    private final Random rand = new Random();

    private static final int ENEMY_HP = 100;
    private static final int ENEMY_POWER = 20;
    private static final int MIN_DIST_FROM_PLAYER = 2;

    public OverworldEntitiesGenerator(final Floor currentFloor,
            final Player player,
            final OverworldModel model,
            final GridNotifier gridNotifier) {

        final StructureData base = model.getBaseGridView();
        final StructureData entity = model.getEntityGridView();
        final RandomPlacementStrategy placer = new ImplRandomPlacement();

        // PLAYER
        final Position playerPos = placer.placePlayer(base, entity, rand);
        if (playerPos == null) {
            throw new IllegalStateException("Impossibile piazzare il PLAYER: nessuna cella valida.");
        }
        player.setPosition(playerPos);

        // 2) ENEMY
        placer.placeObject(base, entity, TileType.ENEMY, model.getCurrentFloor().rooms().size(),
                rand, playerPos, MIN_DIST_FROM_PLAYER);

        // 3) ITEM
        placer.placeObject(base, entity, TileType.ITEM, model.getCurrentFloor().rooms().size(),
                rand, null, MIN_DIST_FROM_PLAYER);

        // entities generation
        generateEnemiesFromEntityGrid(model, gridNotifier);
        generateItemsFromEntityGrid(model);
        model.setSpawnObjects(enemyList, itemList);
    }

    private void generateItemsFromEntityGrid(final OverworldModel model) {
        final List<String> types = List.of("Health Potion", "Antidote", "Attack Buff");
        for (Position pos : getPositionsOfType(TileType.ITEM, model.getEntityGridView())) {
            String type = types.get(rand.nextInt(types.size()));
            itemList.add(itemFactory.createItem(type, pos));
        }
    }

    private void generateEnemiesFromEntityGrid(final OverworldModel model, final GridNotifier gridNotifier) {
        final EnemyFactory factory = new EnemyFactoryImpl(model.getWallCollision(), model.getCombatCollision());
        for (Position pos : getPositionsOfType(
                TileType.ENEMY, model.getEntityGridView())) {
            int roll = rand.nextInt(3);
            Enemy enemy = switch (roll) {
                case 0 -> factory.createFollowerEnemy(ENEMY_HP, ENEMY_POWER, pos, true, gridNotifier);
                case 1 -> factory.createSleeperEnemy(ENEMY_HP, ENEMY_POWER, pos, true, gridNotifier);
                default -> factory.createPatrollerEnemy(ENEMY_HP, ENEMY_POWER, pos, false, gridNotifier);
            };
            enemyList.add(enemy);
        }
    }

    private List<Position> getPositionsOfType(final TileType type, final StructureData entity) {
        final List<Position> out = new ArrayList<>();
        for (int y = 0; y < entity.height(); y++) {
            for (int x = 0; x < entity.width(); x++) {
                if (entity.get(x, y) == type) {
                    out.add(new Position(x, y));
                }
            }
        }
        return out;
    }
}
