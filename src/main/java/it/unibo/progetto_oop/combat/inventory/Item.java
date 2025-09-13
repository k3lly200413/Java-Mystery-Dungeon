package it.unibo.progetto_oop.combat.inventory;

import it.unibo.progetto_oop.combat.potion_strategy.PotionStrategy;
import it.unibo.progetto_oop.overworld.player.adapter_pattern.PossibleUser;
import it.unibo.progetto_oop.overworld.playground.data.Position;

/**
 * Interface for inventory items.
 */
public interface Item {

    /**
     * Get the name of the item.
     *
     * @return the name of the item
     */
    String getName();

    /**
     * Get the description of the item.
     *
     * @return the description of the item
     */
    String getDescription();

    /**
     * Get the position of the item.
     *
     * @return the position of the item
     */
    Position getPosition();

    /**
     * Get the strategy of the item.
     * Default is null because not all items have a strategy.
     *
     * @return the strategy of the item
     */
    default PotionStrategy getStrategy() {
        return null;
    }

    /**
     * Use the item on the specified target.
     *
     * @param target instance of PossibleUser on which the effect is applied
     * @return true if the effect was applied, false otherwise
     */
    boolean use(PossibleUser target);
}
