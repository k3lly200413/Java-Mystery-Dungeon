package it.unibo.progetto_oop.OverworldEntitiesGeneratorTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.Overworld.GridNotifier.GridNotifier;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.ImplArrayListStructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.StructureData;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.TileType;
import it.unibo.progetto_oop.Overworld.Player.Player;
import it.unibo.progetto_oop.Overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.Overworld.mvc.OverworldEntitiesGenerator;
import it.unibo.progetto_oop.Overworld.mvc.OverworldModel;
import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.combat.inventory.Item;

public class OverworldEntitiesGeneratorTest {

    private StructureData base, entity;
    private OverworldModel model;
    private Player player;
    private final ArrayList<Enemy> enemies = new ArrayList<>();
    private final ArrayList<Item> items = new ArrayList<>();

    @BeforeEach
    void setup() {
        model = new OverworldModel(enemies, items);

        base = new ImplArrayListStructureData(5, 5);
        entity = new ImplArrayListStructureData(5, 5);
        model.setBaseGridView(base);
        model.setEntityGridView(entity);

        setAll(base, TileType.WALL);
        base.set(0, 0, TileType.ROOM);
        base.set(1, 1, TileType.ROOM);
        base.set(2, 2, TileType.ROOM);
        base.set(3, 3, TileType.ROOM);
        base.set(4, 4, TileType.ROOM);

        setAll(entity, TileType.NONE);
        entity.set(0, 0, TileType.PLAYER);
        entity.set(1,1, TileType.ENEMY);
        entity.set(2,2, TileType.ITEM);
        entity.set(3,3, TileType.ITEM);
        entity.set(4,4, TileType.ENEMY);

        player = new Player(100, new Inventory());

        new OverworldEntitiesGenerator(
            model.getCurrentFloor(),
            player,
            model,
            new GridNotifier(null)
        );
    }

    // 1) #nemici/#item creati == #celle ENEMY/ITEM
    @Test
    void cardinalitaNemiciEItemUgualeAllaGrigliaEntity() {
        int enemiesOnGrid = count(entity, TileType.ENEMY);
        int itemsOnGrid   = count(entity, TileType.ITEM);

        assertEquals(enemiesOnGrid, enemies.size(), "Nemici generati ≠ ENEMY in entity-grid");
        assertEquals(itemsOnGrid,   items.size(),   "Item generati ≠ ITEM in entity-grid");
    }

    // 2) Player si trova in una posizione valida
    @Test
    void playerInPosizioneValida() {
        Position p = player.getPosition();
        assertNotNull(p, "Player non posizionato");
        assertEquals(TileType.ROOM, base.get(p.x(), p.y()), "La cella del player deve essere ROOM sulla base");
    }

    // 3) Ogni item su cella valida
    @Test
    void tuttiGliItemSuCelleValide() {
        for (Item it : items) {
            Position p = it.getPosition();
            assertEquals(TileType.ROOM, base.get(p.x(), p.y()),  "Item su cella non valida della base");
            assertEquals(TileType.ITEM, entity.get(p.x(), p.y()), "Entity-grid non ha ITEM dove l'oggetto è stato creato");
        }
    }

    // 4) Ogni nemico su cella valida
    @Test
    void tuttiINemiciSuCelleValide() {
        for (Enemy e : enemies) {
            Position p = e.getCurrentPosition();
            assertEquals(TileType.ROOM,  base.get(p.x(), p.y()),   "Nemico su cella non valida della base");
            assertEquals(TileType.ENEMY, entity.get(p.x(), p.y()), "Entity-grid non ha ENEMY dove il nemico è stato creato");
        }
    }

    // ---- utility ----
    private static void setAll(StructureData g, TileType t) {
        for (int y = 0; y < g.height(); y++)
            for (int x = 0; x < g.width(); x++)
                g.set(x, y, t);
    }

    private static int count(StructureData g, TileType t) {
        int c = 0;
        for (int y = 0; y < g.height(); y++)
            for (int x = 0; x < g.width(); x++)
                if (g.get(x, y) == t) c++;
        return c;
    }
}
