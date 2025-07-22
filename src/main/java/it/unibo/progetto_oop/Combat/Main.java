package it.unibo.progetto_oop.Combat;

import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatModel;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatView;

public class Main {
    public static void main(String[] args){
        CombatModel model = new CombatModel();
        CombatView view = new CombatView(12);
        CombatController controller = new CombatController();
    }
}
