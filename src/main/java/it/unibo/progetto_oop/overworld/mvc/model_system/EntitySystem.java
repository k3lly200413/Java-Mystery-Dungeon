package it.unibo.progetto_oop.overworld.mvc.model_system;

import java.util.List;
import java.util.Optional;

import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * Interface for pickup system and enemysystem.
 */
public interface EntitySystem<T> {

    /**
     * Removes an entity at the specified position.
     *
     * @param pos the position to check
     * @return true if an entity was found and removed, false otherwise
     */
    boolean removeEntityAt(Position pos);

    /**
     * Checks if an entity is found at the player's position.
     *
     * @return an Optional containing the entity if found, empty otherwise
     */
    Optional<T> entityFoundAtPlayerPosition();

    /**
     * Get the list of entities managed by the system.
     *
     * @return the list of entities
     */
    List<T> getEntities();

    /**
     * Set the entities managed by the system.
     *
     * @param entities the entities to set
     */
    void setEntities(List<T> entities);
}
