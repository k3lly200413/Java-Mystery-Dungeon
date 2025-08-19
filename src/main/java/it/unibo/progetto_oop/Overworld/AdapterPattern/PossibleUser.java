package it.unibo.progetto_oop.Overworld.AdapterPattern;

public interface PossibleUser {
    int getHp();
    int getMaxHP();
    void increasePlayerHealth(int amount);
    void increasePlayerPower(int amount);
    void setPlayerPoisoned(boolean poisoned);
}