package it.unibo.progetto_oop.overworld.mvc;

import java.util.List;
import java.util.Objects;

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
import it.unibo.progetto_oop.overworld.mvc.generation_entities.OverworldEntitiesGenerator;
import it.unibo.progetto_oop.overworld.mvc.model_system.EnemySystem;
import it.unibo.progetto_oop.overworld.mvc.model_system.MovementSystem;
import it.unibo.progetto_oop.overworld.mvc.model_system.PickupSystem;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.data.listner.ChangeFloorListener;
import it.unibo.progetto_oop.overworld.playground.data.listner.grid_updater.EntityGridUpdater;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ImplArrayListStructureData;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ReadOnlyGrid;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.ReadOnlyGridAdapter;
import it.unibo.progetto_oop.overworld.playground.data.structuredata_strategy.StructureData;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Dungeon;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Floor;
import it.unibo.progetto_oop.overworld.view_manager_observer.ViewManagerObserver;

/**
 * OverworldModel: orchestrator of the game world.
 * - Owns the Player and the model systems (movement / pickup / enemy)
 * - Forwards updates to the Floor through GridNotifier
 * - Holds the Dungeon and manages floor changes (binding the current Floor)
 */
public final class OverworldModel implements OverworldModelApi {

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

    /** Current floor's base grid (structure). */
    private StructureData baseGrid;   // playground: WALL / ROOM / TUNNEL / STAIRS

    /** Current floor's entity grid. */
    private StructureData entityGrid; // entities: PLAYER / ENEMY / ITEM / NONE

    /** Grid notifier used to propagate grid updates (encapsulates GridUpdater). */
    private GridNotifier gridNotifier;

    /** Listener for floor changes. */
    private ChangeFloorListener changeFloorListener;

    /** Wall-collision service. */
    private WallCollision wallCollision;

    /** Combat-collision service. */
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
     * Set the active floor.
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
            // immutable views for UI
            final ReadOnlyGrid baseView   = floor.grid();
            final ReadOnlyGrid entityView = floor.entityGrid();
            // mutable copies for model services (not for UI)
            this.baseGrid   = toMutable(baseView);
            this.entityGrid = toMutable(entityView);
        
            if (this.gridNotifier == null) {
                this.gridNotifier = new GridNotifier(null);
            }
            this.gridNotifier
                .setGridUpdater(new EntityGridUpdater(this.entityGrid));
            this.gridNotifier
                .setListEnemyUpdater(this.enemySystem::removeEnemyAt);
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

            //initialize
            initFloor(floor);
            
            this.changeFloorListener.onFloorChange(getBaseGridView());
        }
        return changedFloor;
    }
    
    private void initFloor(final Floor floor) {
        this.setSpawnObjects(List.of(), List.of());
            new OverworldEntitiesGenerator(
                    floor,
                    this.player,
                    this,
                    this.gridNotifier);
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
     * Set the floor-change listener.
     *
     * @param l the change-floor listener
     */
    @Override
    public void setChangeFloorListener(final ChangeFloorListener l) {
        this.changeFloorListener = l;
    }

    /**
     * Set the grid notifier.
     *
     * @param newGridNotifier the grid notifier to set
     */
    public void setGridNotifier(final GridNotifier newGridNotifier) {
        this.gridNotifier = newGridNotifier;
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
     * Get the player's position.
     * 
     * @return the player's position
     */
    @Override
    public Position getPlayerPosition() {
        return this.player.getPosition();
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
     * Get the enemy system's current enemies.
     *
     * @return the enemy list
     */
    public List<Enemy> getEnemies() {
        return this.enemySystem.getEnemies();
    }

    /**
     * Get the encountered enemy.
     *
     * @return the encountered enemy
     */
    public Enemy getEncounteredEnemy() {
        return this.enemySystem.getEncounteredEnemy();
    }

    /**
     * Check if a combat transition is pending.
     *
     * @return true if a combat transition is pending, false otherwise
     */
    public boolean isCombatTransitionPending() {
        return this.movementSystem.isCombatTransitionPending();
    }

    /**
     * Get the wall-collision service.
     *
     * @return the wall collision instance
     */
    public WallCollision getWallCollision() {
        return this.wallCollision;
    }

    /**
     * Get the combat-collision service.
     *
     * @return the combat collision instance
     */
    public CombatCollision getCombatCollision() {
        return this.combatCollision;
    }

    /**
     * Get the base grid (structure) view.
     *
     * @return the base grid view
     */
    @Override
    public ReadOnlyGrid getBaseGridView() {
        return ReadOnlyGridAdapter.of(this.baseGrid);
    }

    /**
     * Get the entity grid view.
     *
     * @return the entity grid view
     */
    @Override
    public ReadOnlyGrid getEntityGridView() {
        return ReadOnlyGridAdapter.of(this.entityGrid);
    }

    /**
     * Get the base grid (structure).
     * INTERNAL: mutable base grid for model services (not for UI).
     *
     * @return the base grid
     */
    public StructureData getBaseGrid() {
        return this.baseGrid;
    }

    /**
     * Get the entity grid (structure).
     * INTERNAL: mutable entity grid for model services (not for UI).
     *
     * @return the entity grid
     */
    public StructureData getEntityGrid() {
        return this.entityGrid;
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

    /**
     * Clear the combat transition flag.
     */
    public void clearCombatTransitionFlag() {
        this.movementSystem.clearCombatTransitionFlag();
    }

    /**
     * Set the combat transition flag.
     */
    public void setCombatTransitionFlag() {
        this.movementSystem.setCombatTransitionFlag();
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

    //--------- Movement ---------

    /**
     * Move the player to the right direction.
     *
     * @param direction the direction to move
     */

    public void move(final MoveDirection direction) {
        switch (direction) {
            case UP -> this.movementSystem.move(0, -1, pickupSystem, enemySystem);
            case DOWN -> this.movementSystem.move(0, 1, pickupSystem, enemySystem);
            case LEFT -> this.movementSystem.move(-1, 0, pickupSystem, enemySystem);
            case RIGHT -> this.movementSystem.move(1, 0, pickupSystem, enemySystem);
            case NONE -> this.movementSystem.move(0, 0, pickupSystem, enemySystem);
        }
    }

    private static StructureData toMutable(final ReadOnlyGrid src) {
    final StructureData dst = new ImplArrayListStructureData(src.width(), src.height());
    for (int y = 0; y < src.height(); y++) {
        for (int x = 0; x < src.width(); x++) {
            dst.set(x, y, src.get(x, y));
        }
    }
    return dst;
}
}
