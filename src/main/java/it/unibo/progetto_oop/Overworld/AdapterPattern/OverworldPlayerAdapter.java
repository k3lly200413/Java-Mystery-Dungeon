package it.unibo.progetto_oop.Overworld.AdapterPattern;

import it.unibo.progetto_oop.Overworld.Player.Player;

/**
 * OverworldPlayerAdapter - used to break the dependency between potions and CombatModel
 * PossibleUser could be also an enemy
 * thanks to this class potions could be used also outside combat --> in the overworld
 */

public class OverworldPlayerAdapter implements PossibleUser{

    private final Player adaptedPlayer;

    public OverworldPlayerAdapter(Player playerToAdapt){
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
    public void increasePlayerHealth(int amount) {
        this.adaptedPlayer.heal(amount);
    }

    @Override
    public void increasePlayerPower(int amount) {
        this.adaptedPlayer.setHp(amount);
    }

    @Override
    public void setPlayerPoisoned(boolean poisoned) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPlayerPoisoned'");
    }
    
}
