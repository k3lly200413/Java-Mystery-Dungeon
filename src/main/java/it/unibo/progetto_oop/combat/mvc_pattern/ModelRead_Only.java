package it.unibo.progetto_oop.combat.mvc_pattern;

import it.unibo.progetto_oop.overworld.playground.data.Position;

public interface ModelRead_Only {
    int getPlayerHealth();
    int getPlayerMaxHealth();
    int getEnemyHealth();
    int getEnemyMaxHealth();
    int getPlayerPower();
    int getEnemyPower();
    int getPlayerStamina();
    int getPlayerStaminaMax();
    int getPlayerPoisonPower();
    int getEnemyPoisonPower();
    int getPlayerLongRangePower();
    int getEnemyLongRangePower();
    boolean isPlayerTurn();
    boolean isGameOver();
    boolean isEnemyPoisoned();
    boolean isPlayerPoison();
    int getSize();
    Position getPlayerPosition();
    Position getEnemyPosition();
    Position getAttackPosition();

}
