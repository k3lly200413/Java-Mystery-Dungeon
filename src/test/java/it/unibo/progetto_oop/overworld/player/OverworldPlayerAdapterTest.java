package it.unibo.progetto_oop.overworld.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.progetto_oop.combat.inventory.Inventory;
import it.unibo.progetto_oop.overworld.player.adapter_pattern.OverworldPlayerAdapter;

public class OverworldPlayerAdapterTest {

    OverworldPlayerAdapter playerAdapter;
    @BeforeEach
    void setUp() {
        var inventory = new Inventory();
        var player = new Player(100, 100, 10, inventory);
        playerAdapter = new OverworldPlayerAdapter(player);
    }

    /**
     * increase the player's max hp value.
     * @param amount the amount to increase
     */
    @Test
    void increaseMaxHpTest() {
        playerAdapter.increasePlayerMaxHealth(20);
        assertEquals(120, playerAdapter.getMaxHp());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> playerAdapter.increasePlayerMaxHealth(-100)
        );

        assertEquals("Amount must not be negative", exception.getMessage());
    }

    
    /**
     * increase the player's stamina.
     * @param amount the amount to increase.
     */
    @Test
    void increaseStaminaTest() {
        

        playerAdapter.increasePlayerMaxStamina(15);
        assertEquals(115, playerAdapter.getMaxStamina());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> playerAdapter.increasePlayerMaxStamina(-100)
        );

        assertEquals("Amount must not be negative", exception.getMessage());
    }

    /**
     * Increase the player power.
     * @param amount the amount to increase.
     */
    @Test
    public void increasePowerTest() {
        playerAdapter.increasePlayerMaxPower(5);
        assertEquals(15, playerAdapter.getPower());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> playerAdapter.increasePlayerMaxPower(-100)
        );

        assertEquals("Amount must not be negative", exception.getMessage());
    }

    /**
     * increase the player current hp.
     * @param amount the amount to increase
     */
    @Test
    void healTest() {
        playerAdapter.increasePlayerMaxHealth(50);
        assertEquals(150, playerAdapter.getMaxHp());

        playerAdapter.increasePlayerHealth(30);
        assertEquals(130, playerAdapter.getHp());

        playerAdapter.increasePlayerHealth(50);
        assertEquals(150, playerAdapter.getHp());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> playerAdapter.increasePlayerHealth(-100)
        );

        assertEquals("Amount must not be negative", exception.getMessage());
    }   
    
}
