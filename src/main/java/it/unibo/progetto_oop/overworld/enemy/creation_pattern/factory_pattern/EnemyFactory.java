package it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_pattern;

import it.unibo.progetto_oop.overworld.playGround.Data.Position;
import it.unibo.progetto_oop.overworld.enemy.creation_pattern.factory_impl.Enemy;
import it.unibo.progetto_oop.overworld.grid_notifier.GridNotifier;


public interface EnemyFactory {
    /**
     * create a new patroller enemy.
     * @param hp health points
     * @param power power
     * @param spawnPosition where the enemy will spawn
     * @param isVertical will it be moving vertically
     * @param grid the grid notifier
     * @return a new patroller enemy
     */
    Enemy createPatrollerEnemy(int hp, int power, Position spawnPosition,
    boolean isVertical, GridNotifier grid);


    /**
     * create a new follower enemy.
     * @param hp health points
     * @param power power
     * @param spawnPosition where the enemy will spawn
     * @param isVertical will it be moving vertically
    * @param grid the grid notifier
     * @return a new follower enemy
     */
    Enemy createFollowerEnemy(int hp, int power, Position spawnPosition,
    boolean isVertical, GridNotifier grid);

    /**
     * create a new sleeper enemy.
     * @param hp health points
     * @param power power
     * @param spawnPosition where the enemy will spawn
     * @param isVertical will it be moving vertically
     * @param grid the grid notifier
     * @return a new sleeper enemy
     */
    Enemy createSleeperEnemy(int hp, int power, Position spawnPosition,
    boolean isVertical, GridNotifier grid);

    /**
     * create a new boss enemy.
     * @param hp health points
     * @param power power
     * @param spawnPosition where the enemy will spawn
     * @param isVertical will it be moving vertically
     * @param grid the grid notifier
     * @return a new boss enemy
     */
    Enemy createBossEnemy(int hp, int power, Position spawnPosition,
    boolean isVertical, GridNotifier grid);
}
