package it.unibo.progetto_oop.combat.potion_strategy;

import it.unibo.progetto_oop.Overworld.AdapterPattern.PossibleUser;
import it.unibo.progetto_oop.Overworld.PlayGround.Data.Position;
import it.unibo.progetto_oop.Combat.Inventory.Item;

public class Potion extends Item{

    private final PotionStrategy strategy;

    /**
     * 
     * @param name Nome che descrive la pozione
     * @param description Descrizione di funzione della pozione
     * @param quantity Quantità nell'inventario
     * @param strategy Istanza di Pozione necessaria per chiamare i metodi necessari
     */
    public Potion( String name, String description, Position position, PotionStrategy strategy) {
        super(name, description, position);
        this.strategy = strategy;
    }

    /**
     * @return the strategy
     */
    public PotionStrategy getStrategy(){
        return this.strategy;
    }

    /**
     * 
     * @param target Istance of CombatModel to call necessary functions to change player details
     */
    @Override
    public boolean use(PossibleUser target) {
        if (this.strategy != null) {
            this.strategy.applyEffect(target);
            return true;
        } else {
            return false;
        }
    }
}
