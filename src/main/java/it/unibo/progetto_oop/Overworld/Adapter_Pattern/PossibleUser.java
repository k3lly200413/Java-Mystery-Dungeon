package it.unibo.progetto_oop.Overworld.Adapter_Pattern;

public interface PossibleUser {
    int getHp();
    int getMaxHP();
    void increasePlayerHealth(int amount);
    void increasePlayerPower(int amount);
}