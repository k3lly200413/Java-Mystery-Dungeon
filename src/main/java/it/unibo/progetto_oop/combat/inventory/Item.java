package it.unibo.progetto_oop.combat.inventory;
import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.combat.potion_strategy.PotionStrategy;

public interface Item {

    public String getName();

    public String getDescription();

    public Position getPosition();

    default PotionStrategy getStrategy() {
        return null;
    }

    public abstract boolean use(PossibleUser target);
}
