package it.unibo.progetto_oop.combat.state_pattern;

import it.unibo.progetto_oop.combat.inventory.Item;
import it.unibo.progetto_oop.combat.mvc_pattern.CombatPresenter;
import it.unibo.progetto_oop.overworld.player.Player;
import it.unibo.progetto_oop.overworld.player.adapter_pattern.PossibleUser;

/**
 * Interface representing a state in the combat state pattern.
 */

public interface CombatState {
    /**
     * Handles the input for a physical attack during combat.
     *
     * @param context Instance of the presenter
     */
    void handlePhysicalAttackInput(CombatPresenter context);

    /**
     * This method is called when a long-range attack
     * is performed during combat.
     *
     * @param context Instance of the presenter
     * @param isPoison boolean that indicates if the attack is poison
     * @param isFlame boolean that indicates if the attack is flame
     */
    void handleLongRangeAttackInput(CombatPresenter context,
    boolean isPoison, boolean isFlame);

    /**
     * This method is called when info is requested during combat.
     *
     * @param context Instance of the presenter
     */
    void handleInfoInput(CombatPresenter context);

    /**
     * This method is called when running away is attempted during combat.
     *
     * @param context Instance of the presenter
     */
    void handleBackInput(CombatPresenter context);

    /**
     * This method is called when the bag is opened during combat.
     *
     * @param context Instance of the presenter
     */
    void handleBagInput(CombatPresenter context);

    /**
     * This method is called when running away is attempted during combat.
     *
     * @param context Istance of the presenter
     */
    void handleRunInput(CombatPresenter context);

    /**
     * This method is called when an attack buff is used during combat.
     *
     * @param context Instance of the presenter
     */
    void handleAttackBuffInput(CombatPresenter context);

    /**
     * This method is called when healing is performed during combat.
     *
     * @param context Instance of the presenter
     */
    void handleHealInput(CombatPresenter context);

    /**
     * This method is called when a potion is used during combat.
     *
     * @param user The PossibleUser using the potion
     * @param selectedPotion The potion selected from the bag
     * @param player The player using the potion
     */
    void handlePotionUsed(PossibleUser user,
    Item selectedPotion, Player player);

    /**
     * This method is called when curing poison during combat.
     *
     * @param context Instance of the presenter
     */
    void handleCurePoisonInput(CombatPresenter context);

    /**
     * This method is called when curing flame during combat.
     *
     * @param context Instance of the presenter
     */
    void enterState(CombatPresenter context);

    /**
     * This method is called when exiting a combat state.
     *
     * @param context Instance of the presenter
     */
    void exitState(CombatPresenter context);

    /**
     * This method is called when an animation is complete during combat.
     *
     * @param context Instance of the presenter
     */
    void handleAnimationComplete(CombatPresenter context);

}
