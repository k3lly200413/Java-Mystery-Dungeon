package it.unibo.progetto_oop.combat.mvc_pattern;

import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;
import java.util.Objects;

import it.unibo.progetto_oop.overworld.player.adapter_pattern.PossibleUser;

/**
 * Adapter class to adapt CombatModel to PossibleUser interface.
 */
public final class CombatModelPossibleUserAdapter implements PossibleUser {
    /**
     * Supplier for the maximum health points of the player.
     */
    private final Supplier<Integer> maxHp;

    /**
     * Supplier for the current health points of the player.
     */
    private final Supplier<Integer> hp;

    /**
     * Supplier for the power points of the player.
     */
    private final Supplier<Integer> power;

    /**
     * Supplier for the maximum stamina points of the player.
     */
    private final Supplier<Integer> maxStamina;

    /**
     * Supplier for the current stamina points of the player.
     */
    private final Supplier<Integer> stamina;

    /**
     * Supplier for the current health points of the player.
     */
    private final IntConsumer increaseHp;

    /**
     * Supplier for the power points of the player.
     */
    private final IntConsumer increasePower;

    /**
     * Supplier for the maximum stamina points of the player.
     */
    private final IntConsumer increaseMaxStamina;

    /**
     * Supplier for the maximum health points of the player.
     */
    private final IntConsumer increaseMaxHealth;

    /**
     * Supplier to set the poisoned state of the player.
     */
    private final Consumer<Boolean> setPoisoned;

    /**
     * Constructor for the CombatModelPossibleUserAdapter.
     *
     * @param maxHp maximum health points for the player
     * @param hp current health points for the player
     * @param power power points for the player
     * @param maxStamina maximum stamina points for the player
     * @param stamina current stamina points for the player
     * @param increaseHp consumer to increase health points for the player
     * @param increasePower consumer to increase power points for the player
     * @param increaseMaxStamina consumer to increase maximum stamina points for the player
     * @param increaseMaxHealth consumer to increase maximum health points for the player
     * @param setPoisoned consumer to set the poisoned state for the player
     */
    public CombatModelPossibleUserAdapter(final Supplier<Integer> maxHp,
                                          final Supplier<Integer> hp,
                                          final Supplier<Integer> power,
                                          final Supplier<Integer> maxStamina,
                                          final Supplier<Integer> stamina,
                                          final IntConsumer increaseHp,
                                          final IntConsumer increasePower,
                                          final IntConsumer increaseMaxStamina,
                                          final IntConsumer increaseMaxHealth,
                                          final Consumer<Boolean> setPoisoned) {
        this.maxHp = Objects.requireNonNull(maxHp);
        this.hp = Objects.requireNonNull(hp);
        this.power = Objects.requireNonNull(power);
        this.maxStamina = Objects.requireNonNull(maxStamina);
        this.stamina = Objects.requireNonNull(stamina);
        this.increaseHp = Objects.requireNonNull(increaseHp);
        this.increasePower = Objects.requireNonNull(increasePower);
        this.increaseMaxStamina = Objects.requireNonNull(increaseMaxStamina);
        this.increaseMaxHealth = Objects.requireNonNull(increaseMaxHealth);
        this.setPoisoned = Objects.requireNonNull(setPoisoned);
    }

    /**
     * Returns the maximum health points of the player.
     *
     * @return the maximum health points of the player
     */
    @Override
    public int getMaxHp() {
        return maxHp.get();
    }

    /**
     * Returns the current health points of the player.
     *
     * @return the current health points of the player
     */
    @Override
    public int getHp() {
        return hp.get();
    }

    /**
     * Returns the power of the player.
     *
     * @return the power of the player
     */
    @Override
    public int getPower() {
        return power.get();
    }

    /**
     * Returns the maximum stamina points of the player.
     *
     * @return the maximum stamina points of the player
     */
    @Override
    public int getMaxStamina() {
        return maxStamina.get();
    }

    /**
     * Increases the health points of the player.
     */
    @Override
    public void increasePlayerHealth(final int amount) {
        increaseHp.accept(amount);
    }

    /**
     * Increases the maximum power of the player.
     */
    @Override
    public void increasePlayerMaxPower(final int amount) {
        increasePower.accept(amount);
    }

    /**
     * Increases the maximum stamina points of the player.
     */
    @Override
    public void increasePlayerMaxStamina(final int amount) {
        increaseMaxStamina.accept(amount);
    }

    /**
     * Increases the maximum health points of the player.
     */
    @Override
    public void increasePlayerMaxHealth(final int amount) {
        increaseMaxHealth.accept(amount);
    }

    /**
     * Sets the player as poisoned.
     */
    @Override
    public void setPlayerPoisoned(final boolean poisoned) {
        setPoisoned.accept(poisoned);
    }

    /**
     * Returns the current stamina points of the player.
     *
     * @return the current stamina points of the player
     */
    @Override
    public int getStamina() {
        return stamina.get();
    }
}
