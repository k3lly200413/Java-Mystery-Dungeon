package it.unibo.progetto_oop.Overworld.AdapterPattern;

import it.unibo.progetto_oop.Overworld.Player.Player;

/**
 * OverworldPlayerAdapter - used to break the dependency between potions and CombatModel
 * PossibleUser could be also an enemy
 * thanks to this class potions could be used also outside combat
 * --> in the overworld
 */

public final class OverworldPlayerAdapter implements PossibleUser {

    /**
     * the player to adapt.
     */
    private final Player adaptedPlayer;

    /**
     * constructor.
     * @param playerToAdapt the player to adapt
     */
    public OverworldPlayerAdapter(final Player playerToAdapt) {
        if (playerToAdapt == null) {
            throw new IllegalArgumentException("Player to adapt is null");
        }
        this.adaptedPlayer = playerToAdapt;
    }

    @Override
    public int getHp() {
        return this.adaptedPlayer.getCurrentHp();
    }

    @Override
    public int getMaxHP() {
        return this.adaptedPlayer.getMaxHp();
    }

    @Override
    public void increasePlayerHealth(final int amount) {
        this.adaptedPlayer.heal(amount);
    }

    @Override
    public void increasePlayerMaxPower(final int amount) {
        this.adaptedPlayer.increasePower(amount);
    }

    @Override
    public void increasePlayerMaxStamina(final int amount) {
        this.adaptedPlayer.increaseStamina(amount);
    }

    @Override
    public void increasePlayerMaxHealth(final int amount) {
        this.adaptedPlayer.increaseMaxHp(amount);
    }

    @Override
    public void setPlayerPoisoned(final boolean poisoned) {
    }
}
