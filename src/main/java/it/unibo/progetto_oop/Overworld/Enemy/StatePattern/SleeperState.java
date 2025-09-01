package it.unibo.progetto_oop.Overworld.Enemy.StatePattern;


import it.unibo.progetto_oop.Overworld.Enemy.EnemyType;
import it.unibo.progetto_oop.Overworld.Enemy.CreationPattern.FactoryImpl.Enemy;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Overworld.Player.Player;

public class SleeperState implements GenericEnemyState {

    @Override
    public void enterState(Enemy context) {
        System.out.println("Entered Sleeper State");
    }

    @Override
    public void exitState(Enemy context) {
        System.out.println("Exiting Sleeper State");
    }

    @Override
    public void update(Enemy context, Player player) {
        System.out.println("In Sleeper State so no action taken");
        Position curPos = context.getCurrentPosition();
        
        context.getGridNotifier().notifyEnemyMoved(curPos, curPos);
    }

    @Override
    public void onPlayerMoved(Enemy context, Player player) {
        System.out.println("In Sleeper State so no action taken");
    }

    @Override
    public EnemyType getType() {
        return EnemyType.SLEEPER;
    }
    
}
