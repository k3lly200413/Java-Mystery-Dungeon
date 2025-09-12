package it.unibo.progetto_oop.combat.mvc_pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.combat.combat_builder.CombatBuilder;
import it.unibo.progetto_oop.overworld.playground.data.Position;

class CombatModelTest {

    /** Size of the combat grid. */
    private static final int SIZE = 12;

    /** Player's maximum health points. */
    private static final int PLAYER_MAX_HP = 100;

    /** Enemy's maximum health points. */
    private static final int ENEMY_MAX_HP = 80;

    /** Player's current health points. */
    private static final int PLAYER_CURR_HP = 100;

    /** Enemy's current health points. */
    private static final int ENEMY_CURR_HP = 80;

    /** Player's stamina points. */
    private static final int STAMINA_MAX = 10;

    /** Player's power points. */
    private static final int PLAYER_POWER = 12;

    /** Player's poison power points. */
    private static final int PLAYER_POISON_POWER = 3;

    /** Enemy's speed points. */
    private static final int ENEMY_SPEED = 1;

    /** Player's long range power points. */
    private static final int LONG_RANGE_POWER = 7;

    /** Enemy's name. */
    private static final String ENEMY_NAME = "Slime";

    /** Divisor for three. */
    private static final int DIV_THREE = 3;

    /** Divisor for two. */
    private static final int HALF_DIVISOR = 2;

    /** Player's X offset. */
    private static final int PLAYER_X_OFFSET = 2;

    /** Enemy's X offset. */
    private static final int ENEMY_OFFSET = 1;

    /** Big damage value. */
    private static final int BIG_DAMAGE = 10_000;

    /** Constant used for health calculations. */
    private static final int DAMAGE_150 = 150;

    /** Damage value for 15. */
    private static final int DAMAGE_15 = 15;

    /** Damage value for 1000. */
    private static final int DAMAGE_1000 = 1000;

    /** Stamina value for 100. */
    private static final int STAMINA_DEC_100 = 100;

    /** Stamina value for 999. */
    private static final int STAMINA_INC_999 = 999;

    /** Combat model instance. */
    private CombatModel model;

    @BeforeEach
    void setUpCombatModel() {
        final FakeCombatBuilder b = new FakeCombatBuilder()
            .withPlayerMaxHp(PLAYER_MAX_HP).withEnemyMaxHp(ENEMY_MAX_HP)
            .withPlayerCurrHp(PLAYER_CURR_HP).withEnemyCurrHp(ENEMY_CURR_HP)
            .withSize(SIZE).withStaminaMax(STAMINA_MAX)
            .withPlayerPower(PLAYER_POWER)
            .withPlayerPoisonPower(PLAYER_POISON_POWER)
            .withEnemySpeed(ENEMY_SPEED)
            .withEnemyName(ENEMY_NAME).withLongRangePower(LONG_RANGE_POWER);

        this.model = new CombatModel(b);
    }

    @Test
    void constructorInitializesAndRespectsPositions() {
        assertEquals(PLAYER_MAX_HP, this.model.getPlayerMaxHealth());
        assertEquals(ENEMY_MAX_HP, this.model.getEnemyMaxHealth());
        assertEquals(PLAYER_CURR_HP, this.model.getPlayerHealth());
        assertEquals(ENEMY_CURR_HP, this.model.getEnemyHealth());
        assertEquals(STAMINA_MAX, this.model.getPlayerStaminaMax());
        assertEquals(STAMINA_MAX, this.model.getPlayerStamina());
        assertEquals(PLAYER_POWER, this.model.getPlayerPower());
        assertEquals(PLAYER_POISON_POWER, this.model.getPlayerPoisonPower());
        assertEquals(PLAYER_POISON_POWER, this.model.getEnemyPoisonPower());
        assertEquals(LONG_RANGE_POWER, this.model.getPlayerLongRangePower());
        assertEquals(LONG_RANGE_POWER, this.model.getEnemyLongRangePower());
        assertEquals(ENEMY_SPEED, this.model.getEnemySpeed());
        assertEquals(ENEMY_NAME, this.model.getEnemyName());

        final Position expectedPlayer = new Position(
            (SIZE / DIV_THREE) - PLAYER_X_OFFSET, SIZE / HALF_DIVISOR);
        final Position expectedEnemy = new Position(SIZE - (
            (SIZE / DIV_THREE) - ENEMY_OFFSET), SIZE / HALF_DIVISOR);

        assertEquals(expectedPlayer, this.model.getPlayerPosition());
        assertEquals(expectedEnemy, this.model.getEnemyPosition());
        assertTrue(this.model.isPlayerTurn());
    }

    @Test
    void hpAndStaminaBounds() {
        this.model.decreasePlayerHealth(DAMAGE_150);
        assertEquals(0, this.model.getPlayerHealth());
        this.model.increasePlayerHealth(DAMAGE_1000);
        assertEquals(this.model.getPlayerMaxHealth(),
        this.model.getPlayerHealth());

        this.model.decreaseEnemyHealth(DAMAGE_1000);
        assertEquals(0, this.model.getEnemyHealth());

        this.model.decreasePlayerStamina(STAMINA_DEC_100);
        assertEquals(0, this.model.getPlayerStamina());
        this.model.increasePlayerStamina(STAMINA_INC_999);
        assertEquals(this.model.getPlayerStaminaMax(),
        this.model.getPlayerStamina());
    }

    @Test
    void applyAttackAndGameOver() {
        final int remEnemy = this.model.applyAttackHealth(true, DAMAGE_15);
        assertEquals(ENEMY_CURR_HP - DAMAGE_15, remEnemy);
        assertFalse(this.model.isGameOver());

        this.model.decreaseEnemyHealth(BIG_DAMAGE);
        assertTrue(this.model.isGameOver());
        assertEquals(this.model.getEnemyPosition(), this.model.getWhoDied());
    }

    private static final class FakeCombatBuilder extends CombatBuilder {

        /** Size of the combat grid. */
        private int size;

        /** Player's maximum health points. */
        private int staminaMax;

        /** The base attack power of the player. */
        private int playerPower;

        /** The poison attack power of the player. */
        private int playerPoisonPower;

        /** The speed value of the enemy. */
        private int enemySpeed;

        /** The name of the enemy. */
        private String enemyName;

        /** The maximum health points of the player. */
        private int playerMaxHealth;

        /** The maximum health points of the enemy. */
        private int enemyMaxHealth;

        /** The current health points of the player. */
        private int playerCurrentHp;

        /** The current health points of the enemy. */
        private int enemyCurrentHp;

        /** The long-range attack power of the player. */
        private int playerLongRangePower;

        FakeCombatBuilder withSize(final int v) {
            this.size = v;
            return this;
        }

        FakeCombatBuilder withStaminaMax(final int v) {
            this.staminaMax = v;
            return this;
        }

        FakeCombatBuilder withPlayerPower(final int v) {
            this.playerPower = v;
            return this;
        }

        FakeCombatBuilder withPlayerPoisonPower(final int v) {
            this.playerPoisonPower = v;
            return this;
        }

        FakeCombatBuilder withEnemySpeed(final int v) {
            this.enemySpeed = v;
            return this;
        }

        FakeCombatBuilder withEnemyName(final String v) {
            this.enemyName = v;
            return this;
        }

        FakeCombatBuilder withPlayerMaxHp(final int v) {
            this.playerMaxHealth = v;
            return this;
        }

        FakeCombatBuilder withEnemyMaxHp(final int v) {
            this.enemyMaxHealth = v;
            return this;
        }

        FakeCombatBuilder withPlayerCurrHp(final int v) {
            this.playerCurrentHp = v;
            return this;
        }

        FakeCombatBuilder withEnemyCurrHp(final int v) {
            this.enemyCurrentHp = v;
            return this;
        }

        FakeCombatBuilder withLongRangePower(final int v) {
            this.playerLongRangePower = v;
            return this;
        }

        // Getters called by CombatModel
        @Override public int getSize() {
            return this.size;
        }

        @Override public int getStaminaMax() {
            return this.staminaMax;
        }

        @Override public int getPlayerPower() {
            return this.playerPower;
        }

        @Override public int getPlayerPoisonPower() {
            return this.playerPoisonPower;
        }

        @Override public int getEnemySpeed() {
            return this.enemySpeed;
        }

        @Override public String getEnemyName() {
            return this.enemyName;
        }

        @Override public int getPlayerMaxHealth() {
            return this.playerMaxHealth;
        }

        @Override public int getEnemyMaxHealth() {
            return this.enemyMaxHealth;
        }

        @Override public int getPlayerCurrentHp() {
            return this.playerCurrentHp;
        }

        @Override public int getEnemyCurrentHp() {
            return this.enemyCurrentHp;
        }

        @Override public int getPlayerLongRangePower() {
            return this.playerLongRangePower;
        }

    }
}
