package it.unibo.progetto_oop.Overworld.Enemy.StatePattern;

import it.unibo.progetto_oop.Overworld.Enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.MVC.OverworldModel;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class CombatTransitionState implements GenericEnemyState{
    private final EnemyType enemyType;

    public CombatTransitionState(EnemyType enemyType) {
        this.enemyType = enemyType;
    }

    @Override
    public void enterState(Enemy context, OverworldModel model) {
        // TODO: transition to combat state in the game application

        model.clearCombatTransitionFlag(); // combat started, not in transition anymore
        model.setInCombatFlag(); 
    }

    @Override
    public void exitState(Enemy context) {
        System.out.println("Exiting CombatTransition");
    }

    @Override
    public void update(Enemy enemy, OverworldModel model, Player player) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void onPlayerMoved(Enemy context, Player player, OverworldModel model) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onPlayerMoved'");
    }

    @Override
    public EnemyType getType() {
        return this.enemyType;
    }
    
}
