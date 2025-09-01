package it.unibo.progetto_oop.combat.state_pattern;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatController;
import it.unibo.progetto_oop.Overworld.Player.Player;

public interface CombatState {
    /**
     * Handles the input for a physical attack during combat.
     *
     * @param context Instance of the controller
     */
    void handlePhysicalAttackInput(CombatController context);

    /**
     * This method is called when a long-range attack
     * is performed during combat.
     *
     * @param context Instance of the controller
     * @param isPoison boolean that indicates if the attack is poison
     * @param isFlame boolean that indicates if the attack is flame
     */
    void handleLongRangeAttackInput(CombatController context,
    boolean isPoison, boolean isFlame);

    /**
     * This method is called when info is requested during combat.
     *
     * @param context Instance of the controller
     */
    void handleInfoInput(CombatController context);

    /**
     * This method is called when running away is attempted during combat.
     *
     * @param context Instance of the controller
     */
    void handleBackInput(CombatController context);

    /**
     * This method is called when the bag is opened during combat.
     *
     * @param context Instance of the controller
     */
    void handleBagInput(CombatController context); // Add if implementing

    /**
     * This method is called when running away is attempted during combat.
     *
     * @param context Istance of the controller
     */
    void handleRunInput(CombatController context);  // Add if implementing

    /**
     * This method is called when an attack buff is used during combat.
     *
     * @param context Instance of the controller
     */
    void handleAttackBuffInput(CombatController context);

    /**
     * This method is called when healing is performed during combat.
     *
     * @param context Instance of the controller
     */
    void handleHealInput(CombatController context);

    /**
     * This method is called when a potion is used during combat.
     *
     * @param context Instance of the controller
     * @param selectedPotion The potion selected from the bag
     * @param player The player using the potion
     */
    void handlePotionUsed(CombatController context,
    Item selectedPotion, Player player);

    /**
     * This method is called when curing poison during combat.
     *
     * @param context Instance of the controller
     */
    void handleCurePoisonInput(CombatController context);

    /**
     * This method is called when curing flame during combat.
     *
     * @param context Instance of the controller
     */
    void enterState(CombatController context);

    /**
     * This method is called when exiting a combat state.
     *
     * @param context Instance of the controller
     */
    void exitState(CombatController context);

    /**
     * This method is called when an animation is complete during combat.
     *
     * @param context Instance of the controller
     */
    void handleAnimationComplete(CombatController context);

}
