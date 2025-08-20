package it.unibo.progetto_oop.Combat.Inventory;

public class InventoryController {
    private final Inventory model;
    private final InventoryView view;
    
    // TODO: Implement the InventoryController logic

    public InventoryController(Inventory model, InventoryView view) {
        this.model = model;
        this.view = view;
    }

    public void showInventory() {
        this.view.render(this.model);
    }
}
