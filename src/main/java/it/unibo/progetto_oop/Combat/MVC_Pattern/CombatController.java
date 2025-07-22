package it.unibo.progetto_oop.Combat.MVC_Pattern;

public class CombatController {
    private CombatModel model;
    private CombatView view;

    public CombatController(CombatModel model, CombatView view){
        this.model = model;
        this.view = view;
        view.createGrid(model.getCells());
        view.setVisible(true);
    }

}
