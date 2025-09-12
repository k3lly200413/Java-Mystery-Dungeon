package it.unibo.progetto_oop.overworld.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.overworld.player.adapter_pattern.OverworldPlayerAdapter;

class OverworldPlayerAdapterTest {

    /**
     * object under test.
     */
    private OverworldPlayerAdapter playerAdapter;

    private static final String ERR_MSG = "Amount must not be negative";

    /**
     * costant max hp.
     */
    private static final int MAX_HP = 100;

    /**
     * costant max stamina.
     */
    private static final int MAX_STAMINA = 100;

    /**
     * costant power.
     */
    private static final int POWER = 10;

    /**
     * costant amount 1.
     */
    private static final int AMOUNT_1 = 20;

    /**
     * costant amount 2.
     */
    private static final int AMOUNT_2 = -100;

    @BeforeEach
    void setUp() {
        final var inventory = new Inventory();
        final var player = new Player(MAX_HP, MAX_STAMINA, POWER, inventory);
        playerAdapter = new OverworldPlayerAdapter(player);
    }

    @Test
    void increaseMaxHpTest() {
        playerAdapter.increasePlayerMaxHealth(AMOUNT_1);
        assertEquals(AMOUNT_1 + MAX_HP, playerAdapter.getMaxHp());

        final IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> playerAdapter.increasePlayerMaxHealth(AMOUNT_2)
        );

        assertEquals(ERR_MSG, exception.getMessage());
    }


    @Test
    void increaseStaminaTest() {
        playerAdapter.increasePlayerMaxStamina(AMOUNT_1);
        assertEquals(MAX_STAMINA + AMOUNT_1, playerAdapter.getMaxStamina());

        final IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> playerAdapter.increasePlayerMaxStamina(AMOUNT_2)
        );

        assertEquals(ERR_MSG, exception.getMessage());
    }


    @Test
    void increasePowerTest() {
        playerAdapter.increasePlayerMaxPower(AMOUNT_1);
        assertEquals(POWER + AMOUNT_1, playerAdapter.getPower());

        final IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> playerAdapter.increasePlayerMaxPower(AMOUNT_2)
        );

        assertEquals(ERR_MSG, exception.getMessage());
    }

    @Test
    void healTest() {
        playerAdapter.increasePlayerMaxHealth(AMOUNT_1);
        assertEquals(MAX_HP + AMOUNT_1, playerAdapter.getMaxHp());

        playerAdapter.increasePlayerHealth(AMOUNT_1);
        assertEquals(MAX_HP + AMOUNT_1, playerAdapter.getHp());

        final IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> playerAdapter.increasePlayerHealth(AMOUNT_2)
        );

        assertEquals(ERR_MSG, exception.getMessage());
    }
}
