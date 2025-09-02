package it.unibo.progetto_oop.Overworld.MVC;

import java.util.List;

import it.unibo.progetto_oop.Combat.Inventory.Inventory;
import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.GridNotifier.GridNotifier;
import it.unibo.progetto_oop.Overworld.MVC.ModelSystem.EnemySystem;
import it.unibo.progetto_oop.Overworld.MVC.ModelSystem.MovementSystem;
import it.unibo.progetto_oop.Overworld.MVC.ModelSystem.PickupSystem;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
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

    private Dungeon dungeon;

    private final PickupSystem pickupSystem;
    private final EnemySystem enemySystem;
    private final MovementSystem movementSystem;

    // to access the grid
    public GridNotifier gridNotifier; // incapsula GridUpdater
    private StructureData gridView; // read-only

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
        this.gridView = floor.grid();       // read-only
        this.gridNotifier.setGridUpdater(floor); // Floor implementa GridUpdater
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

    public void setGridNotifier(GridNotifier gridNotifier){
        this.gridNotifier = gridNotifier;
    }
}
