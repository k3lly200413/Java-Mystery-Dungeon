package it.unibo.progetto_oop.EnemyStrategyPattern;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;

public class BossAttackStrategy {
    private CombatModel model;
    private int bossHealthPercent;
    private String curranteBossState;
    private int bossTurnCounter;

    public BossAttackStrategy(CombatModel model){
        this.model = model;
        // TODO: add below variables to model
        this.bossHealthPercent = (this.model.getEnemyHealth() / model.getMaxHealth())*100;
        this.curranteBossState = "Normal";
        this.bossTurnCounter = 0;
    }

    public void performAttack(CombatController context) {
        // this.model.increaseBossTurnCounter();
        this.bossHealthPercent = updatePercent();
        if (this.bossHealthPercent < 50 && this.curranteBossState.toUpperCase().equals("NORMAL")){
            this.curranteBossState = "ENRAGED";
            context.getView().showInfo("The Boss is now Enraged");
            //TODO: Change colour of boss
        }
        if (this.curranteBossState.toUpperCase().equals("ENRAGED")) {
            // TODO: Add another strong attack or something I don't have time now
            context.performEnemySuperAttack();
        }
        else {
            if (this.bossTurnCounter % 4 == 0) {
                // charging 
            }
            else if (this.bossTurnCounter % 3 == 0) {
                // perform long range attack
            } 
            else {
                // perform physical attack
            }
        }
    }

    private int updatePercent(){
        return (this.model.getEnemyHealth() / model.getMaxHealth()) * 100;
    }

}
