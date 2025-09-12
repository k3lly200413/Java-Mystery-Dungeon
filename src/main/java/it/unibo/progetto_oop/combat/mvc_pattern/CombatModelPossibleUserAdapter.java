package it.unibo.progetto_oop.combat.mvc_pattern;

import it.unibo.progetto_oop.overworld.player.adapter_pattern.PossibleUser;

public final class CombatModelPossibleUserAdapter implements PossibleUser {
    private final CombatModel model;


    public CombatModelPossibleUserAdapter(final CombatModel model) {
        this.model = model;
    }

    /**
     * Returns the maximum health points of the player.
     *
     * @return the maximum health points of the player
     */
    @Override
    public int getMaxHp() {
        return model.getPlayerMaxHealth();
    }

    /**
     * Returns the current health points of the player.
     *
     * @return the current health points of the player
     */
    @Override
    public int getHp() {
        return model.getPlayerHealth();
    }

    /**
     * Returns the power of the player.
     *
     * @return the power of the player
     */
    @Override
    public int getPower() {
        return model.getPlayerPower();
    }

    /**
     * Returns the maximum stamina points of the player.
     *
     * @return the maximum stamina points of the player
     */
    @Override
    public int getMaxStamina() {
        return model.getMaxStamina();
    }

    /**
     * Increases the health points of the player.
     */
    @Override
    public void increasePlayerHealth(int amount) {
        model.increasePlayerHealth(amount);
    }

    /**
     * Increases the maximum power of the player.
     */
    @Override
    public void increasePlayerMaxPower(int amount) {
        model.increasePlayerMaxPower(amount);
    }

    /**
     * Increases the maximum stamina points of the player.
     */
    @Override
    public void increasePlayerMaxStamina(int amount) {
        model.increasePlayerMaxStamina(amount);
    }

    /**
     * Increases the maximum health points of the player.
     */
    @Override
    public void increasePlayerMaxHealth(int amount) {
        model.increasePlayerMaxHealth(amount);
    }

    /**
     * Sets the player as poisoned.
     */
    @Override
    public void setPlayerPoisoned(boolean poisoned) {
        model.setPlayerPoisoned(poisoned);
    }

    /**
     * Returns the current stamina points of the player.
     *
     * @return the current stamina points of the player
     */
    @Override
    public int getStamina() {
        return model.getStamina();
    }
}
