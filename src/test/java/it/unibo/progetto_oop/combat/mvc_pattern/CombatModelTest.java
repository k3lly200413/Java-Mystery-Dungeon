package it.unibo.progetto_oop.combat.mvc_pattern;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import it.unibo.progetto_oop.combat.combat_builder.CombatBuilder;
import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * Unit tests for {@link CombatModel}. Uses JUnit 5 and Mockito to stub the builder and verify behavior.
 */
class CombatModelTest {

    private CombatBuilder builder;
    private CombatModel model;

    // ===== configuration values used across tests =====
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

    // ===== avoid magic numbers in assertions/logic =====
    private static final int DIV_THREE = 3;
    private static final int HALF_DIVISOR = 2;
    private static final int ENEMY_OFFSET = 1;

    private static final int DMG_30 = 30;
    private static final int DMG_20 = 20;
    private static final int DMG_15 = 15;
    private static final int DMG_BIG = 10_000;

    private static final int STAMINA_DEC_4 = 4;
    private static final int STAMINA_INC_3 = 3;
    private static final int STAMINA_INC_5 = 5;
    private static final int STAMINA_DEC_50 = 50;

    private static final int POS_1 = 1;
    private static final int POS_2 = 2;
    private static final int POS_3 = 3;
    private static final int POS_4 = 4;
    private static final int POS_8 = 8;
    private static final int POS_9 = 9;

    @BeforeEach
    void setUp() {
        builder = mock(CombatBuilder.class);

        // Stubs used by CombatModel constructor
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

        // Positions according to resetPositions():
        // player: ((size / 3) - 2, size / 2)
        // enemy:  (size - ((size / 3) - 1), size / 2)
        Position expectedPlayer = new Position((SIZE / DIV_THREE) - POS_2, (SIZE / HALF_DIVISOR));
        Position expectedEnemy  = new Position(SIZE - ((SIZE / DIV_THREE) - ENEMY_OFFSET), (SIZE / HALF_DIVISOR));

        assertEquals(expectedPlayer, model.getPlayerPosition());
        assertEquals(expectedEnemy, model.getEnemyPosition());

        // Death ray path starts with enemyPosition
        assertEquals(1, model.getDeathRayPath().size());
        assertEquals(expectedEnemy, model.getDeathRayPath().get(0));

        // Initial flags
        assertTrue(model.isPlayerTurn());
        assertFalse(model.isBossTurn());
        assertFalse(model.isEnemyPoisoned());
        assertFalse(model.isPlayerPoison());
        assertFalse(model.isPoisonAnimation());
    }

    @Test
    void healthIncreaseAndDecreaseRespectBounds() {
        // Decrease player (no negatives)
        model.decreasePlayerHealth(DMG_30);
        assertEquals(PLAYER_CURR_HP - DMG_30, model.getPlayerHealth());
        model.decreasePlayerHealth(DMG_BIG);
        assertEquals(0, model.getPlayerHealth());

        // Increase player (capped at max)
        model.increasePlayerHealth(50); // 0 -> 50
        assertEquals(50, model.getPlayerHealth());
        model.increasePlayerHealth(DMG_BIG); // capped
        assertEquals(PLAYER_MAX_HP, model.getPlayerHealth());

        // Decrease enemy
        model.decreaseEnemyHealth(DMG_20);
        assertEquals(ENEMY_CURR_HP - DMG_20, model.getEnemyHealth());
        model.decreaseEnemyHealth(DMG_BIG);
        assertEquals(0, model.getEnemyHealth());
    }

    @Test
    void staminaIncreaseAndDecreaseRespectBounds() {
        // Decrease capped at 0
        model.decreasePlayerStamina(STAMINA_DEC_4);
        assertEquals(STAMINA_MAX - STAMINA_DEC_4, model.getPlayerStamina());
        model.decreasePlayerStamina(DMG_BIG);
        assertEquals(0, model.getPlayerStamina());

        // Increase capped at max
        model.increasePlayerStamina(STAMINA_INC_3);
        assertEquals(STAMINA_INC_3, model.getPlayerStamina());
        model.increasePlayerStamina(DMG_BIG);
        assertEquals(STAMINA_MAX, model.getPlayerStamina());

        // Change max stamina (increase/decrease)
        model.increasePlayerMaxStamina(STAMINA_INC_5);
        assertEquals(STAMINA_MAX + STAMINA_INC_5, model.getPlayerStaminaMax());

        model.decreasePlayerMaxStamina(STAMINA_DEC_50);
        assertEquals(0, model.getPlayerStaminaMax()); // not below 0
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

        // Subsequent calls do not revert to false
        model.setEnemyPoisoned(false);
        assertTrue(model.isEnemyPoisoned());
        model.setEnemyPoisoned(true);
        assertTrue(model.isEnemyPoisoned());
    }

    @Test
    void applyAttackHealthUpdatesCorrectTargetAndReturnsRemaining() {
        // Player attacks enemy
        int remainingEnemy = model.applyAttackHealth(true, DMG_15);
        assertEquals(ENEMY_CURR_HP - DMG_15, remainingEnemy);
        assertEquals(ENEMY_CURR_HP - DMG_15, model.getEnemyHealth());

        // Enemy attacks player
        int remainingPlayer = model.applyAttackHealth(false, DMG_20);
        assertEquals(PLAYER_CURR_HP - DMG_20, remainingPlayer);
        assertEquals(PLAYER_CURR_HP - DMG_20, model.getPlayerHealth());
    }

    @Test
    void isGameOverSetsWhoDiedCorrectly() {
        // Initially no one is dead
        assertFalse(model.isGameOver());
        assertNull(model.getWhoDied());

        // Kill enemy
        model.decreaseEnemyHealth(DMG_BIG);
        assertTrue(model.isGameOver());
        assertEquals(model.getEnemyPosition(), model.getWhoDied());

        // Reset (reinitialize) to test player death case
        setUp();
        model.decreasePlayerHealth(DMG_BIG);
        assertTrue(model.isGameOver());
        assertEquals(model.getPlayerPosition(), model.getWhoDied());
    }

    @Test
    void settersAndFlagsWork() {
        // Turns
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
        // Set positions
        Position newPlayerPos = new Position(POS_1, POS_2);
        Position newEnemyPos  = new Position(POS_8, POS_9);
        Position atkPos       = new Position(POS_2, POS_2);

        model.setPlayerPosition(newPlayerPos);
        model.setEnemyPosition(newEnemyPos);
        model.setAttackPosition(atkPos);

        assertEquals(newPlayerPos, model.getPlayerPosition());
        assertEquals(newEnemyPos, model.getEnemyPosition());
        assertEquals(atkPos, model.getAttackPosition());

        // Death ray path
        model.clearDeathRayPath();
        assertTrue(model.getDeathRayPath().isEmpty());

        Position p1 = new Position(POS_3, POS_3);
        Position p2 = new Position(POS_4, POS_3);
        model.addDeathRayPosition(p1);
        model.addDeathRayPosition(p2);

        List<Position> path = model.getDeathRayPath();
        assertEquals(2, path.size());
        assertEquals(p1, path.get(0));
        assertEquals(p2, path.get(1));
    }

    @Test
    void maxHpSettersAffectBounds() {
        // Change player & enemy max HP and current HP
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
