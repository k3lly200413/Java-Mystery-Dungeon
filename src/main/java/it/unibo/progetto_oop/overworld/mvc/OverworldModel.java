package it.unibo.progetto_oop.overworld.mvc;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.overworld.combat_collision.CombatCollision;
import it.unibo.progetto_oop.overworld.combat_collision.CombatCollisionImpl;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.MovementUtil.MoveDirection;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision.WallCollision;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision.WallCollisionImpl;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.mvc.generation_entities.EntityStatsConfig;
import it.unibo.progetto_oop.overworld.mvc.model_system.EnemySystem;
import it.unibo.progetto_oop.overworld.mvc.model_system.MovementSystem;
import it.unibo.progetto_oop.overworld.mvc.model_system.PickupSystem;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.StructureData_strategy.ImplArrayListStructureData;
import it.unibo.progetto_oop.overworld.playground.data.StructureData_strategy.StructureData;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.data.listner.ChangeFloorListener;
import it.unibo.progetto_oop.overworld.playground.data.listner.grid_updater.EntityGridUpdater;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Dungeon;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Floor;
import it.unibo.progetto_oop.overworld.view_manager_observer.ViewManagerObserver;

/**
 * OverworldModel: orchestratore del mondo di gioco.
 * - possiede Player e i systems (movement/pickup/enemy)
 * - inoltra gli aggiornamenti al Floor tramite GridNotifier
 * - contiene il Dungeon e gestisce il cambio piano (binding del Floor corrente)
 */
public final class OverworldModel {

    /**
     * Configuration for entity statistics.
     */
    private final EntityStatsConfig esConfig;

    /** The dungeon instance. */
    private Dungeon dungeon;

    /** The player instance. */
    private final Player player;

    /** PickupSystem instance. */
    private final PickupSystem pickupSystem;

    /** EnemySystem instance. */
    private final EnemySystem enemySystem;

    /** MovementSystem instance. */
    private final MovementSystem movementSystem;

    /** Current floor's grid (read-only). */
    private StructureData baseGrid;   // playground: WALL/ROOM/TUNNEL/STAIRS

    /** Current floor's entity grid. */
    private StructureData entityGrid; // entities: PLAYER/ENEMY/ITEM/NONE

    /** The grid notifier. To update the grid. Incapsulates GridUpdater. */
    private GridNotifier gridNotifier;

    /** The change floor listener. */
    private ChangeFloorListener changeFloorListener;

    /** The wall collision instance. */
    private WallCollision wallCollision;

    /** The floor initializer function. */
    private Consumer<Floor> floorInitializer = f -> { };

    /** The combat collision instance. */
    private final CombatCollision combatCollision;

    /**
     * Constructor of the OverworldModel class.
     *
     * @param enemies the enemies on the current floor
     * @param items the items on the current floor
     * @param config the entity stats configuration
     */
    public OverworldModel(final List<Enemy> enemies, final List<Item> items,
                        final EntityStatsConfig config) {
        this.esConfig = Objects.requireNonNull(config);
        this.player = new Player(
            esConfig.playerMaxHp(),
            esConfig.playerStamina(),
            esConfig.playerPower(),
            new Inventory()
        );
        this.gridNotifier = new GridNotifier(null);

        this.pickupSystem = new PickupSystem(items, this.player);
        this.enemySystem = new EnemySystem(enemies, this.player, this);
        this.movementSystem = new MovementSystem(this.player, this);

        this.combatCollision = new CombatCollisionImpl();

        setSpawnObjects(enemies, items);
    }

    /**
     * Bind the dungeon to the model.
     *
     * @param newDungeon the dungeon to bind
     */
    public void bindDungeon(final Dungeon newDungeon) {
        this.dungeon = newDungeon;
    }

    /**
     * Sets the active floor.
     *
     * @param floor current floor
     */
    public void bindCurrentFloor(final Floor floor) {
        if (floor == null) {
            this.baseGrid = null;
            this.entityGrid = null;
            if (this.gridNotifier != null) {
                this.gridNotifier.setGridUpdater(null);
                this.gridNotifier.setListEnemyUpdater(null);
                this.gridNotifier.setListItemUpdater(null);
            }
        } else {
            this.baseGrid = floor.grid(); // il Floor ha solo il terreno
            entityGrid =
                new ImplArrayListStructureData(
                    baseGrid.width(), baseGrid.height());
            entityGrid.fill(TileType.NONE);
            if (this.gridNotifier == null) {
                this.gridNotifier = new GridNotifier(null);
            }
            this.gridNotifier
                .setGridUpdater(new EntityGridUpdater(this.entityGrid));
            this.gridNotifier
                .setListEnemyUpdater(
                        this.enemySystem::removeEnemyAt);
            this.gridNotifier
                .setListItemUpdater(this.pickupSystem::removeItemAt);

        }
        this.wallCollision = new WallCollisionImpl(baseGrid, entityGrid);
    }

    /**
     * Go to the next floor.
     *
     * @return true if the floor changed, false otherwise
     */
    public boolean nextFloor() {
        final boolean changedFloor = this.dungeon.nextFloor();
        if (changedFloor) {
            final Floor floor = this.dungeon.getCurrentFloor();
            bindCurrentFloor(floor);
            floorInitializer.accept(floor);
            this.changeFloorListener.onFloorChange(this.baseGrid);
        }
        return changedFloor;
    }

    // --------- Setters ---------- //

    /**
     * Set the enemies and items on the current floor.
     *
     * @param enemies the enemies on the current floor
     * @param items the items on the current floor
     */
    public void setSpawnObjects(final List<Enemy> enemies,
    final List<Item> items) {
        this.pickupSystem.setItems(items);
        this.enemySystem.setEnemies(enemies);
    }

    /**
     * Set the floor initializer.
     *
     * @param init the floor initializer
     */
    public void setFloorInitializer(final Consumer<Floor> init) {
        this.floorInitializer = Objects.requireNonNull(init);
    }

    /**
     * Set the change floor listener.
     *
     * @param l the change floor listener
     */
    public void setChangeFloorListener(final ChangeFloorListener l) {
        this.changeFloorListener = l;
    }

    /**
     * Set the entity at a given position.
     *
     * @param p the position to set
     * @param t the tile type to set
     */
    public void setEntityAt(final Position p, final TileType t) {
        entityGrid.set(p.x(), p.y(), t);
    }

    /**
     * Set the base grid view.
     *
     * @param base the base grid view
     */
    public void setBaseGridView(final StructureData base) {
        this.baseGrid = base;
    }

    /**
     * Set the entity grid view.
     *
     * @param entity the entity grid view
     */
    public void setEntityGridView(final StructureData entity) {
        this.entityGrid = entity;
    }

    /**
     * Set the grid notifier.
     *
     * @param newGridNotifier the grid notifier to set
     */
    public void setGridNotifier(final GridNotifier newGridNotifier) {
        this.gridNotifier = newGridNotifier;
    }

    /**
     * Set the combat transition listener.
     *
     * @param curranteViewManagerObserver the view manager observer
     */
    public void setCombatTransitionListener(
    final ViewManagerObserver curranteViewManagerObserver) {
        this.combatCollision
            .setViewManagerListener(curranteViewManagerObserver);
    }

    //---------Getters----------

    /**
     * Get the current floor.
     *
     * @return the current floor
     */
    public Floor getCurrentFloor() {
    return this.dungeon.getCurrentFloor();
    }

    /**
     * Get the grid notifier.
     *
     * @return the grid notifier
     */
    public GridNotifier getGridNotifier() {
        return gridNotifier;
    }

    /**
     * Get the player.
     *
     * @return the player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Get the items on the map.
     *
     * @return the list of items in the overworld
     */
    public List<Item> getItem() {
        return this.pickupSystem.getItem();
    }

    /**
     * Get the inventory instance.
     *
     * @return the inventory instance
     */
    public Inventory getInventoryInstance() {
        return this.pickupSystem.getInventoryInstance();
    }


    /**
     * Get the wall collision instance.
     *
     * @return the wall collision instance
     */
    public WallCollision getWallCollision() {
        return this.wallCollision;
    }

    /**
     * Get the combat collision instance.
     *
     * @return the combat collision instance
     */
    public CombatCollision getCombatCollision() {
        return this.combatCollision;
    }

    /**
     * Get the base grid view.
     *
     * @return the base grid view
     */
    public StructureData getBaseGridView() {
        return baseGrid;
    }

    /**
     * Get the entity grid view.
     *
     * @return the entity grid view
     */
    public StructureData getEntityGridView() {
        return entityGrid;
    }

    /**
     * Get the entity at a given position.
     *
     * @param p the position to check
     * @return the entity at the given position
     */
    public TileType getEntityAt(final Position p) {
        return entityGrid.get(p.x(), p.y());
    }

    /**
     * Get the entity stats configuration.
     *
     * @return the entity stats configuration
     */
    public EntityStatsConfig getEntityStatsConfig() {
        return this.esConfig;
    }

    public boolean isCombatTransitionPending() {
        return this.movementSystem.isCombatTransitionPending();
    }
    //---------Movimento---------


    /**
     * Move the player to the right.
     */
    public void move(MoveDirection direction) {
        switch (direction) {
            case UP -> this.movementSystem.move(0, -1, pickupSystem, enemySystem);
            case DOWN -> this.movementSystem.move(0, 1, pickupSystem, enemySystem);
            case LEFT -> this.movementSystem.move(-1, 0, pickupSystem, enemySystem);
            case RIGHT -> this.movementSystem.move(1, 0, pickupSystem, enemySystem);
            case NONE -> this.movementSystem.move(0, 0, pickupSystem, enemySystem);
            default ->
                throw new IllegalStateException(
                    "Unexpected value: " + direction);
        }
    }
}
