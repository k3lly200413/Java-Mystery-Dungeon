package it.unibo.progetto_oop.Overworld.Enemy;

public interface EnemyFactory {
    public Enemy createpatrollerEnemy(); 
    public Enemy createFollowerEnemy(); 
    public Enemy createSleeperEnemy();
}
