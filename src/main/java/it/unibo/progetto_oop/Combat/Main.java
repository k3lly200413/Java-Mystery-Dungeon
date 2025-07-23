package it.unibo.progetto_oop.Combat;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatView;

public class Main {
    public static void main(String[] args){

        //es
        int size = 15;
        int playerPower = 5;
        int playerPoisonPower = 2;
        int enemyPower = 3;
        int enemySpeed = 3;
        String enemyName = "Dragon";
        
        CombatModel model = new CombatModel(size, playerPower, playerPoisonPower, enemyPower, enemySpeed, enemyName);
        CombatView view = new CombatView(12);
        CombatController controller = new CombatController(model, view);
    }
}
