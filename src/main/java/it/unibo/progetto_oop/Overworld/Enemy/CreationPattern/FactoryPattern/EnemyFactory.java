package it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryPattern;

import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.GridNotifier.GridNotifier;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;


public interface EnemyFactory {
    /**
     * create a new patroller enemy.
     * @param hp health points
     * @param power power
     * @param spawnPosition where the enemy will spawn 
     * @param isVertical will it be moving vertically
     * @param walls walls in the middle of the current floor
     * @return a new patroller enemy
     */
    public Enemy createPatrollerEnemy(int hp, int power, Position spawnPosition, boolean isVertical, GridNotifier grid); 


    /**
     * create a new follower enemy.
     * @param hp health points
     * @param power power
     * @param spawnPosition where the enemy will spawn 
     * @param isVertical will it be moving vertically
     * @param walls walls in the middle of the current floor
     * @return a new follower enemy
     */
    public Enemy createFollowerEnemy(int hp, int power, Position spawnPosition, boolean isVertical, GridNotifier grid); 

    /**
     * create a new sleeper enemy.
     * @param hp health points
     * @param power power
     * @param spawnPosition where the enemy will spawn 
     * @param isVertical will it be moving vertically
     * @param walls walls in the middle of the current floor
     * @return a new sleeper enemy
     */
    public Enemy createSleeperEnemy(int hp, int power, Position spawnPosition, boolean isVertical, GridNotifier grid);

    /**
     * create a new boss enemy.
     * @param hp health points
     * @param power power
     * @param spawnPosition where the enemy will spawn 
     * @param isVertical will it be moving vertically
     * @param walls walls in the middle of the current floor
     * @return a new boss enemy
     */
    public Enemy createBossEnemy(int hp, int power, Position spawnPosition, boolean isVertical, GridNotifier grid);
}
