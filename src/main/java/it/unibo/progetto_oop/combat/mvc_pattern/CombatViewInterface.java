package it.unibo.progetto_oop.combat.mvc_pattern;

public interface CombatViewInterface {
    public void updatePlayerHealth(final int value);
    public void updatePlayerStamina(final int value);
    public void updateEnemyHealth(final int value);
    public void showInfo(final String text);
    public void clearInfo();
    public void setController(CombatController combatController);
    public void showGameOver(final Runnable onRestart);
}
