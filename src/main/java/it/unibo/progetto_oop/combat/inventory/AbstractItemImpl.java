package it.unibo.progetto_oop.combat.inventory;
import java.util.Objects;

import it.unibo.progetto_oop.overworld.player.adapter_pattern.PossibleUser;
import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * @author Laura Bertozzi
 */
public abstract class AbstractItemImpl implements Item {
    /**
     * the name of the item.
     */
    private final String name;

    /**
     * the description of the item.
     */
    private final String description;

    /**
     * the position of the item.
     */
    private final Position position;

    /**
     * Constructor for ItemImpl.
     * @param newName the name of the item
     * @param newDescription the description of the item
     * @param newPosition the position of the item
     */
    public AbstractItemImpl(final String newName, final String newDescription,
    final Position newPosition) {
        this.name = newName;
        this.description = newDescription;
        this.position = newPosition;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final String getDescription() {
        return description;
    }

    @Override
    public final Position getPosition() {
        return this.position;
    }

    @Override
    public abstract boolean use(PossibleUser target);

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AbstractItemImpl)) {
            return false;
        }
        final AbstractItemImpl other = (AbstractItemImpl) o;
        return Objects.equals(name, other.name)
        && Objects.equals(description, other.description);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(name, description);
    }
}
