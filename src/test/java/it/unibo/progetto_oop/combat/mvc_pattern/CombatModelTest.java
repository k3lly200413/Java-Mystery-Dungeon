package it.unibo.progetto_oop.combat.mvc_pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.combat.combat_builder.CombatBuilder;
import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * Test di unità per CombatModel.
 * Usa JUnit 5 + Mockito per simulare CombatBuilder.
 */
class CombatModelTest {

    private CombatBuilder builder;
    private CombatModel model;

    // Valori di configurazione coerenti per tutti i test
    private static final int SIZE = 12;
    private static final int PLAYER_MAX_HP = 100;
    private static final int ENEMY_MAX_HP = 80;
    private static final int PLAYER_CURR_HP = 100;
    private static final int ENEMY_CURR_HP = 80;
    private static final int STAMINA_MAX = 10;
    private static final int PLAYER_POWER = 12;
    private static final int PLAYER_POISON_POWER = 3;
    private static final int ENEMY_POWER = 8;
    private static final int ENEMY_SPEED = 1;
    private static final String ENEMY_NAME = "Slime";
    private static final int LONG_RANGE_POWER = 7;

    @BeforeEach
    void setUp() {
        builder = mock(CombatBuilder.class);

        // Stub dei getter usati dal costruttore di CombatModel
        when(builder.getPlayerMaxHealth()).thenReturn(PLAYER_MAX_HP);
        when(builder.getEnemyMaxHealth()).thenReturn(ENEMY_MAX_HP);
        when(builder.getPlayerCurrentHp()).thenReturn(PLAYER_CURR_HP);
        when(builder.getEnemyCurrentHp()).thenReturn(ENEMY_CURR_HP);
        when(builder.getSize()).thenReturn(SIZE);
        when(builder.getStaminaMax()).thenReturn(STAMINA_MAX);
        when(builder.getPlayerPower()).thenReturn(PLAYER_POWER);
        when(builder.getPlayerPoisonPower()).thenReturn(PLAYER_POISON_POWER);
        when(builder.getEnemyPower()).thenReturn(ENEMY_POWER);
        when(builder.getEnemySpeed()).thenReturn(ENEMY_SPEED);
        when(builder.getEnemyName()).thenReturn(ENEMY_NAME);
        when(builder.getPlayerLongRangePower()).thenReturn(LONG_RANGE_POWER);

        model = new CombatModel(builder);
    }

    @Test
    void constructor_initializesCoreFieldsAndPositions() {
        assertEquals(PLAYER_MAX_HP, model.getPlayerMaxHealth());
        assertEquals(ENEMY_MAX_HP, model.getEnemyMaxHealth());
        assertEquals(PLAYER_CURR_HP, model.getPlayerHealth());
        assertEquals(ENEMY_CURR_HP, model.getEnemyHealth());
        assertEquals(STAMINA_MAX, model.getPlayerStaminaMax());
        assertEquals(STAMINA_MAX, model.getPlayerStamina());
        assertEquals(PLAYER_POWER, model.getPlayerPower());
        assertEquals(PLAYER_POWER, model.getBasicPlayerPower());
        assertEquals(PLAYER_POISON_POWER, model.getPlayerPoisonPower());
        assertEquals(PLAYER_POISON_POWER, model.getEnemyPoisonPower());
        assertEquals(LONG_RANGE_POWER, model.getPlayerLongRangePower());
        assertEquals(LONG_RANGE_POWER, model.getEnemyLongRangePower());
        assertEquals(ENEMY_POWER, model.getEnemyPower());
        assertEquals(ENEMY_SPEED, model.getEnemySpeed());
        assertEquals(ENEMY_NAME, model.getEnemyName());

        // Posizioni secondo la logica di resetPositions():
        // player: ((size/3)-2, size/2)
        // enemy:  (size - (size/3) + 1, size/2)
        Position expectedPlayer = new Position((SIZE / 3) - 2, (SIZE / 2));
        Position expectedEnemy  = new Position(SIZE - (SIZE / 3) + 1, (SIZE / 2));

        assertEquals(expectedPlayer, model.getPlayerPosition());
        assertEquals(expectedEnemy, model.getEnemyPosition());

        // Il deathRayPath viene inizializzato con l'enemyPosition
        assertEquals(1, model.getDeathRayPath().size());
        assertEquals(expectedEnemy, model.getDeathRayPath().get(0));

        // Turno e flag iniziali
        assertTrue(model.isPlayerTurn());
        assertFalse(model.isBossTurn());
        assertFalse(model.isEnemyPoisoned());
        assertFalse(model.isPlayerPoison());
        assertFalse(model.isPoisonAnimation());
    }

    @Test
    void healthIncreaseAndDecreaseRespectBounds() {
        // Diminuzione giocatore (no valori negativi)
        model.decreasePlayerHealth(30);
        assertEquals(PLAYER_CURR_HP - 30, model.getPlayerHealth());
        model.decreasePlayerHealth(10_000);
        assertEquals(0, model.getPlayerHealth());

        // Aumento giocatore (cappato al max)
        model.increasePlayerHealth(50); // da 0 a 50
        assertEquals(50, model.getPlayerHealth());
        model.increasePlayerHealth(10_000); // cappato
        assertEquals(PLAYER_MAX_HP, model.getPlayerHealth());

        // Diminuzione nemico
        model.decreaseEnemyHealth(20);
        assertEquals(ENEMY_CURR_HP - 20, model.getEnemyHealth());
        model.decreaseEnemyHealth(10_000);
        assertEquals(0, model.getEnemyHealth());
    }

    @Test
    void staminaIncreaseAndDecreaseRespectBounds() {
        // Decrease cap at 0
        model.decreasePlayerStamina(4);
        assertEquals(STAMINA_MAX - 4, model.getPlayerStamina());
        model.decreasePlayerStamina(10_000);
        assertEquals(0, model.getPlayerStamina());

        // Increase cap at max
        model.increasePlayerStamina(3);
        assertEquals(3, model.getPlayerStamina());
        model.increasePlayerStamina(10_000);
        assertEquals(STAMINA_MAX, model.getPlayerStamina());

        // Modifica dello stamina max (increase/decrease)
        model.increasePlayerMaxStamina(5);
        assertEquals(STAMINA_MAX + 5, model.getPlayerStaminaMax());

        model.decreasePlayerMaxStamina(50);
        assertEquals(0, model.getPlayerStaminaMax()); // non va sotto 0
    }

    @Test
    void powerIncreaseAndResetWork() {
        model.increasePlayerMaxPower(5);
        assertEquals(PLAYER_POWER + 5, model.getPlayerPower());
        model.resetPlayerPower();
        assertEquals(PLAYER_POWER, model.getPlayerPower());

        model.increaseEnemyPower(4);
        assertEquals(ENEMY_POWER + 4, model.getEnemyPower());
    }

    @Test
    void setEnemyPoisonedIsOneWayToTrue() {
        assertFalse(model.isEnemyPoisoned());
        model.setEnemyPoisoned(true);
        assertTrue(model.isEnemyPoisoned());

        // Chiamate successive non devono cambiare nulla (rimane true)
        model.setEnemyPoisoned(false);
        assertTrue(model.isEnemyPoisoned());
        model.setEnemyPoisoned(true);
        assertTrue(model.isEnemyPoisoned());
    }

    @Test
    void applyAttackHealthUpdatesCorrectTargetAndReturnsRemaining() {
        // Il player attacca il nemico
        int remainingEnemy = model.applyAttackHealth(true, 15);
        assertEquals(ENEMY_CURR_HP - 15, remainingEnemy);
        assertEquals(ENEMY_CURR_HP - 15, model.getEnemyHealth());

        // Il nemico attacca il player
        int remainingPlayer = model.applyAttackHealth(false, 20);
        assertEquals(PLAYER_CURR_HP - 20, remainingPlayer);
        assertEquals(PLAYER_CURR_HP - 20, model.getPlayerHealth());
    }

    @Test
    void isGameOverSetsWhoDiedCorrectly() {
        // Nessuno è morto all'inizio
        assertFalse(model.isGameOver());
        assertNull(model.getWhoDied());

        // Uccido il nemico
        model.decreaseEnemyHealth(10_000);
        assertTrue(model.isGameOver());
        assertEquals(model.getEnemyPosition(), model.getWhoDied());

        // Reset ipotetico: ricarico modello per testare il caso player
        setUp();
        model.decreasePlayerHealth(10_000);
        assertTrue(model.isGameOver());
        assertEquals(model.getPlayerPosition(), model.getWhoDied());
    }

    @Test
    void settersAndFlagsWork() {
        // Turni
        model.setPlayerTurn(false);
        assertFalse(model.isPlayerTurn());
        model.setBossTurn(true);
        assertTrue(model.isBossTurn());

        // Poison animation & player poison
        model.setPoisonAnimation(true);
        assertTrue(model.isPoisonAnimation());
        model.setPlayerPoisoned(true);
        assertTrue(model.isPlayerPoison());

        // Boss counters & state
        model.clearBossAttackCount();
        assertEquals(0, model.getBossAttackCounter());
        model.increaseBossAttackCounter();
        model.increaseBossAttackCounter();
        assertEquals(2, model.getBossAttackCounter());

        model.resetBossTurnCounter();
        assertEquals(0, model.getBossTurnCounter());
        model.increaseBossTurnCounter();
        assertEquals(1, model.getBossTurnCounter());

        model.setBossAttackCounter(5);
        assertEquals(5, model.getBossAttackCounter());

        model.setCurrentBossState("ENRAGED");
        assertEquals("ENRAGED", model.getCurrentBossState());
    }

    @Test
    void positionsAndDeathRayPathMutations() {
        // Set posizioni
        Position newPlayerPos = new Position(1, 2);
        Position newEnemyPos  = new Position(8, 9);
        Position atkPos       = new Position(2, 2);

        model.setPlayerPosition(newPlayerPos);
        model.setEnemyPosition(newEnemyPos);
        model.setAttackPosition(atkPos);

        assertEquals(newPlayerPos, model.getPlayerPosition());
        assertEquals(newEnemyPos, model.getEnemyPosition());
        assertEquals(atkPos, model.getAttackPosition());

        // Death ray path
        model.clearDeathRayPath();
        assertTrue(model.getDeathRayPath().isEmpty());

        Position p1 = new Position(3, 3);
        Position p2 = new Position(4, 3);
        model.addDeathRayPosition(p1);
        model.addDeathRayPosition(p2);

        List<Position> path = model.getDeathRayPath();
        assertEquals(2, path.size());
        assertEquals(p1, path.get(0));
        assertEquals(p2, path.get(1));
    }

    @Test
    void maxHpSettersAffectBounds() {
        // Cambio max HP player & enemy e current HP
        model.setPlayerMaxHp(120);
        model.setPlayerCurrentHp(110);
        assertEquals(120, model.getPlayerMaxHealth());
        assertEquals(110, model.getPlayerHealth());

        model.setEnemyMaxHp(60);
        model.setEnemyCurrentHp(55);
        assertEquals(60, model.getEnemyMaxHealth());
        assertEquals(55, model.getEnemyHealth());
    }
}
