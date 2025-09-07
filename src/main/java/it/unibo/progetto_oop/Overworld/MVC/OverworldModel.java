package it.unibo.progetto_oop.Overworld.MVC;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision.CombatCollision;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision.CombatCollisionImpl;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision.WallCollision;
import it.unibo.progetto_oop.Overworld.Enemy.MovementStrategy.WallCollision.WallCollisionImpl;
import it.unibo.progetto_oop.Overworld.GridNotifier.GridNotifier;
import it.unibo.progetto_oop.Overworld.MVC.ModelSystem.EnemySystem;
import it.unibo.progetto_oop.Overworld.MVC.ModelSystem.MovementSystem;
import it.unibo.progetto_oop.Overworld.MVC.ModelSystem.PickupSystem;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.ChangeFloorListener;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.EntityGridUpdater;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.ImplArrayListStructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.Dungeon;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.Floor;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.combat.inventory.Item;

/**
 * OverworldModel: orchestratore del mondo di gioco.
 * - possiede Player e i systems (movement/pickup/enemy)
 * - valida i movimenti contro la griglia del Floor corrente (read-only)
 * - inoltra gli aggiornamenti al Floor tramite GridUpdater (notify*)
 * - contiene il Dungeon e gestisce il cambio piano (binding del Floor corrente)
 */
public final class OverworldModel {

    private Dungeon dungeon;
    private final Player player;
    private boolean inCombat;

    //private Dungeon dungeon;

    private final PickupSystem pickupSystem;
    private final EnemySystem enemySystem;
    private final MovementSystem movementSystem;
    
    // current floor's grid (read-only)
    private StructureData baseGrid;   // playground: WALL/ROOM/TUNNEL/STAIRS
    private StructureData entityGrid; // entities: PLAYER/ENEMY/ITEM/NONE

    // to access the grid
    private GridNotifier gridNotifier; // incapsulates GridUpdater
    private ChangeFloorListener changeFloorListener;
    private WallCollision wallCollision;
    private Consumer<Floor> floorInitializer = f -> {};
    private CombatCollision combatCollision;

    public OverworldModel(final List<Enemy> enemies, final List<Item> items) {
        this.player = new Player(100, new Inventory());
        this.inCombat = false;
        this.gridNotifier = new GridNotifier(null);

        this.pickupSystem = new PickupSystem(null, this.player, this);
        this.enemySystem  = new EnemySystem(null, this.player, this);
        this.movementSystem = new MovementSystem(this.player, this);

        this.combatCollision = new CombatCollisionImpl(this.gridNotifier);

        setSpawnObjects(enemies, items);
    }

    // Collega il dungeon e seleziona il primo Floor
    public void bindDungeon(final Dungeon dungeon) {
        this.dungeon = dungeon;
    }

    // Imposta quale floor è quello attivo
    public void bindCurrentFloor(final Floor floor) {
        if (floor == null) {
            this.baseGrid = null;
            this.entityGrid = null;
            if (this.gridNotifier != null) {
                this.gridNotifier.setGridUpdater(null);
            }
        }
        else {
            this.baseGrid = floor.grid(); // il Floor ha solo il terreno
            entityGrid = new ImplArrayListStructureData(baseGrid.width(), baseGrid.height());
            entityGrid.fill(TileType.NONE);
            if (this.gridNotifier == null) {
                this.gridNotifier = new GridNotifier(null);
            }
            this.gridNotifier.setGridUpdater(new EntityGridUpdater(this.entityGrid));
        }
        this.wallCollision = new WallCollisionImpl(baseGrid, entityGrid);
    }

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

    //---------Setters----------
    public void setSpawnObjects(final List<Enemy> enemies, final List<Item> items) {
        this.pickupSystem.setItems(items);
        this.enemySystem.setEnemies(enemies);
    }

    public void setFloorInitializer(Consumer<Floor> init) {
        this.floorInitializer = Objects.requireNonNull(init);
    }
    
    public void setChangeFloorListener(final ChangeFloorListener l) {
        this.changeFloorListener = l;
    }

    public void setEntityAt(Position p, TileType t) {
        entityGrid.set(p.x(), p.y(), t);
    }
    
    //---------Getters----------
    public Floor getCurrentFloor() {
    return this.dungeon.getCurrentFloor();
    }
    public GridNotifier getGridNotifier() {
        return gridNotifier;
    }
    public Player getPlayer() {
        return this.player;
    }
    public List<Item> getItem() {
        return this.pickupSystem.getItem();
    }
    public Inventory getInventoryInstance() {
        return this.pickupSystem.getInventoryInstance();
    }
    public List<Enemy> getEnemies() {
        return this.enemySystem.getEnemies();
    }
    public Enemy getEncounteredEnemy() {
        return this.enemySystem.getEncounteredEnemy();
    }
    public boolean isInCombat() {
        return this.inCombat;
    }
    public boolean isCombatTransitionPending() {
        return this.movementSystem.isCombatTransitionPending();
    }
    public WallCollision getWallCollision() {
        return this.wallCollision;
    }

    public CombatCollision getCombatCollision() {
        return this.combatCollision;
    }

    public StructureData getBaseGridView() {
        return baseGrid;
    }

    public StructureData getEntityGridView() {
        return entityGrid;
    }

    public TileType getEntityAt(Position p) {
        return entityGrid.get(p.x(), p.y());
    }

    //------Combat flags---------

    public void setEncounteredEnemy(final Enemy e) {
        this.enemySystem.setEncounteredEnemy(e);
    }
    public void clearInCombatFlag() {
        this.inCombat = false;
    }
    public void setInCombatFlag() {
        this.inCombat = true;
    }
    public void clearCombatTransitionFlag() {
        this.movementSystem.clearCombatTransitionFlag();
    }
    public void setCombatTransitionFlag() {
        this.movementSystem.setCombatTransitionFlag();
    }

    //---------Movimento---------

    public void movePlayer(final int dx, final int dy) {
        this.movementSystem.move(dx, dy, this.pickupSystem, this.enemySystem);
    }

    public void setGridNotifier(GridNotifier gridNotifier){
        this.gridNotifier = gridNotifier;
    }
}
