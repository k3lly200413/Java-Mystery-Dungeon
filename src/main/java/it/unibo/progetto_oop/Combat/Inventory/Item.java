package it.unibo.progetto_oop.combat.inventory;
import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;
import it.unibo.progetto_oop.combat.position.Position;

public abstract class Item {
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

    public abstract boolean use(PossibleUser target);
}
