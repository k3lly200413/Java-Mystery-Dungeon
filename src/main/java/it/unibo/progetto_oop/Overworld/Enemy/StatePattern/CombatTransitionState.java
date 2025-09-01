package it.unibo.progetto_oop.Overworld.Enemy.StatePattern;

import it.unibo.progetto_oop.Overworld.Enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class CombatTransitionState implements GenericEnemyState{
    private final EnemyType enemyType;

    public CombatTransitionState(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    @Override
    public void enterState(Enemy context) {

        // TODO: transition to combat state in the game application

    }

    @Override
    public void exitState(Enemy context) {
        System.out.println("Exiting CombatTransition");
    }

    @Override
    public void update(Enemy context, Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void onPlayerMoved(Enemy context, Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onPlayerMoved'");
    }

    @Override
    public EnemyType getType() {
        return this.enemyType;
    }
    
}
