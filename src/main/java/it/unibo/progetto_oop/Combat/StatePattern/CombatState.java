package it.unibo.progetto_oop.Combat.StatePattern;

import it.unibo.progetto_oop.Combat.Inventory.Item;
import it.unibo.progetto_oop.Combat.MVC_Pattern.CombatController;
import it.unibo.progetto_oop.Overworld.Player.Player;

public interface CombatState {
    /**
     * Handles the input for a physical attack.
     *
     * @param context the CombatController context
     */
    void handlePhysicalAttackInput(CombatController context);
    /**
     * Handles the input for a long-range attack.
     *
     * @param context the CombatController context
     * @param isPoison indicates if the attack is poison-based
     * @param isFlame indicates if the attack is flame-based
     */
    void handleLongRangeAttackInput(
        CombatController context,
        boolean isPoison,
        boolean isFlame);
    /**
     * Handles the input for displaying information.
     *
     * @param context the CombatController context
     */
    void handleInfoInput(CombatController context);
    /**
     * Handles the input for going back in the combat menu.
     *
     * @param context the CombatController context
     */
    void handleBackInput(CombatController context);
    /**
     * Handles the input for opening the bag.
     *
     * @param context the CombatController context
     */
    void handleBagInput(CombatController context); // Add if implementing
    /**
     * Handles the input for running away from combat.
     *
     * @param context the CombatController context
     */
    void handleRunInput(CombatController context);  // Add if implementing
    /**
     * Handles the input for using a healing item.
     *
     * @param context the CombatController context
     */
    void handleAttackBuffInput(CombatController context);
    /**
     * Handles the input for healing the player.
     *
     * @param context the CombatController context
     */
    void handleHealInput(CombatController context);

     /**
     * Handles the input for using a potion during combat.
     *
     * @param context Istance of the controller
     * @param selectedPotion Istance of Potionthat represents the used potion
     * @param player The player who is using the potion
     *
     * This method is called when a potion is used during combat.
     */
    void handlePotionUsed(
        CombatController context,
        Item selectedPotion,
        Player player);
    /**
     * Handles the input for curing poison.
     * @param context the CombatController context
     */
    void handleCurePoisonInput(CombatController context); // ??
    // void handleAttackBuffInput(CombatController context); ???
    // void handleHealingInput(CombatController context); // ???

    /**
     * Enters the combat state.
     *
     * @param context the CombatController context
     */
    void enterState(CombatController context);
    /**
     * Exits the combat state.
     *
     * @param context the CombatController context
     */
    void exitState(CombatController context);
    /**
     * Handles the completion of an animation.
     *
     * @param context the CombatController context
     */
    void handleAnimationComplete(CombatController context);


}
