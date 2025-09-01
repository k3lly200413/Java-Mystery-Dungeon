package it.unibo.progetto_oop.Overworld.MVC;

import java.util.List;

import it.unibo.progetto_oop.Combat.Inventory.Inventory;
import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.MVC.ModelSystem.EnemySystem;
import it.unibo.progetto_oop.Overworld.MVC.ModelSystem.MovementSystem;
import it.unibo.progetto_oop.Overworld.MVC.ModelSystem.PickupSystem;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.GridUpdater;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.Dungeon;
import it.unibo.progetto_oop.Overworld.PlayGround.DungeonLogic.Floor;
import it.unibo.progetto_oop.Overworld.Player.Player;

/**
 * OverworldModel: orchestratore del mondo di gioco.
 * - possiede Player e i systems (movement/pickup/enemy)
 * - valida i movimenti contro la griglia del Floor corrente (read-only)
 * - inoltra gli aggiornamenti al Floor tramite GridUpdater (notify*)
 * - contiene il Dungeon e gestisce il cambio piano (binding del Floor corrente)
 */
public final class OverworldModel {

    private final Player player;
    private boolean inCombat;

    private final PickupSystem pickupSystem;
    private final EnemySystem enemySystem;
    private final MovementSystem movementSystem;

    private Dungeon dungeon;
    private StructureData gridView;   // read-only
    private GridUpdater grid;

    public OverworldModel(final List<Enemy> enemies, final List<Item> items) {
        this.player = new Player(100, new Inventory());
        this.inCombat = false;

        this.pickupSystem = new PickupSystem(null, this.player, this);
        this.enemySystem  = new EnemySystem(null, this.player, this);
        this.movementSystem = new MovementSystem(null, this.player, this);

        setSpawnObjects(enemies, items);
    }

    // Collega il dungeon e seleziona il primo Floor
    public void bindDungeon(final Dungeon dungeon) {
        this.dungeon = dungeon;
        bindCurrentFloor(dungeon.getCurrentFloor());
    }

    // Imposta quale floor è quello attivo
    public void bindCurrentFloor(final Floor floor) {
        this.grid = floor;            // Floor implementa GridUpdater
        this.gridView = floor.grid(); // read-only per movimenti
    }

    public boolean nextFloor() {
        final boolean changedFloor = this.dungeon.nextFloor();
        if (changedFloor) {
            bindCurrentFloor(dungeon.getCurrentFloor());
        }
        return changedFloor;
    }

    public void setSpawnObjects(final List<Enemy> enemies, final List<Item> items) {
        this.pickupSystem.setItems(items);
        this.enemySystem.setEnemies(enemies);
    }

    //---------Getters----------
/* 
    public Floor getCurrentFloor() {
        return this.dungeon.getCurrentFloor();
    }*/

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

    public boolean inBounds(final Position p) {
        if (this.gridView == null) return false;
        return p.x() >= 0 && p.y() >= 0 && p.x() < this.gridView.width() && p.y() < this.gridView.height();
    }

    // Regola entrabile = inBounds && non WALL
    public boolean canEnter(final Position to) {
        if (!inBounds(to))
            return false;
        return this.gridView.get(to.x(), to.y()) != TileType.WALL;
    }

    
    public void setGridUpdater(final GridUpdater grid) {
        this.grid = grid;
        if (grid instanceof Floor f) {
            this.gridView = f.grid();
        }
    }
    public void notifyPlayerMoved(final Position from, final Position to) {
        if (this.grid != null) this.grid.onPlayerMove(from, to);
    }
    public void notifyEnemyMoved(final Position from, final Position to) {
        if (this.grid != null) this.grid.onEnemyMove(from, to);
    }
    public void notifyItemRemoved(final Position at) {
        if (this.grid != null) this.grid.onItemRemoved(at);
    }
    public void notifyEnemyRemoved(final Position at) {
        if (this.grid != null) this.grid.onEnemyRemoved(at);
    }
}
