package it.unibo.progetto_oop.Overworld.Inventory;
import it.unibo.progetto_oop.Combat.Position.Position;

public class Item {
    private final String name;
    private final String description;
     private final Position position;
    
    public Item(String name, String description, Position position) {
        this.name = name;
        this.description = description;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Position getPosition(){
        return this.position;
    }
}
