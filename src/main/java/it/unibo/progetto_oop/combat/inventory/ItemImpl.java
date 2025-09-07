package it.unibo.progetto_oop.combat.inventory;
import java.util.Objects;

import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;

/**
 * @author Laura Bertozzi
 */
public abstract class ItemImpl implements Item {
    private final String name;
    private final String description;
    private final Position position;
    
    public ItemImpl(String name, String description, Position position) {
        this.name = name;
        this.description = description;
        this.position = position;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Position getPosition(){
        return this.position;
    }

    public abstract boolean use(PossibleUser target);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ItemImpl)) {
            return false;
        }
        ItemImpl other = (ItemImpl) o;
        return Objects.equals(name, other.name) &&
               Objects.equals(description, other.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}