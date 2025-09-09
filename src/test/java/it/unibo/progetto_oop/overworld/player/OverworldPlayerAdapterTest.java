package it.unibo.progetto_oop.overworld.player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class OverworldPlayerAdapterTest {

    /**
     * increase the player's max hp value.
     * @param amount the amount to increase
     */
    @Test
    void increaseMaxHpTest() {
        var player = new Player(100, 100, 10, inventory);

        //player.setHp(30);
        //assertEquals(30, player.getCurrentHp());

        player.increaseMaxHp(20);
        assertEquals(120, player.getMaxHp());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> player.increaseMaxHp(-100)
        );

        assertEquals("Amount must not be negative", exception.getMessage());
    }

    
    /**
     * increase the player's stamina.
     * @param amount the amount to increase.
     */
    @Test
    void increaseStaminaTest() {
        var player = new Player(100, 100, 10, inventory);

        player.increaseStamina(15);
        assertEquals(115, player.getStamina());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> player.increaseStamina(-100)
        );

        assertEquals("Amount must not be negative", exception.getMessage());
    }

    /**
     * Increase the player power.
     * @param amount the amount to increase.
     */
    @Test
    public void increasePowerTest() {
        var player = new Player(100, 100, 10, inventory);

        player.increasePower(5);
        assertEquals(15, player.getPower());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> player.increasePower(-100)
        );

        assertEquals("Amount must not be negative", exception.getMessage());
    }

    /**
     * increase the player current hp.
     * @param amount the amount to increase
     */
    @Test
    void healTest() {
        var player = new Player(100, 100, 10, inventory);

        player.setHp(50);
        assertEquals(50, player.getCurrentHp());

        player.heal(30);
        assertEquals(80, player.getCurrentHp());

        player.heal(50);
        assertEquals(100, player.getCurrentHp());

        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> player.heal(-100)
        );

        assertEquals("Amount must not be negative", exception.getMessage());
    }   
    
}
