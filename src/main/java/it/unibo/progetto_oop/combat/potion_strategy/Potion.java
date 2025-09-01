package it.unibo.progetto_oop.combat.potion_strategy;

import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;
import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.position.Position;

public class Potion extends Item {

    /** The strategy for applying potion effects. */
    private final PotionStrategy strategy;

    /**
     * Constructs a Potion with the specified parameters.
     *
     * @param name        the name of the potion
     * @param description the description of the potion
     * @param position    the position of the potion on the game board
     * @param newStrategy    the effect strategy applied when the potion is used
     */
    public Potion(final String name, final String description,
                final Position position, final PotionStrategy newStrategy) {
        super(name, description, position);
        this.strategy = newStrategy;
    }

    /**
     * Returns the strategy associated with this potion.
     *
     * @return the potion's {@link PotionStrategy}
     */
    public PotionStrategy getStrategy() {
        return this.strategy;
    }

    /**
     * Uses the potion on the specified target. If a strategy is defined,
     * its effect is applied to the target.
     *
     * @param target instance of PossibleUser on which the effect is applied
     * @return true if the effect was applied, false otherwise
     */
    @Override
    public boolean use(final PossibleUser target) {
        if (this.strategy != null) {
            System.out.println("Applying effect of: " + this.getName());
            this.strategy.applyEffect(target);
            return true;
        } else {
            System.out.println("Warning: Potion "
            + getName() + " has no strategy.");
            return false;
        }
    }

}
