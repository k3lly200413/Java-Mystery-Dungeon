package it.unibo.progetto_oop.Combat.StatePattern;

import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;

public interface CombatState {
    /**
     *
     * @param context Istance of the controller
     *
     * This method is called when a physical attack is performed during combat.
     */
    void handlePhysicalAttackInput(CombatController context);

    /**
     *
     * @param context Istance of the controller
     * @param isPoison boolean that indicates if the attack is poison
     * @param isFlame boolean that indicates if the attack is flame
     *
     * This method is called when a long-range attack
     * is performed during combat.
     */
    void handleLongRangeAttackInput(CombatController context,
    boolean isPoison, boolean isFlame);

    /**
     *
     * @param context Istance of the controller
     *
     * This method is called when info is requested during combat.
     */
    void handleInfoInput(CombatController context);

    /**
     *
     * @param context Istance of the controller
     *
     * This method is called when running away is attempted during combat.
     */
    void handleBackInput(CombatController context);

    /**
     *
     * @param context Istance of the controller
     *
     * This method is called when the bag is opened during combat.
     */
    void handleBagInput(CombatController context); // Add if implementing

    /**
     * This method is called when running away is attempted during combat.
     * @param context Istance of the controller
     */
    void handleRunInput(CombatController context);  // Add if implementing

    /**
     *
     * @param context Istance of the controller
     *
     * This method is called when an attack buff is used during combat.
     */
    void handleAttackBuffInput(CombatController context);

    /**
     *
     * @param context Istance of the controller
     *
     * This method is called when healing is performed during combat.
     */
    void handleHealInput(CombatController context);

    /**
     *
     * @param context Istance of the controller
     * @param selectedPotion The potion selected from the bag
     *
     * This method is called when a potion is used during combat.
     */
    void handlePotionUsed(CombatController context,
    Item selectedPotion);

    /**
     *
     * @param context Istance of the controller
     *
     * This method is called when curing poison during combat.
     */
    void handleCurePoisonInput(CombatController context);
    // void handleAttackBuffInput(CombatController context);
    // void handleHealingInput(CombatController context);

    /**
     *
     * @param context Istance of the controller
     *
     * This method is called when entering a combat state.
     */
    void enterState(CombatController context);

    /**
     *
     * @param context Istance of the controller
     *
     * This method is called when exiting a combat state.
     */
    void exitState(CombatController context);

    /**
     *
     * @param context Istance of the controller
     *
     * This method is called when an animation is complete during combat.
     */
    void handleAnimationComplete(CombatController context);

}
