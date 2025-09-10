package it.unibo.progetto_oop.overworld.mvc.generation_entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.potion_factory.ItemFactory;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_pattern.EnemyFactory;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_pattern.EnemyFactoryImpl;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.mvc.OverworldModel;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.data.StructureData_strategy.StructureData;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Floor;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.ImplRandomPlacement;
import it.unibo.progetto_oop.overworld.playground.placement_strategy.RandomPlacementStrategy;

public class OverworldEntitiesGenerator {

    /**
     * List to store the items generated in the overworld.
     */
    private final List<Item> itemList = new ArrayList<>();

    /**
     * List to store the enemies generated in the overworld.
     */
    private final List<Enemy> enemyList = new ArrayList<>();
    /**
     * Factory for creating items in the overworld.
     */
    private final ItemFactory itemFactory = new ItemFactory();

    /**
     * Random instance for generating random values.
     */
    private final Random rand = new Random();

    /**
     * Indicates whether the current floor contains a boss.
     */
    private boolean isBoss = false;

    /**
     * Minimum distance from the player for placing objects.
     */
    private static final int MIN_DIST_FROM_PLAYER = 2;

    /**
     * Number of enemy types available for random selection.
     */
    private static final int ENEMY_TYPE_COUNT = 3;

    /**
     * Constructor for OverworldEntitiesGenerator.
     * Initializes the overworld entities.
     *
     * @param currentFloor the current floor of the dungeon
     * @param player the player object
     * @param model the overworld model containing grid data
     * @param gridNotifier the notifier for grid updates
     */
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
            throw new IllegalStateException("No valid position for player");
        }
        player.setPosition(playerPos);

        if (model.getCurrentFloor().rooms().size() == 1) {
            //boss
            this.isBoss = true;
            placer.placeObject(
                base,
                entity,
                TileType.BOSS,
                1,
                rand,
                playerPos,
                MIN_DIST_FROM_PLAYER
            );
        } else {
            // 2) ENEMY
            placer.placeObject(
                base,
                entity,
                TileType.ENEMY,
                model.getCurrentFloor().rooms().size(),
                rand,
                playerPos,
                MIN_DIST_FROM_PLAYER
            );
            // 3) ITEM
            placer.placeObject(
                base,
                entity,
                TileType.ITEM,
                model.getCurrentFloor().rooms().size(),
                rand,
                null,
                MIN_DIST_FROM_PLAYER
            );
        }

        // entities generation
        generateEnemiesFromEntityGrid(model, gridNotifier);
        generateItemsFromEntityGrid(model);
        model.setSpawnObjects(enemyList, itemList);
    }

    private void generateItemsFromEntityGrid(final OverworldModel model) {
        final List<String> types = List.of(
            "Health Potion",
            "Antidote",
            "Attack Buff"
        );
        for (Position pos : getPositionsOfType(
                TileType.ITEM, model.getEntityGridView())) {
            String type = types.get(rand.nextInt(types.size()));
            itemList.add(itemFactory.createItem(type, pos));
        }
    }

    private void generateEnemiesFromEntityGrid(
            final OverworldModel model,
            final GridNotifier gridNotifier) {
        final EnemyFactory factory = new EnemyFactoryImpl(
            model.getWallCollision(),
            model.getCombatCollision()
        );
        if (this.isBoss) {
            Position pos = getPositionsOfType(
                TileType.BOSS, model.getEntityGridView()).get(0);
            Enemy enemy = factory.createBossEnemy(
                model.getEntityStatsConfig().enemyHp()
                    * model.getEntityStatsConfig().bossHpMultiplier(),
                model.getEntityStatsConfig().enemyPower()
                    * model.getEntityStatsConfig().bossPowerMultiplier(),
                pos,
                true,
                gridNotifier
            );
            enemyList.add(enemy);
        } else {
            for (Position pos : getPositionsOfType(
                    TileType.ENEMY, model.getEntityGridView())) {
                int roll = rand.nextInt(ENEMY_TYPE_COUNT);
                Enemy enemy = switch (roll) {
                    case 0 -> factory.createFollowerEnemy(
                        model.getEntityStatsConfig().enemyHp(),
                        model.getEntityStatsConfig().enemyPower(),
                        pos,
                        true,
                        gridNotifier);
                    case 1 -> factory.createSleeperEnemy(
                        model.getEntityStatsConfig().enemyHp(),
                        model.getEntityStatsConfig().enemyPower(),
                        pos,
                        true,
                        gridNotifier);
                    default -> factory.createPatrollerEnemy(
                        model.getEntityStatsConfig().enemyHp(),
                        model.getEntityStatsConfig().enemyPower(),
                        pos,
                        false,
                        gridNotifier
                    );
                };
                enemyList.add(enemy);
            }
        }
    }

    private List<Position> getPositionsOfType(
            final TileType type,
            final StructureData entity) {
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
