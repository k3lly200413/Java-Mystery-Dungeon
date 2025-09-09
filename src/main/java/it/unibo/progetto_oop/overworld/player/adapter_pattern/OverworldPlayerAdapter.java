package it.unibo.progetto_oop.overworld.player.adapter_pattern;

import it.unibo.progetto_oop.overworld.player.Player;

/**
 * OverworldPlayerAdapter - used to break the dependency between
 * potions and CombatModel.
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
    public int getMaxHp() {
        return this.adaptedPlayer.getMaxHp();
    }

    @Override
    public int getPower() {
        return this.adaptedPlayer.getPower();
    }

    @Override
    public int getStamina() {
        return this.adaptedPlayer.getStamina();
    }

    @Override
    public int getMaxStamina() {
        return this.adaptedPlayer.getMaxStamina();
    }

    @Override
    public void increasePlayerHealth(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must not be negative");
        }
        if (this.adaptedPlayer.getCurrentHp() != 
        this.adaptedPlayer.getMaxHp() 
        && this.adaptedPlayer.getCurrentHp() != 0) {
                // if currentHP + amount > maxHP, set it to maxHP
                this.adaptedPlayer.setHp(
                    Math.min(
                        this.adaptedPlayer.getMaxHp(), 
                        this.adaptedPlayer.getCurrentHp() + amount));
            }
    }

    @Override
    public void increasePlayerMaxPower(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must not be negative");
        }
        this.adaptedPlayer.setPower(this.adaptedPlayer.getPower() + amount);
    }

    @Override
    public void increasePlayerMaxStamina(final int amount) {
        if (amount < 0) {
        throw new IllegalArgumentException("Amount must not be negative");
        }
        this.adaptedPlayer.setMaxStamina(amount + this.adaptedPlayer.getMaxStamina());
    }

    @Override
    public void increasePlayerMaxHealth(final int amount) {
        if (amount < 0) {
        throw new IllegalArgumentException("Amount must not be negative");
        }
        this.adaptedPlayer.setMaxHp(amount + this.adaptedPlayer.getMaxHp());
    }

    @Override
    public void setPlayerPoisoned(final boolean poisoned) {
        throw new UnsupportedOperationException("Overworld player cannot be poisoned here");
    }
}
