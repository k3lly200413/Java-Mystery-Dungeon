package it.unibo.progetto_oop.Combat.StatePattern;

import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Overworld.Player.Player;

public interface CombatState {
    void handlePhysicalAttackInput(CombatController context);
    void handleLongRangeAttackInput(CombatController context, boolean isPoison, boolean isFlame);
    void handleInfoInput(CombatController context);
    void handleBackInput(CombatController context);
    void handleBagInput(CombatController context); // Add if implementing
    void handleRunInput(CombatController context);  // Add if implementing
    void handleAttackBuffInput(CombatController context);

     /**
     * 
     * @param context Istance of the controller
     * @param selectedPotion Istance of Potionthat represents the used potion
     * 
     * This method is called when a potion is used during combat.
     */
    void handlePotionUsed(CombatController context, Item selectedPotion, Player player);

    void handleCurePoisonInput(CombatController context); // ??
    // void handleAttackBuffInput(CombatController context); ???
    // void handleHealingInput(CombatController context); // ???


    void enterState(CombatController context);
    void exitState(CombatController context);

    void handleAnimationComplete(CombatController context);


}
