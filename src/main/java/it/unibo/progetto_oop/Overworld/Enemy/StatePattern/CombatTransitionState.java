package it.unibo.progetto_oop.Overworld.Enemy.StatePattern;

import it.unibo.progetto_oop.Overworld.Enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.MVC.ViewManager;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class CombatTransitionState implements GenericEnemyState{
    private final EnemyType enemyType;

    public CombatTransitionState(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    @Override
    public void enterState(Enemy context) {
        System.out.println("Entering CombatTransition");
        
    }

    @Override
    public void exitState(Enemy context) {
        System.out.println("Exiting CombatTransition");
    }

    @Override
    public void update(Enemy context, Player player) {}

    @Override
    public void onPlayerMoved(Enemy context, Player player) {}

    @Override
    public EnemyType getType() {
        return this.enemyType;
    }
    
}
