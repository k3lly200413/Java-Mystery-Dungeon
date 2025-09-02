package it.unibo.progetto_oop.Overworld.Enemy;

public enum EnemyType {
    PATROLLER, // has a fixed patrol path
    FOLLOWER, // if it sees the player it starts following him
    SLEEPER // it is stationary and will only attack if the player gets too close
}