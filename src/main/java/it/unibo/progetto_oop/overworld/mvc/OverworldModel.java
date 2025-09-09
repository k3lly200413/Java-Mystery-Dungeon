package it.unibo.progetto_oop.overworld.mvc;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.overworld.ViewManagerObserver;
import it.unibo.progetto_oop.overworld.combat_collision.CombatCollision;
import it.unibo.progetto_oop.overworld.combat_collision.CombatCollisionImpl;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision.WallCollision;
import it.unibo.progetto_oop.overworld.enemy.movement_strategy.wall_collision.WallCollisionImpl;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;
import it.unibo.progetto_oop.overworld.mvc.model_system.EnemySystem;
import it.unibo.progetto_oop.overworld.mvc.model_system.MovementSystem;
import it.unibo.progetto_oop.overworld.mvc.model_system.PickupSystem;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.playground.data.ChangeFloorListener;
import it.unibo.progetto_oop.overworld.playground.data.EntityGridUpdater;
import it.unibo.progetto_oop.overworld.playground.data.ImplArrayListStructureData;
import it.unibo.progetto_oop.overworld.playground.data.Position;
import it.unibo.progetto_oop.overworld.playground.data.StructureData;
import it.unibo.progetto_oop.overworld.playground.data.TileType;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Dungeon;
import it.unibo.progetto_oop.overworld.playground.dungeon_logic.Floor;

/**
 * OverworldModel: orchestratore del mondo di gioco.
 * - possiede Player e i systems (movement/pickup/enemy)
 * - valida i movimenti contro la griglia del Floor corrente (read-only)
 * - inoltra gli aggiornamenti al Floor tramite GridUpdater (notify*)
 * - contiene il Dungeon e gestisce il cambio piano (binding del Floor corrente)
 */
public final class OverworldModel {

    /**
     * The dungeon instance.
     */
    private Dungeon dungeon;

    /**
     * The player instance.
     */
    private final Player player;

    /**
     * true if the player is in combat.
     */
    private boolean inCombat;

    /**
     * pickupSystem instance.
     */
    private final PickupSystem pickupSystem;

    /**
     * enemySystem instance.
     */
    private final EnemySystem enemySystem;

    /**
     * movementSystem instance.
     */
    private final MovementSystem movementSystem;

    /**
     * current floor's grid (read-only).
     */
    private StructureData baseGrid;   // playground: WALL/ROOM/TUNNEL/STAIRS

    /**
     * current floor's entity grid.
     */
    private StructureData entityGrid; // entities: PLAYER/ENEMY/ITEM/NONE

    /**
     * the grid notifier.
     * to update the grid
     * incapsulates GridUpdater
     */
    private GridNotifier gridNotifier;

    /**
     * the change floor listener.
     */
    private ChangeFloorListener changeFloorListener;

    /**
     * the wall collision instance.
     */
    private WallCollision wallCollision;

    /**
     * the floor initializer function.
     */
    private Consumer<Floor> floorInitializer = f -> { };

    /**
     * the combat collision instance.
     */
    private final CombatCollision combatCollision;

    /**
     * player max hp.
     */
    private static final int PLAYER_MAX_HP = 100;

    /**
     * player stamina.
    */
    private static final int PLAYER_STAMINA = 100;

    /**
     * the player power.
     */
    private static final int PLAYER_POWER = 99;

    /**
     * Constructor of the OverworldModel class.
     * @param enemies the enemies on the current floor
     * @param items the items on the current floor
     */
    public OverworldModel(final List<Enemy> enemies, final List<Item> items) {
        this.player = new Player(PLAYER_MAX_HP, PLAYER_STAMINA,
            PLAYER_POWER, new Inventory());
        this.inCombat = false;
        this.gridNotifier = new GridNotifier(null);

        this.pickupSystem = new PickupSystem(items, this.player);
        this.enemySystem  = new EnemySystem(enemies, this.player, this);
        this.movementSystem = new MovementSystem(this.player, this);

        this.combatCollision = new CombatCollisionImpl();

        setSpawnObjects(enemies, items);
    }

    // Collega il dungeon e seleziona il primo Floor
    /**
     * Bind the dungeon to the model.
     * @param newDungeon the dungeon to bind
     */
    public void bindDungeon(final Dungeon newDungeon) {
        this.dungeon = newDungeon;
    }

    // Imposta quale floor è quello attivo
    /**
     * Sets the active floor.
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
     * @param init the floor initializer
     */
    public void setFloorInitializer(final Consumer<Floor> init) {
        this.floorInitializer = Objects.requireNonNull(init);
    }

    /**
     * Set the change floor listener.
     * @param l the change floor listener
     */
    public void setChangeFloorListener(final ChangeFloorListener l) {
        this.changeFloorListener = l;
    }

    /**
     * Set the entity at a given position.
     * @param p the position to set
     * @param t the tile type to set
     */
    public void setEntityAt(final Position p, final TileType t) {
        entityGrid.set(p.x(), p.y(), t);
    }

    /**
     * Set the base grid view.
     * @param base the base grid view
     */
    public void setBaseGridView(final StructureData base) {
        this.baseGrid = base;
    }

    /**
     * Set the entity grid view.
     * @param entity the entity grid view
     */
    public void setEntityGridView(final StructureData entity) {
        this.entityGrid = entity;
    }

    /**
     * Set the grid notifier.
     * @param newGridNotifier
     */
    public void setGridNotifier(final GridNotifier newGridNotifier) {
        this.gridNotifier = newGridNotifier;
    }

    //---------Getters----------

    /**
     * Get the current floor.
     * @return the current floor
     */
    public Floor getCurrentFloor() {
    return this.dungeon.getCurrentFloor();
    }

    /**
     * Get the grid notifier.
     * @return the grid notifier
     */
    public GridNotifier getGridNotifier() {
        return gridNotifier;
    }

    /**
     * Get the player.
     * @return the player
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * get the items on the map.
     * @return the list of items in the overworld
     */
    public List<Item> getItem() {
        return this.pickupSystem.getItem();
    }

    /**
     * Get the inventory instance.
     * @return the inventory instance
     */
    public Inventory getInventoryInstance() {
        return this.pickupSystem.getInventoryInstance();
    }

    /**
     * Get the enemy system.
     * @return the enemy system
     */
    public List<Enemy> getEnemies() {
        return this.enemySystem.getEnemies();
    }

    /**
     * Get the encountered enemy.
     * @return the encountered enemy
     */
    public Enemy getEncounteredEnemy() {
        return this.enemySystem.getEncounteredEnemy();
    }

    /**
     * Check if the player is in combat.
     * @return true if the player is in combat, false otherwise
     */
    public boolean isInCombat() {
        return this.inCombat;
    }

    /**
     * Check if a combat transition is pending.
     * @return true if a combat transition is pending, false otherwise
     */
    public boolean isCombatTransitionPending() {
        return this.movementSystem.isCombatTransitionPending();
    }

    /**
     * Get the wall collision instance.
     * @return the wall collision instance
     */
    public WallCollision getWallCollision() {
        return this.wallCollision;
    }

    /**
     * Get the combat collision instance.
     * @return the combat collision instance
     */
    public CombatCollision getCombatCollision() {
        return this.combatCollision;
    }

    /**
     * Get the base grid view.
     * @return the base grid view
     */
    public StructureData getBaseGridView() {
        return baseGrid;
    }

    /**
     * Get the entity grid view.
     * @return the entity grid view
     */
    public StructureData getEntityGridView() {
        return entityGrid;
    }

    /**
     * Get the entity at a given position.
     * @param p the position to check
     * @return the entity at the given position
     */
    public TileType getEntityAt(final Position p) {
        return entityGrid.get(p.x(), p.y());
    }

    // ---- Combat flags ---- //

    /**
     * Set the encountered enemy.
     * @param e the encountered enemy
     */
    public void setEncounteredEnemy(final Enemy e) {
        this.enemySystem.setEncounteredEnemy(e);
    }

    /**
     * Clear the inCombat flag.
     */
    public void clearInCombatFlag() {
        this.inCombat = false;
    }

    /**
     * Set the inCombat flag.
     */
    public void setInCombatFlag() {
        this.inCombat = true;
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
     * @param curranteViewManagerObserver the view manager observer
     */
    public void setCombatTransitionListener(
    final ViewManagerObserver curranteViewManagerObserver) {
        this.combatCollision
            .setViewManagerListener(curranteViewManagerObserver);
    }

    //---------Movimento---------

    /**
     * Move the player to the right.
     */
    public void moveRight() {
        this.movementSystem.move(1, 0, pickupSystem, enemySystem);
    }

    /**
     * Move the player to the left.
     */
    public void moveLeft() {
        this.movementSystem.move(-1, 0, pickupSystem, enemySystem);
    }

    /**
     * Move the player to the up.
     */
    public void moveUp() {
        this.movementSystem.move(0, -1, pickupSystem, enemySystem);
    }

    /**
     * Move the player to the down.
     */
    public void moveDown() {
        this.movementSystem.move(0, 1, pickupSystem, enemySystem);
    }
}
